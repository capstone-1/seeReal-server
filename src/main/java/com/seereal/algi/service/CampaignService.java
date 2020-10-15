package com.seereal.algi.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.seereal.algi.dto.registeredCampaign.CampaignRegisterRequestDto;
import com.seereal.algi.dto.registeredCampaign.CampaignSuggestRequestDto;
import com.seereal.algi.model.category.Category;
import com.seereal.algi.model.category.CategoryRepository;
import com.seereal.algi.model.registeredCampaign.RegisteredCampaign;
import com.seereal.algi.model.registeredCampaign.RegisteredCampaignRepository;
import com.seereal.algi.model.suggestedCampaign.SuggestedCampaign;
import com.seereal.algi.model.suggestedCampaign.SuggestedCampaignRepository;
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

    private final CategoryRepository categoryRepository;
    private final RegisteredCampaignRepository registeredCampaignRepository;
    private final SuggestedCampaignRepository suggestedCampaignRepository;
    private final S3Service s3Service;

    public String registerCampaign(CampaignRegisterRequestDto requestDto) {
        List<Category> categories = getCategories(requestDto.getCategories());
        RegisteredCampaign registeredCampaign = requestDto.toEntity();

        URL presignedUrl = s3Service.getPresignedUrl(registeredCampaign.getCampaignName());
        registeredCampaign.setCampaignImageUrl(s3Service.parseS3Url(presignedUrl));
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
    
    private List<Category> getCategories(List<String> categoryNames) {
        return categoryNames.stream().map(this::convertToCategory).collect(Collectors.toList());
    }
    private Category convertToCategory(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(() -> new NoSuchElementException("Invalid Category Name!"));
    }
}
