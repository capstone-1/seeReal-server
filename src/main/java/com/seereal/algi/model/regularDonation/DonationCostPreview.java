package com.seereal.algi.model.regularDonation;

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
    @JoinColumn(name = "regular_donation_id")
    private RegularDonation regularDonation;

    @Builder
    public DonationCostPreview(String name, Integer targetNum, Integer cost, String etc) {
        this.name = name;
        this.targetNum = targetNum;
        this.cost = cost;
        this.etc = etc;
    }

    public void setRegularDonation(RegularDonation regularDonation) {
        this.regularDonation = regularDonation;
    }
}
