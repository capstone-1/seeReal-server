package com.seereal.algi.dto.registeredCampaign;

import com.seereal.algi.model.category.Category;
import com.seereal.algi.model.registeredCampaign.RegisteredCampaign;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class CampaignDetailsResponseDto {
    private String registrant;
    private String campaignName;
    private String campaignImage;
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
    private List<String> categories = new ArrayList<>();

    @Builder
    public CampaignDetailsResponseDto(String registrant, String campaignName, String campaignImage, String introduction, LocalDate startDate, LocalDate endDate, Integer targetAmount, String target, Integer targetNumber, String explanation, String workName, Integer workFee, String workEtc, String itemName, Integer itemNumber, String itemShop, Integer itemFee, Boolean hasReception, Boolean hasReview, List<String> categories) {
        this.registrant = registrant;
        this.campaignName = campaignName;
        this.campaignImage = campaignImage;
        this.introduction = introduction;
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetAmount = targetAmount;
        this.target = target;
        this.targetNumber = targetNumber;
        this.explanation = explanation;
        this.workName = workName;
        this.workFee = workFee;
        this.workEtc = workEtc;
        this.itemName = itemName;
        this.itemNumber = itemNumber;
        this.itemShop = itemShop;
        this.itemFee = itemFee;
        this.hasReception = hasReception;
        this.hasReview = hasReview;
        this.categories = categories;
    }

    public static CampaignDetailsResponseDto convertToDto(RegisteredCampaign campaign) {
        return CampaignDetailsResponseDto.builder()
                .registrant(campaign.getRegistrant())
                .campaignName(campaign.getCampaignName())
                .campaignImage(campaign.getCampaignImage())
                .introduction(campaign.getIntroduction())
                .startDate(campaign.getStartDate())
                .endDate(campaign.getEndDate())
                .targetAmount(campaign.getTargetAmount())
                .target(campaign.getTarget())
                .targetNumber(campaign.getTargetNumber())
                .explanation(campaign.getExplanation())
                .workName(campaign.getWorkName())
                .workFee(campaign.getWorkFee())
                .workEtc(campaign.getWorkEtc())
                .itemName(campaign.getItemName())
                .itemNumber(campaign.getItemNumber())
                .itemShop(campaign.getItemShop())
                .itemFee(campaign.getItemFee())
                .hasReception(campaign.getHasReception())
                .hasReview(campaign.getHasReview())
                .categories(campaign.getCategories().stream().map(Category::getName).collect(Collectors.toList()))
                .build();
    }
}
