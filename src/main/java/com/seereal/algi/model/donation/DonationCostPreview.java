package com.seereal.algi.model.donation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class DonationCostPreview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer targetNum;
    private Integer cost;
    private String etc;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donation_id")
    private Donation donation;

    @Builder
    public DonationCostPreview(String name, Integer targetNum, Integer cost, String etc) {
        this.name = name;
        this.targetNum = targetNum;
        this.cost = cost;
        this.etc = etc;
    }

    public void setDonation(Donation donation) {
        this.donation = donation;
    }
}
