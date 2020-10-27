package com.seereal.algi.service;

import com.seereal.algi.config.constant.S3Constants;
import com.seereal.algi.dto.regularDonation.*;
import com.seereal.algi.model.category.Category;
import com.seereal.algi.model.category.CategoryRepository;
import com.seereal.algi.model.regularDonation.*;
import com.seereal.algi.service.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.seereal.algi.config.constant.S3Constants.REGULAR_DONATION_IMAGE;

@Service
public class RegularDonationService {
    @Autowired
    private RegularDonationRepository regularDonationRepository;
    @Autowired
    private DonationCostPreviewRepository donationCostPreviewRepository;
    @Autowired
    private DonationResultRepository donationResultRepository;
    @Autowired
    private DonationCostResultRepository donationCostResultRepository;
    @Autowired
    private S3Util s3Util;
    @Autowired
    private CategoryRepository categoryRepository;

    public String saveRegularDonation(RegularDonationSaveRequestDto requestDto) {
        RegularDonation donation = RegularDonationSaveRequestDto.convertToEntity(requestDto);
        regularDonationRepository.save(donation);
        URL presignedUrl = s3Util.getPresignedUrlForRegularDonation(donation.getName(),REGULAR_DONATION_IMAGE);
        donation.setProfileUrl(s3Util.parseS3Url(presignedUrl));
        requestDto.getCostPreviews().stream().map(DonationCostPreviewRequestDto::convertToEntity)
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

    // 정기기부 결과 등록
    public void saveDonationResult(DonationResultDto.Request requestDto, String name) {
        DonationResult result = requestDto.convertToEntity();
        RegularDonation regularDonation = regularDonationRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("해당 등록자 정보가 없습니다."));
        result.setRegularDonation(regularDonation);
        donationResultRepository.save(result);
    }

    // 정기기부 결과의 지출내역 등록
    public void saveDonationCostResult(DonationCostResultDto.Request requestDto, String name, Integer quarter) {
        DonationCostResult costResult = requestDto.convertToEntity();

        RegularDonation regularDonation = regularDonationRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("해당 등록자 정보가 없습니다."));
        List<DonationResult> results = regularDonation.getResults();

        // 도네이션 정보로부터 도네이션 리저트 획득 -> 입력받은 quater로 필터링
        DonationResult result = results.stream()
                .filter(donationResult -> donationResult.getQuarter().equals(quarter)).collect(Collectors.toList()).get(0);

        URL presignedUrl = s3Util.getPresignedUrlForDonation(result.getId() + result.getQuarter() + costResult.getName(),
                S3Constants.DONATION_RECEIPT);
        // costResult에 값 세팅팅
        costResult.setDonationResult(result);
        costResult.setReceiptUrl(s3Util.parseS3Url(presignedUrl));

        donationCostResultRepository.save(costResult);
    }

    // 한 정기기부의 특정 분기에 대한 결과 내역을 조회한다.
    public DonationResultDto.Response findDonationResultByNameAndQuater(String name, Integer quarter) {
        RegularDonation regularDonation = regularDonationRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("해당 등록자 정보가 없습니다."));
        List<DonationResult> results = regularDonation.getResults();

        // 도네이션 정보로부터 도네이션 리저트 획득 -> 입력받은 quater로 필터링
        DonationResult result = results.stream()
                .filter(donationResult -> donationResult.getQuarter().equals(quarter)).collect(Collectors.toList()).get(0);

        List<DonationCostResultDto.Response> resultDtos = result.getCostResults()
                .stream().map(DonationCostResultDto.Response::new).collect(Collectors.toList());

        return DonationResultDto.Response.builder()
                .content(result.getContent())
                .quarter(result.getQuarter())
                .costResult(resultDtos)
                .build();
    }
}
