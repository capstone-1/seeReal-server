package com.seereal.algi.dto.registeredCampaign;
import com.seereal.algi.model.registeredCampaign.RegisteredCampaign;
import com.seereal.algi.model.suggestedCampaign.SuggestedCampaign;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class CampaignSuggestRequestDto {
    private String campaignName;
    private String categories;
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
    private String motivation;
    private Boolean hasReception;
    private Boolean hasReview;

    public SuggestedCampaign toEntity() {
        return SuggestedCampaign.builder()
                .campaignName(this.campaignName)
                .categories(this.categories)
                .introduction(this.introduction)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .explanation(this.explanation)
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
                .motivation(this.motivation)
                .build();
    }
}