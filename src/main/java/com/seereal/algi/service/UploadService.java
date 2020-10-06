package com.seereal.algi.service;import com.seereal.algi.dto.activity.SaveActivityDto;import com.seereal.algi.dto.campaign.SaveCampaginDto;import com.seereal.algi.dto.campaigncost.SaveCampaignCostDto;import com.seereal.algi.model.activity.Activity;import com.seereal.algi.model.activity.ActivityRepository;import com.seereal.algi.model.campaign.CampaignRepository;import com.seereal.algi.model.campaigncost.CampaignCostRepository;import com.seereal.algi.model.organization.OrganizationRepository;import org.modelmapper.ModelMapper;import org.modelmapper.TypeMap;import org.springframework.stereotype.Service;@Servicepublic class UploadService {    private final ActivityRepository activityRepository;    private final CampaignRepository campaignRepository;    private final CampaignCostRepository costRepository;    private final OrganizationRepository organizationRepository;    private final ModelMapper modelMapper;    public UploadService(ActivityRepository activityRepository, CampaignRepository campaignRepository,                         CampaignCostRepository costRepository, OrganizationRepository organizationRepository,                         ModelMapper modelMapper){        this.activityRepository = activityRepository;        this.campaignRepository = campaignRepository;        this.costRepository = costRepository;        this.organizationRepository = organizationRepository;        this.modelMapper = modelMapper;    }//    public long saveActivity(SaveActivityDto activityDto){//        ////        Activity activity = modelMapper.map(activityDto, Activity.class);//        organizationRepository.findByName(organizationName);//        return 0;//    }////    public long saveCampaign(SaveCampaginDto campaginDto){//        String organizationName = campaginDto.getOrganizationName();//        return 0;//    }////    public long saveCampaignCost(SaveCampaignCostDto campaignCostDto){//        String organizationName = campaignCostDto.getOrganizationName();//        return 0;//    }}