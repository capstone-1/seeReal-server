package com.seereal.algi.service;

import com.seereal.algi.dto.registeredCampaign.BeforeApproveCampaignResponseDto;
import com.seereal.algi.dto.registeredCampaign.CampaignDetailsResponseDto;
import com.seereal.algi.dto.registeredCampaign.CampaignRegisterRequestDto;
import com.seereal.algi.dto.registeredCampaign.CampaignSuggestRequestDto;
import com.seereal.algi.model.category.Category;
import com.seereal.algi.model.category.CategoryRepository;
import com.seereal.algi.model.registeredCampaign.RegisteredCampaign;
import com.seereal.algi.model.registeredCampaign.RegisteredCampaignRepository;
import com.seereal.algi.model.suggestedCampaign.SuggestedCampaign;
import com.seereal.algi.model.suggestedCampaign.SuggestedCampaignRepository;
import com.seereal.algi.service.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CategoryRepository categoryRepository;
    private final RegisteredCampaignRepository registeredCampaignRepository;
    private final SuggestedCampaignRepository suggestedCampaignRepository;
    private final S3Util s3Util;

    public String registerCampaign(CampaignRegisterRequestDto requestDto, String registrant) {
        List<Category> categories = getCategories(requestDto.getCategories());
        RegisteredCampaign registeredCampaign = requestDto.toEntityWithRegistrant(registrant);

        URL presignedUrl = s3Util.getPresignedUrl(registeredCampaign.getCampaignName());
        registeredCampaign.setCampaignImageUrl(s3Util.parseS3Url(presignedUrl));
        // save
        for (Category category : categories) {
            registeredCampaign.addCategory(category);
            category.addRegisteredCampaign(registeredCampaign);
            categoryRepository.save(category);
        }
        registeredCampaignRepository.save(registeredCampaign);

       return presignedUrl.toExternalForm();
    }

    public Long suggestCampaign(CampaignSuggestRequestDto requestDto) {
        SuggestedCampaign suggestedCampaign = requestDto.toEntity();
        return suggestedCampaignRepository.save(suggestedCampaign).getId();
    }

    public List<BeforeApproveCampaignResponseDto> getAllCampaignsBeforeApprove() {
        System.out.println(registeredCampaignRepository.findAllNotApproved().size());
        return registeredCampaignRepository.findAllNotApproved()
                                            .stream()
                                            .map(BeforeApproveCampaignResponseDto::convertToDto)
                                            .collect(Collectors.toList());
    }

    public CampaignDetailsResponseDto getCampaignDetails(String campaignName) {
        return CampaignDetailsResponseDto.convertToDto(registeredCampaignRepository.findByCampaignName(campaignName)
                                            .orElseThrow(() -> new NoSuchElementException("No Campaign Details")));
    }

    private List<Category> getCategories(List<String> categoryNames) {
        return categoryNames.stream().map(this::convertToCategory).collect(Collectors.toList());
    }
    private Category convertToCategory(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(() -> new NoSuchElementException("Invalid Category Name!"));
    }
}
