package com.seereal.algi.model.regularDonation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class DonationCostResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer targetNum;
    private Integer cost;
    private String etc;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donation_result_id")
    private DonationResult donationResult;
    private String receiptUrl;

    @Builder
    public DonationCostResult(String name, Integer targetNum, Integer cost, String etc) {
        this.name = name;
        this.targetNum = targetNum;
        this.cost = cost;
        this.etc = etc;
    }

    public void setReceiptUrl(String url) {
        this.receiptUrl = url;
    }

    public void setDonationResult(DonationResult donationResult) {
        this.donationResult = donationResult;
    }
}
