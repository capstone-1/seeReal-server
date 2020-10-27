package com.seereal.algi.service;

import com.seereal.algi.config.constant.S3Constants;
import com.seereal.algi.dto.registeredCampaign.*;
import com.seereal.algi.model.campaignReview.OrganizationCampaignReviewRepository;
import com.seereal.algi.model.campaignReview.OrganizationCampignReview;
import com.seereal.algi.model.campaignReview.PersonalCampaignReview;
import com.seereal.algi.model.campaignReview.PersonalCampaignReviewRepository;
import com.seereal.algi.model.category.Category;
import com.seereal.algi.model.category.CategoryRepository;
import com.seereal.algi.model.registeredCampaign.RegisteredCampaign;
import com.seereal.algi.model.registeredCampaign.RegisteredCampaignRepository;
import com.seereal.algi.model.suggestedCampaign.SuggestedCampaign;
import com.seereal.algi.model.suggestedCampaign.SuggestedCampaignRepository;
import com.seereal.algi.service.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CategoryRepository categoryRepository;
    private final RegisteredCampaignRepository registeredCampaignRepository;
    private final SuggestedCampaignRepository suggestedCampaignRepository;
    private final PersonalCampaignReviewRepository personalCampaignReviewRepository;
    private final OrganizationCampaignReviewRepository organizationCampaignReviewRepository;
    private final ModelMapper modelMapper;
    private final S3Util s3Util;

    public String saveCampaign(List<Category> categories, RegisteredCampaign registeredCampaign) {
        URL presignedUrl = s3Util.getPresignedUrlForCampaign(registeredCampaign.getCampaignName(), S3Constants.CAMPAIGN_IMAGE);
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

    public String registerCampaign(CampaignRegisterRequestDto requestDto, String registrant) {
        List<Category> categories = getCategories(requestDto.getCategories());
        RegisteredCampaign registeredCampaign = requestDto.toEntityWithRegistrant(registrant);
        return saveCampaign(categories, registeredCampaign);
    }

    public Long suggestCampaign(CampaignSuggestDto.RequestAndResponse requestDto, String registrant) {
        SuggestedCampaign suggestedCampaign = requestDto.toEntityWithRegistrant(registrant);
        return suggestedCampaignRepository.save(suggestedCampaign).getId();
    }

    public List<SimpleCampaignResponseDto> getAllSuggestedCampaign() {
        return suggestedCampaignRepository.findAll().stream()
                .map(SimpleCampaignResponseDto::convertToDto)
                .collect(Collectors.toList());
    }

    public String approveSuggestedCampaign(Long id) {
        SuggestedCampaign suggestedCampaign = suggestedCampaignRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Invalid Id!"));
        List<String> parsedCategories = new ArrayList<>(Arrays.asList(suggestedCampaign.getCategories().split(", ")));
        List<Category> categories = getCategories(parsedCategories);

        RegisteredCampaign registeredCampaign = RegisteredCampaign.builder()
                .registrant(suggestedCampaign.getRegistrant())
                .campaignName(suggestedCampaign.getCampaignName())
                .explanation(suggestedCampaign.getExplanation())
                .startDate(suggestedCampaign.getStartDate())
                .endDate(suggestedCampaign.getEndDate())
                .introduction(suggestedCampaign.getIntroduction())
                .target(suggestedCampaign.getTarget())
                .targetAmount(suggestedCampaign.getTargetAmount())
                .targetNumber(suggestedCampaign.getTargetNumber())
                .workName(suggestedCampaign.getWorkName())
                .workFee(suggestedCampaign.getWorkFee())
                .workEtc(suggestedCampaign.getWorkEtc())
                .itemName(suggestedCampaign.getItemName())
                .itemNumber(suggestedCampaign.getItemNumber())
                .itemShop(suggestedCampaign.getItemShop())
                .itemFee(suggestedCampaign.getItemFee())
                .hasReview(suggestedCampaign.getHasReview())
                .hasReception(suggestedCampaign.getHasReception())
                .build();
        registeredCampaign.setApprove();
        suggestedCampaignRepository.delete(suggestedCampaign);
        return saveCampaign(categories, registeredCampaign);
    }

    public List<SimpleCampaignResponseDto> getAllCampaignsBeforeApprove() {
        return registeredCampaignRepository.findAllNotApproved()
                                            .stream()
                                            .map(SimpleCampaignResponseDto::convertToDto)
                                            .collect(Collectors.toList());
    }

    public CampaignDetailsResponseDto getCampaignDetails(String campaignName) {
        return CampaignDetailsResponseDto.convertToDto(registeredCampaignRepository.findByCampaignName(campaignName)
                                            .orElseThrow(() -> new NoSuchElementException("No Campaign Details")));
    }

    public CampaignDetailsResponseDto approveCampaign(String campaignName) {
        RegisteredCampaign campaign = registeredCampaignRepository.findByCampaignName(campaignName)
                                                                    .orElseThrow(() -> new NoSuchElementException("Invalid Category Name!"));
        campaign.setApprove();
        return CampaignDetailsResponseDto.convertToDto(registeredCampaignRepository.save(campaign));
    }

    public CampaignDetailsResponseDto addPersonalReview(String campaignName, String personalReview) {
        RegisteredCampaign campaign = registeredCampaignRepository.findByCampaignName(campaignName)
                .orElseThrow(() -> new NoSuchElementException("Invalid Category Name!"));
        PersonalCampaignReview review =  new PersonalCampaignReview(personalReview);
        campaign.addPersonalReview(review);

        personalCampaignReviewRepository.save(review);
        return CampaignDetailsResponseDto.convertToDto(registeredCampaignRepository.save(campaign));
    }

    public OrgCampaignReviewResponseDto addOrganizationReview(String campaignName, OrgCampaignReviewRequestDto requestDto) {
        RegisteredCampaign campaign = registeredCampaignRepository.findByCampaignName(campaignName)
                .orElseThrow(() -> new NoSuchElementException("Invalid Category Name!"));
        OrganizationCampignReview review = OrgCampaignReviewRequestDto.convertToEntity(requestDto);

        URL presignedUrlForWorkReceipt = s3Util.getPresignedUrlForCampaign(campaignName, S3Constants.CAMPAIGN_WORK_RECEIPT);
        review.setWorkReceiptUrl(s3Util.parseS3Url(presignedUrlForWorkReceipt));
        URL presignedUrlForItemReceipt = s3Util.getPresignedUrlForCampaign(campaignName, S3Constants.CAMPAIGN_ITEM_RECEIPT);
        review.setItemReceiptUrl(s3Util.parseS3Url(presignedUrlForItemReceipt));

        campaign.addOrganizationReview(review);
        organizationCampaignReviewRepository.save(review);
        registeredCampaignRepository.save(campaign);

        return new OrgCampaignReviewResponseDto(presignedUrlForItemReceipt.toExternalForm(), presignedUrlForWorkReceipt.toExternalForm());
    }

    private List<Category> getCategories(List<String> categoryNames) {
        return categoryNames.stream().map(this::convertToCategory).collect(Collectors.toList());
    }
    private Category convertToCategory(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(() -> new NoSuchElementException("Invalid Category Name!"));
    }
}
