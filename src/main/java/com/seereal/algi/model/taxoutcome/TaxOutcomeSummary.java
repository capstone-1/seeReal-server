package com.seereal.algi.model.taxoutcome;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "tax_outcome_summary")
@NoArgsConstructor
@Getter
public class TaxOutcomeSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_outcome_summary_id")
    private Long id;
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

    @Builder
    public TaxOutcomeSummary(Integer laborCost, Integer consumableCost, Integer rentCost, Integer etcOperationCost, Integer remedyCost, Integer eventCost, Integer promotionCost, Integer etcBusinessCost, Integer vatCost, Integer taxProcessingCost, Integer officeCost, Integer etcRecruitCost, Integer gasCost, Integer communicationCost, Integer etcCost, Integer conversionCost, Integer exchangeCost, Integer etcNonBusinessCost) {
        this.laborCost = laborCost;
        this.consumableCost = consumableCost;
        this.rentCost = rentCost;
        this.etcOperationCost = etcOperationCost;
        this.remedyCost = remedyCost;
        this.eventCost = eventCost;
        this.promotionCost = promotionCost;
        this.etcBusinessCost = etcBusinessCost;
        this.vatCost = vatCost;
        this.taxProcessingCost = taxProcessingCost;
        this.officeCost = officeCost;
        this.etcRecruitCost = etcRecruitCost;
        this.gasCost = gasCost;
        this.communicationCost = communicationCost;
        this.etcCost = etcCost;
        this.conversionCost = conversionCost;
        this.exchangeCost = exchangeCost;
        this.etcNonBusinessCost = etcNonBusinessCost;
    }
}
