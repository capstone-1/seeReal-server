package com.seereal.algi.dto.registeredCampaign;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class CampaignRegisterRequestDto {
    private String campaignName;
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
    private List<String> categories;
}
