package com.seereal.algi.service;

import com.seereal.algi.config.constant.S3Constants;
import com.seereal.algi.dto.donation.*;
import com.seereal.algi.model.category.Category;
import com.seereal.algi.model.category.CategoryRepository;
import com.seereal.algi.model.donation.*;
import com.seereal.algi.security.context.UserContext;
import com.seereal.algi.service.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.seereal.algi.config.constant.S3Constants.DONATION_IMAGE;

@Service
public class RegularDonationService {
    @Autowired
    private DonationRepository donationRepository;
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

    private Donation getDonationById(Long id, String s) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(s));
    }

    public String saveRegularDonation(DonationSaveRequestDto requestDto) {
        Donation donation = DonationSaveRequestDto.convertToEntity(requestDto);
        Long id = donationRepository.save(donation).getId();
        requestDto.getCostPreviews().stream().map(DonationCostPreviewRequestDto::convertToEntity)
                .forEach(r -> {
                    r.setDonation(donation);
                    donationCostPreviewRepository.save(r);
                });

        requestDto.getCategories().stream().map(this::convertToCategory)
                .forEach(c -> {
                    donation.addCategory(c);
                    c.addRegularDonation(donation);
                    categoryRepository.save(c);
                });

        URL presignedUrl = s3Util.getPresignedUrlForRegularDonation(id, DONATION_IMAGE);
        return presignedUrl.toExternalForm();
    } // 조회시 조회용 url 발급 필요

    public PagedModel<EntityModel<SimpleDonationResponseDto>> getRegularDonationList(Pageable pageable) {
        Page<SimpleDonationResponseDto> page = donationRepository.findAll(pageable).map(donation -> new SimpleDonationResponseDto(donation,
                s3Util.generateURL(S3Constants.DONATION_PREFIX, String.valueOf(donation.getId()), DONATION_IMAGE)));
        PagedResourcesAssembler<SimpleDonationResponseDto> assembler = new PagedResourcesAssembler<>(null, null);
        return assembler.toModel(page);
    }

    public PagedModel<EntityModel<SimpleDonationResponseDto>> getRegularDonationList(Pageable pageable, String name) {
        Page<SimpleDonationResponseDto> page = donationRepository.findSearchName(name, pageable).map(donation -> new SimpleDonationResponseDto(donation,
                s3Util.generateURL(S3Constants.DONATION_PREFIX, String.valueOf(donation.getId()), DONATION_IMAGE)));
        PagedResourcesAssembler<SimpleDonationResponseDto> assembler = new PagedResourcesAssembler<>(null, null);
        return assembler.toModel(page);
    }

    public DetailDonationResponseDto getRegularDonationDetail(Long id) {
        Donation donation = getDonationById(id, "No Donation");
        donation.setProfileUrl(s3Util.generateURL(S3Constants.DONATION_PREFIX, String.valueOf(id), DONATION_IMAGE));
        return DetailDonationResponseDto.convertToDto(donationRepository.save(donation));
    }

    private Category convertToCategory(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(() -> new NoSuchElementException("Invalid Category Name!"));
    }

    // 정기기부 결과 등록
    public void saveDonationResult(DonationResultDto.Request requestDto, Long id) {
        DonationResult result = requestDto.convertToEntity();
        Donation donation = getDonationById(id, "해당 등록자 정보가 없습니다.");
        result.setDonation(donation);
        donationResultRepository.save(result);
    }

    // 정기기부 결과의 지출내역 등록
    public void saveDonationCostResult(DonationCostResultDto.Request requestDto, Long id, Integer quarter) {
        DonationCostResult costResult = requestDto.convertToEntity();

        Donation donation = getDonationById(id, "해당 등록자 정보가 없습니다.");
        List<DonationResult> results = donation.getResults();

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

    // 한 정기기부의 특정 분기에 대한 결과 내역 조회
    public DonationResultDto.Response findDonationResultByNameAndQuater(Long id, Integer quarter) {
        Donation donation = getDonationById(id, "해당 등록자 정보가 없습니다.");
        List<DonationResult> results = donation.getResults();

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

    public PagedModel<EntityModel<SimpleDonationResponseDto>> getRegularDonationsByCategory(Pageable pageable, String category) {
        Page<SimpleDonationResponseDto> page = donationRepository.findSearchCategory(category, pageable).map(donation -> new SimpleDonationResponseDto(donation,
                s3Util.generateURL(S3Constants.DONATION_PREFIX, String.valueOf(donation.getId()), DONATION_IMAGE)));
        PagedResourcesAssembler<SimpleDonationResponseDto> assembler = new PagedResourcesAssembler<>(null, null);
        return assembler.toModel(page);
    }

    public void addFavoriteDonation(UserContext context, Long id) {
        //id로 정기기부 이름 찾기
        // token에서 email 추출하고 이를 통해 사용자 검색 (DB)
        // 연관관계 매핑
    }
}
