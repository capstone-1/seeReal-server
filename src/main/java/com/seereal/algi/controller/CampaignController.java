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

    // 캠페인 등록 (기업)
    @PostMapping("/campaign/register")
    public String registerCampaign(@RequestBody CampaignRegisterRequestDto requestDto) {
        //TODO: get registrant in JWT Token
        String registrant = "tempRegistrant";
        return campaignService.registerCampaign(requestDto, registrant);
    }

    //승인 대기 캠페인 조회
    @GetMapping("/campaign/before-approve")
    public List<BeforeApproveCampaignResponseDto> getCampaignBeforeApprove() {
        return campaignService.getAllCampaignsBeforeApprove();
    }

    //캠페인 상세정보 조회
    @GetMapping("/campaign/{campaignName}")
    public CampaignDetailsResponseDto getCampaignDetails(@PathVariable("campaignName") String campaignName) {
        return campaignService.getCampaignDetails(campaignName);

    }

    //캠페인 승인
    @PutMapping("/campaign/approve/{campaignName}")
    public CampaignDetailsResponseDto approveCampaign(@PathVariable("campaignName") String campaignName) {
        return campaignService.approveCampaign(campaignName);
    }

    // 개인 캠페인 후기 등록
    @PostMapping("/campaign/personal-review/{campaignName}")
    public CampaignDetailsResponseDto addPersonalReview(@PathVariable("campaignName") String campaignName, @RequestBody String personalReview) {
        return campaignService.addPersonalReview(campaignName,personalReview);
    }
    //TODO: 기업 캠페인 결과 등록

    @PostMapping("/campaign/suggest")
    public Long suggestCampaign(@RequestBody CampaignSuggestRequestDto requestDto) {
        return campaignService.suggestCampaign(requestDto);
    }
}
