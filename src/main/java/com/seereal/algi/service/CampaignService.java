package com.seereal.algi.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.seereal.algi.dto.registeredCampaign.CampaignRegisterRequestDto;
import com.seereal.algi.model.category.Category;
import com.seereal.algi.model.category.CategoryRepository;
import com.seereal.algi.model.registeredCampaign.RegisteredCampaign;
import com.seereal.algi.model.registeredCampaign.RegisteredCampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.seereal.algi.config.constant.S3Constants.BUCKET_NAME;
import static com.seereal.algi.config.constant.S3Constants.CAMPAIGN_PREFIX;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final AmazonS3 s3Client;
    private final CategoryRepository categoryRepository;
    private final RegisteredCampaignRepository registeredCampaignRepository;

    public String registerCampaign(CampaignRegisterRequestDto requestDto) {
        List<Category> categories = getCategories(requestDto.getCategories());
        RegisteredCampaign registeredCampaign = getRegisteredCampaign(requestDto);

        URL presignedUrl = getPresignedUrl(registeredCampaign.getCampaignName());
        registeredCampaign.setCampaignImageUrl(parseS3Url(presignedUrl));
        // save
        for (Category category : categories) {
            registeredCampaign.addCategory(category);
            category.addRegisteredCampaign(registeredCampaign);
            categoryRepository.save(category);
        }
        registeredCampaignRepository.save(registeredCampaign);

       return presignedUrl.toExternalForm();
    }

    private RegisteredCampaign getRegisteredCampaign(CampaignRegisterRequestDto requestDto) {
        return RegisteredCampaign.builder().campaignName(requestDto.getCampaignName())
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

    private String parseS3Url(URL presignedUrl) {
        return presignedUrl.getProtocol() + "://" + presignedUrl.getHost() + presignedUrl.getPath();
    }

    private URL getPresignedUrl(String campaignName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME, CAMPAIGN_PREFIX + campaignName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getExpiration());
        generatePresignedUrlRequest.addRequestParameter(Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());
        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }

    private Date getExpiration() {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + 1000 * 60 * 5);
        return expiration;
    }

    private List<Category> getCategories(List<String> categoryNames) {
        return categoryNames.stream().map(this::convertToCategory).collect(Collectors.toList());
    }
    private Category convertToCategory(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(() -> new NoSuchElementException("Invalid Category Name!"));
    }
}
