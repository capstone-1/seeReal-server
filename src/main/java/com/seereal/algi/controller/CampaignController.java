package com.seereal.algi.controller;

import com.seereal.algi.dto.registeredCampaign.BeforeApproveCampaignResponseDto;
import com.seereal.algi.dto.registeredCampaign.CampaignDetailsResponseDto;
import com.seereal.algi.dto.registeredCampaign.CampaignRegisterRequestDto;
import com.seereal.algi.dto.registeredCampaign.CampaignSuggestRequestDto;
import com.seereal.algi.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CampaignController {
    @Autowired
    private CampaignService campaignService;

    @PostMapping("/campaign/register")
    public String registerCampaign(@RequestBody CampaignRegisterRequestDto requestDto) {
        //TODO: get registrant in JWT Token
        String registrant = "tempRegistrant";
        return campaignService.registerCampaign(requestDto, registrant);
    }

    @GetMapping("/campaign/before-approve")
    public List<BeforeApproveCampaignResponseDto> getCampaignBeforeApprove() {
        return campaignService.getAllCampaignsBeforeApprove();
    }

    @GetMapping("/campaign/{campaignName}")
    public CampaignDetailsResponseDto getCampaignDetails(@PathVariable("campaignName") String campaignName) {
        return campaignService.getCampaignDetails(campaignName);

    }
    @PostMapping("/campaign/suggest")
    public Long suggestCampaign(@RequestBody CampaignSuggestRequestDto requestDto) {
        return campaignService.suggestCampaign(requestDto);
    }
}
