package com.seereal.algi.dto.registeredCampaign;

import com.seereal.algi.model.registeredCampaign.RegisteredCampaign;
import com.seereal.algi.model.suggestedCampaign.SuggestedCampaign;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SimpleCampaignResponseDto {
    private String name;
    private String registrant;

    @Builder
    public SimpleCampaignResponseDto(String name, String registrant) {
        this.name = name;
        this.registrant = registrant;
    }

    public static SimpleCampaignResponseDto convertToDto(RegisteredCampaign campaign) {
        return SimpleCampaignResponseDto.builder().name(campaign.getCampaignName()).registrant(campaign.getRegistrant()).build();
    }

    public static SimpleCampaignResponseDto convertToDto(SuggestedCampaign campaign) {
        return SimpleCampaignResponseDto.builder().name(campaign.getCampaignName()).registrant(campaign.getRegistrant()).build();
    }
}
