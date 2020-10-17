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
    private final PersonalCampaignReviewRepository personalCampaignReviewRepository;
    private final OrganizationCampaignReviewRepository organizationCampaignReviewRepository;
    private final S3Util s3Util;

    public String registerCampaign(CampaignRegisterRequestDto requestDto, String registrant) {
        List<Category> categories = getCategories(requestDto.getCategories());
        RegisteredCampaign registeredCampaign = requestDto.toEntityWithRegistrant(registrant);

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

        URL presignedUrlForWorkReceipt = s3Util.getPresignedUrlForCampaign(campaignName, S3Constants.CAMPAIGN_WORK_RECIEPT);
        review.setWorkReceiptUrl(s3Util.parseS3Url(presignedUrlForWorkReceipt));
        URL presignedUrlForItemReceipt = s3Util.getPresignedUrlForCampaign(campaignName, S3Constants.CAMPAIGN_ITEM_RECIEPT);
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
