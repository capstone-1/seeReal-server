package com.seereal.algi.dto.taxincome;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaxIncomeSummaryResponseDto {
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
}
