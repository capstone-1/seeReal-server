package com.seereal.algi.dto.registeredCampaign;

import com.seereal.algi.model.campaignReview.OrganizationCampignReview;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrgCampaignReviewRequestDto {
    private String content;
    private String workName;
    private Integer workFee;
    private Integer workNumber;
    private String workEtc;
    private String itemName;
    private Integer itemNumber;
    private String itemShop;
    private Integer itemFee;

    public static OrganizationCampignReview convertToEntity(OrgCampaignReviewRequestDto dto) {
        return OrganizationCampignReview.builder().content(dto.getContent())
                                                    .itemName(dto.getItemName())
                                                    .itemShop(dto.getItemShop())
                                                    .itenFee(dto.getItemFee())
                                                    .itenNumber(dto.getItemNumber())
                                                    .workEtc(dto.getWorkEtc())
                                                    .workFee(dto.getWorkFee())
                                                    .workName(dto.getWorkName())
                                                    .workNumber(dto.getWorkNumber())
                                                    .build();
    }
}
