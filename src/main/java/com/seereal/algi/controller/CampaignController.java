package com.seereal.algi.controller;

import com.seereal.algi.dto.registeredCampaign.CampaignRegisterRequestDto;
import com.seereal.algi.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CampaignController {
    @Autowired
    private CampaignService campaignService;

    @PostMapping("/campaign")
    public String registerCampaign(@RequestBody CampaignRegisterRequestDto requestDto) {
       return campaignService.registerCampaign(requestDto);
    }
}
