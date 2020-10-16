package com.seereal.algi.model.taxincome;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "tax_income_summary")
@NoArgsConstructor
@Getter
@Setter
public class TaxIncomeSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_income_summary_id")
    private Long id;
    private Integer carriedAmount;
    private Integer personalDonation;
    private Integer corporateDonation;
    private Integer ngoDonation;
    private Integer itemDonation;
    private Integer etcDonation;
    private Integer supportFund;
    private Integer grantFund;
    private Integer otherFund;
    private Integer etcBusinessProfit;
    private Integer interestProfit;
    private Integer conversionProfit;
    private Integer exchangeProfit;
    private Integer etcNonBusinessProfit;

    @Builder
    public TaxIncomeSummary(Integer carriedAmount, Integer personalDonation, Integer corporateDonation, Integer ngoDonation, Integer itemDonation, Integer etcDonation, Integer supportFund, Integer grantFund, Integer otherFund, Integer etcBusinessProfit, Integer interestProfit, Integer conversionProfit, Integer exchangeProfit, Integer etcNonBusinessProfit) {
        this.carriedAmount = carriedAmount;
        this.personalDonation = personalDonation;
        this.corporateDonation = corporateDonation;
        this.ngoDonation = ngoDonation;
        this.itemDonation = itemDonation;
        this.etcDonation = etcDonation;
        this.supportFund = supportFund;
        this.grantFund = grantFund;
        this.otherFund = otherFund;
        this.etcBusinessProfit = etcBusinessProfit;
        this.interestProfit = interestProfit;
        this.conversionProfit = conversionProfit;
        this.exchangeProfit = exchangeProfit;
        this.etcNonBusinessProfit = etcNonBusinessProfit;
    }
}
