package com.seereal.algi.service;

import com.seereal.algi.config.constant.S3Constants;
import com.seereal.algi.dto.regularDonation.DetailRegularDonationResponseDto;
import com.seereal.algi.dto.regularDonation.DonationCostPreviewRequestDto;
import com.seereal.algi.dto.regularDonation.RegularDonationSaveRequestDto;
import com.seereal.algi.dto.regularDonation.SimpleRegularDonationResponseDto;
import com.seereal.algi.model.category.Category;
import com.seereal.algi.model.category.CategoryRepository;
import com.seereal.algi.model.registeredCampaign.RegisteredCampaign;
import com.seereal.algi.model.regularDonation.DonationCostPreviewRepository;
import com.seereal.algi.model.regularDonation.RegularDonation;
import com.seereal.algi.model.regularDonation.RegularDonationRepository;
import com.seereal.algi.service.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.seereal.algi.config.constant.S3Constants.REGULAR_DONATION_IMAGE;
import static com.seereal.algi.config.constant.S3Constants.REGULAR_DONATION_PREFIX;

@Service
public class RegularDonationService {
    @Autowired
    private RegularDonationRepository regularDonationRepository;
    @Autowired
    private DonationCostPreviewRepository donationCostPreviewRepository;
    @Autowired
    private S3Util s3Util;
    @Autowired
    private CategoryRepository categoryRepository;

    public String saveRegularDonation(RegularDonationSaveRequestDto requestDto) {
        RegularDonation donation = RegularDonationSaveRequestDto.convertToEntity(requestDto);
        regularDonationRepository.save(donation);
        URL presignedUrl = s3Util.getPresignedUrlForRegularDonation(donation.getName(),REGULAR_DONATION_IMAGE);
        donation.setProfileUrl(s3Util.parseS3Url(presignedUrl));
        requestDto.getCostPreviews().stream().map(DonationCostPreviewRequestDto::convertToentity)
                .forEach(r -> {
                    r.setRegularDonation(donation);
                    donationCostPreviewRepository.save(r);
                });

        requestDto.getCategories().stream().map(this::convertToCategory)
                .forEach(c -> {
                    donation.addCategory(c);
                    c.addRegularDonation(donation);
                    categoryRepository.save(c);
                });
        return presignedUrl.toExternalForm();
    }

    public List<SimpleRegularDonationResponseDto> getRegularDonationList() {
        return regularDonationRepository.findAll().stream().map(r -> SimpleRegularDonationResponseDto.builder()
                                                                                            .name(r.getName())
                                                                                            .registrant(r.getRegistrant())
                                                                                            .build()).collect(Collectors.toList());
    }

    public DetailRegularDonationResponseDto getRegularDonationDetail(String name) {
        return DetailRegularDonationResponseDto.convertToDto(regularDonationRepository.findByName(name)
                                                                .orElseThrow(() -> new NoSuchElementException("Regualr Donation doesn't exist!")));

    }

    private Category convertToCategory(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(() -> new NoSuchElementException("Invalid Category Name!"));
    }
}
