package com.seereal.algi.dto.registeredCampaign;

import com.seereal.algi.model.registeredCampaign.RegisteredCampaign;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BeforeApproveCampaignResponseDto {
    private String name;
    private String registrant;

    @Builder
    public BeforeApproveCampaignResponseDto(String name, String registrant) {
        this.name = name;
        this.registrant = registrant;
    }

    public static BeforeApproveCampaignResponseDto convertToDto(RegisteredCampaign campaign) {
        return BeforeApproveCampaignResponseDto.builder().name(campaign.getCampaignName()).registrant(campaign.getRegistrant()).build();
    }
}
