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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.*;
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

    public String saveCampaign(List<Category> categories, RegisteredCampaign registeredCampaign) {
        // save
        for (Category category : categories) {
            registeredCampaign.addCategory(category);
            category.addRegisteredCampaign(registeredCampaign);
            categoryRepository.save(category);
        }
        Long id = registeredCampaignRepository.save(registeredCampaign).getId();

        URL presignedUrl = s3Util.getPresignedUrlForCampaign(id, S3Constants.CAMPAIGN_IMAGE);
//        registeredCampaign.setCampaignImageUrl(s3Util.parseS3Url(presignedUrl));
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

    @Transactional
    public CampaignDetailsResponseDto getCampaignDetails(Long id) {
        RegisteredCampaign registeredCampaign = registeredCampaignRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Campaign Details"));
        registeredCampaign.setCampaignImage(s3Util.generateURL(S3Constants.CAMPAIGN_PREFIX, String.valueOf(id), S3Constants.CAMPAIGN_IMAGE));
        return CampaignDetailsResponseDto.convertToDto(registeredCampaignRepository.save(registeredCampaign));
    }

    public CampaignDetailsResponseDto approveCampaign(Long id) {
        RegisteredCampaign campaign = registeredCampaignRepository.findById(id)
                                                                    .orElseThrow(() -> new NoSuchElementException("Invalid Category Name!"));
        campaign.setApprove();
        return CampaignDetailsResponseDto.convertToDto(registeredCampaignRepository.save(campaign));
    }

    public CampaignDetailsResponseDto addPersonalReview(Long id, String personalReview) {
        RegisteredCampaign campaign = registeredCampaignRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Invalid Category Name!"));
        PersonalCampaignReview review =  new PersonalCampaignReview(personalReview);
        campaign.addPersonalReview(review);

        personalCampaignReviewRepository.save(review);
        return CampaignDetailsResponseDto.convertToDto(registeredCampaignRepository.save(campaign));
    }

    public OrgCampaignReviewResponseDto addOrganizationReview(Long id, OrgCampaignReviewRequestDto requestDto) {
        RegisteredCampaign campaign = registeredCampaignRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Invalid Category Name!"));
        OrganizationCampignReview review = OrgCampaignReviewRequestDto.convertToEntity(requestDto);

        URL presignedUrlForWorkReceipt = s3Util.getPresignedUrlForCampaign(id, S3Constants.CAMPAIGN_WORK_RECEIPT);
        review.setWorkReceiptUrl(s3Util.parseS3Url(presignedUrlForWorkReceipt));
        URL presignedUrlForItemReceipt = s3Util.getPresignedUrlForCampaign(id, S3Constants.CAMPAIGN_ITEM_RECEIPT);
        review.setItemReceiptUrl(s3Util.parseS3Url(presignedUrlForItemReceipt));

        campaign.addOrganizationReview(review);
        organizationCampaignReviewRepository.save(review);
        registeredCampaignRepository.save(campaign);

        return new OrgCampaignReviewResponseDto(presignedUrlForItemReceipt.toExternalForm(), presignedUrlForWorkReceipt.toExternalForm());
    }

    public List<SimpleCampaignResponseDto> getCampaignByCategories(List<String> categories) {
        List<Category> categoryList = categories.stream()
                                                .map(this::convertToCategory)
                                                .collect(Collectors.toList());
        Set<RegisteredCampaign> campaigns = new HashSet<>();
        for (Category category : categoryList) {
            categoryRepository.findByName(category.getName()).ifPresent(c -> campaigns.addAll(c.getRegisteredCampaigns()));
        }
        return campaigns.stream().filter(RegisteredCampaign::getIsApprove).map(SimpleCampaignResponseDto::convertToDto).collect(Collectors.toList());
    }

    private List<Category> getCategories(List<String> categoryNames) {
        return categoryNames.stream().map(this::convertToCategory).collect(Collectors.toList());
    }

    private Category convertToCategory(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(() -> new NoSuchElementException("Invalid Category Name!"));
    }
}
