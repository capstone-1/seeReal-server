package com.seereal.algi.model.suggestedCampaign;

import com.seereal.algi.model.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class SuggestedCampaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suggested_campaign_id")
    private Long id;
    private String registrant;
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
    private String motivation;
    private Boolean hasReception;
    private Boolean hasReview;
    private String categories;

    @Builder
    public SuggestedCampaign(String registrant, String campaignName, String categories, String introduction, LocalDate startDate, LocalDate endDate,
                             Integer targetAmount, String target, Integer targetNumber, String explanation, String workName, Integer workFee,
                             String workEtc, String itemName, Integer itemNumber, String itemShop, Integer itemFee, String motivation,
                             Boolean hasReception, Boolean hasReview){
        this.registrant = registrant;
        this.campaignName = campaignName;
        this.categories = categories;
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
        this.motivation = motivation;
        this.hasReception = hasReception;
        this.hasReview = hasReview;
    }
}

