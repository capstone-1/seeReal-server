package com.seereal.algi.model.donation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class DonationResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_result_id")
    private Long id;
    private String content;
    private Integer quarter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regular_donation_id")
    private Donation donation;

    @OneToMany(mappedBy = "donationResult", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DonationCostResult> costResults = new ArrayList<>();

    @Builder
    public DonationResult(String content, Integer quarter) {
        this.content = content;
        this.quarter = quarter;
    }

    public void setDonation(Donation donation) {
        this.donation = donation;
    }
}
