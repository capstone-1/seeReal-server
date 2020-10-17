package com.seereal.algi.dto.registeredCampaign;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class OrgCampaignReviewResponseDto {
    private String itemReceiptUrl;
    private String workReceiptUrl;

    public OrgCampaignReviewResponseDto(String itemReceiptUrl, String workReceiptUrl) {
        this.itemReceiptUrl = itemReceiptUrl;
        this.workReceiptUrl = workReceiptUrl;
    }
}
