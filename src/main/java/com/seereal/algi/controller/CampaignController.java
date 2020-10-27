package com.seereal.algi.controller;

import com.seereal.algi.dto.registeredCampaign.*;
import com.seereal.algi.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CampaignController {
    @Autowired
    private CampaignService campaignService;

    // 캠페인 제안 (개인)
    @PostMapping("/campaign/suggest")
    public Long suggestCampaign(@RequestBody CampaignSuggestDto.RequestAndResponse requestDto) {
        String registrant = "tempRegistrant";
        return campaignService.suggestCampaign(requestDto, registrant);
    }

    // 캠페인 등록 (기업)
    @PostMapping("/campaign/register")
    public String registerCampaign(@RequestBody CampaignRegisterRequestDto requestDto) {
        //TODO: get registrant in JWT Token
        String registrant = "tempRegistrant";
        return campaignService.registerCampaign(requestDto, registrant);
    }

    //제안된 캠페인 조회
    @GetMapping("/campaign/suggested")
    public List<SimpleCampaignResponseDto> getSuggestedCampaign() {
        return campaignService.getAllSuggestedCampaign();
    }

    //승인 대기 캠페인 조회
    @GetMapping("/campaign/before-approve")
    public List<SimpleCampaignResponseDto> getCampaignBeforeApprove() {
        return campaignService.getAllCampaignsBeforeApprove();
    }

    //캠페인 상세정보 조회
    @GetMapping("/campaign/{campaignName}")
    public CampaignDetailsResponseDto getCampaignDetails(@PathVariable("campaignName") String campaignName) {
        return campaignService.getCampaignDetails(campaignName);
    }

    //캠페인 승인 (단체)
    @PutMapping("/campaign/approve/{campaignName}")
    public CampaignDetailsResponseDto approveCampaign(@PathVariable("campaignName") String campaignName) {
        return campaignService.approveCampaign(campaignName);
    }

    //제안된 캠페인 등록 - 반환값은 이미지 upload url
    @PostMapping("/campaign/suggested-approve/{id}")
    public String approveSuggestedCampaign(@PathVariable("id") Long id){
        return campaignService.approveSuggestedCampaign(id);
    }

    // 개인 캠페인 후기 등록
    @PostMapping("/campaign/personal-review/{campaignName}")
    public CampaignDetailsResponseDto addPersonalReview(@PathVariable("campaignName") String campaignName, @RequestBody String personalReview) {
        return campaignService.addPersonalReview(campaignName,personalReview);
    }

    // 기업 캠페인 후기 등록
    @PostMapping("campaign/organization-review/{campaignName}")
    public OrgCampaignReviewResponseDto addOrganizationReview(@PathVariable("campaignName") String campaignName, @RequestBody OrgCampaignReviewRequestDto requestDto) {
        return campaignService.addOrganizationReview(campaignName, requestDto);
    }

    // 승인된 캠페인 조회 (검색 조건 : 카테고리)
    @GetMapping("/campaign/category")
    public List<CampaignDetailsResponseDto> getCampaignsByCategory(@RequestParam(value = "name") List<String> categories) {
       return campaignService.getCampaignByCategories(categories);
    }
}