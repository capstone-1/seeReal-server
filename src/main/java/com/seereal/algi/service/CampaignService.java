package com.seereal.algi.service;

import com.seereal.algi.dto.registeredCampaign.CampaignRegisterRequestDto;
import com.seereal.algi.model.category.Category;
import com.seereal.algi.model.category.CategoryRepository;
import com.seereal.algi.model.registeredCampaign.RegisteredCampaign;
import com.seereal.algi.model.registeredCampaign.RegisteredCampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CampaignService {
    //TODO: Generate Pre Signed Url
    private static final String TEMP_PRE_SIGNED_URL_PREFIX = "http://presignedurl/" ;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RegisteredCampaignRepository registeredCampaignRepository;

    public String registerCampaign(CampaignRegisterRequestDto requestDto) {
        List<Category> categories = getCategories(requestDto.getCategories());
        RegisteredCampaign registeredCampaign = getRegisteredCampaign(requestDto);

        // save
        for (Category category : categories) {
            registeredCampaign.addCategory(category);
            category.addRegisteredCampaign(registeredCampaign);
            categoryRepository.save(category);
        }
        registeredCampaignRepository.save(registeredCampaign);
        //TODO: Generate Pre Signed Url
        return TEMP_PRE_SIGNED_URL_PREFIX + registeredCampaign.getCampaignName();
    }

    private RegisteredCampaign getRegisteredCampaign(CampaignRegisterRequestDto requestDto) {
        return RegisteredCampaign.builder().campaignName(requestDto.getCampaignName())
                                            .campaignImage(generateS3Url(requestDto.getCampaignName()))
                                            .startDate(requestDto.getStartDate())
                                            .endDate(requestDto.getEndDate())
                                            .explanation(requestDto.getExplanation())
                                            .introduction(requestDto.getIntroduction())
                                            .itemFee(requestDto.getItemFee())
                                            .itemName(requestDto.getItemName())
                                            .itemNumber(requestDto.getItemNumber())
                                            .itemShop(requestDto.getItemShop())
                                            .target(requestDto.getTarget())
                                            .targetAmount(requestDto.getTargetAmount())
                                            .targetNumber(requestDto.getTargetNumber())
                                            .workEtc(requestDto.getWorkEtc())
                                            .workFee(requestDto.getWorkFee())
                                            .workName(requestDto.getWorkName())
                                            .hasReception(requestDto.getHasReception())
                                            .hasReview(requestDto.getHasReview())
                                            .build();
    }
    //TODO: generate S3 URL
    private String generateS3Url(String campaignName) {
        return "http://test-s3-url/" + campaignName;
    }
    private List<Category> getCategories(List<String> categoryNames) {
        return categoryNames.stream().map(this::convertToCategory).collect(Collectors.toList());
    }
    private Category convertToCategory(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(() -> new NoSuchElementException("Invalid Category Name!"));
    }
}
