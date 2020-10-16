package com.seereal.algi.dto.registeredCampaign;


import com.seereal.algi.model.registeredCampaign.RegisteredCampaign;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class CampaignRegisterRequestDto {
    private String campaignName;
    private String introduction;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer targetAmount;
    private String target;
    private Integer targetNumber;
    private String explanation;
    private String workName;
    private Integer workFee;
    private String workEtc;
    private String itemName;
    private Integer itemNumber;
    private String itemShop;
    private Integer itemFee;
    private Boolean hasReception;
    private Boolean hasReview;
    private List<String> categories;

    public RegisteredCampaign toEntityWithRegistrant(String registrant) {
        return RegisteredCampaign.builder()
                .registrant(registrant)
                .campaignName(this.campaignName)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .explanation(this.explanation)
                .introduction(this.introduction)
                .itemFee(this.itemFee)
                .itemName(this.itemName)
                .itemNumber(this.itemNumber)
                .itemShop(this.itemShop)
                .target(this.target)
                .targetAmount(this.targetAmount)
                .targetNumber(this.targetNumber)
                .workEtc(this.workEtc)
                .workFee(this.workFee)
                .workName(this.workName)
                .hasReception(this.hasReception)
                .hasReview(this.hasReview)
                .build();
    }
}
