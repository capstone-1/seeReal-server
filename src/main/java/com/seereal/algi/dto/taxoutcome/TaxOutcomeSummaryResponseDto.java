package com.seereal.algi.dto.taxoutcome;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaxOutcomeSummaryResponseDto {
    private Integer laborCost;
    private Integer consumableCost;
    private Integer rentCost;
    private Integer etcOperationCost;
    private Integer remedyCost;
    private Integer eventCost;
    private Integer promotionCost;
    private Integer etcBusinessCost;
    private Integer vatCost;
    private Integer taxProcessingCost;
    private Integer officeCost;
    private Integer etcRecruitCost;
    private Integer gasCost;
    private Integer communicationCost;
    private Integer etcCost;
    private Integer conversionCost;
    private Integer exchangeCost;
    private Integer etcNonBusinessCost;
}
