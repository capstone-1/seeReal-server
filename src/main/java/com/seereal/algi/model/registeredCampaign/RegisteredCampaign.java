package com.seereal.algi.model.registeredCampaign;

import com.seereal.algi.model.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
//@Entity(name = "registered_campaign")
public class RegisteredCampaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registered_campaign_id")
    private Long id;
    private String campaignName;
    private String campaignImage;
    private String introduction;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "campaign_category",
                joinColumns = @JoinColumn(name = "registered_campaign_id"),
                inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @Builder
    public RegisteredCampaign(String campaignName, String campaignImage, String introduction, LocalDateTime startDate, LocalDateTime endDate, Integer targetAmount, String target, Integer targetNumber, String explanation, String workName, Integer workFee, String workEtc, String itemName, Integer itemNumber, String itemShop, Integer itemFee, Boolean hasReception, Boolean hasReview) {
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
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }
}
