package com.seereal.algi.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.seereal.algi.dto.activity.ActivityRequestDto;
import com.seereal.algi.dto.activity.ActivityResponseDto;
import com.seereal.algi.dto.campaign.CampaginRequestDto;
import com.seereal.algi.dto.campaign.CampaignResponseDto;
import com.seereal.algi.dto.campaigncost.CampaignCostRequestDto;
import com.seereal.algi.dto.campaigncost.CampaignCostResponseDto;
import com.seereal.algi.dto.organization.OrganizationSignUpRequestDto;
import com.seereal.algi.dto.taxincome.TaxIncomeSummaryRequestDto;
import com.seereal.algi.dto.taxincome.TaxIncomeSummaryResponseDto;
import com.seereal.algi.dto.taxoutcome.TaxOutcomeSummaryRequestDto;
import com.seereal.algi.dto.taxoutcome.TaxOutcomeSummaryResponseDto;
import com.seereal.algi.model.activity.Activity;
import com.seereal.algi.model.activity.ActivityRepository;
import com.seereal.algi.model.campaign.Campaign;
import com.seereal.algi.model.campaign.CampaignRepository;
import com.seereal.algi.model.campaigncost.CampaignCost;
import com.seereal.algi.model.campaigncost.CampaignCostRepository;
import com.seereal.algi.model.organization.Organization;
import com.seereal.algi.model.organization.OrganizationRepository;
import com.seereal.algi.model.taxincome.TaxIncomeSummary;
import com.seereal.algi.model.taxincome.TaxIncomeSummaryRepository;
import com.seereal.algi.model.taxoutcome.TaxOutcomeSummary;
import com.seereal.algi.model.taxoutcome.TaxOutcomeSummaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationService {
    private static String BUCKET_NAME = "capstone-seereal";
    private static String BUSINESS_REPORT_NAME = "/business-report";
    private static String TAX_REPORT_NAME = "/tax_report";
    private static Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    private final ActivityRepository activityRepository;
    private final CampaignRepository campaignRepository;
    private final CampaignCostRepository costRepository;
    private final ModelMapper modelMapper;
    private final OrganizationRepository organizationRepository;
    private final AmazonS3 s3Client;
    private final TaxIncomeSummaryRepository incomeSummaryRepository;
    private final TaxOutcomeSummaryRepository outcomeSummaryRepository;

    public String getPresignedUrlForBusinessReport(String registerNumber) {
        URL presignedUrl = getPresignedUrl(registerNumber, BUSINESS_REPORT_NAME);
        //DB 저장
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                                                            .orElseThrow(() -> new IllegalArgumentException("Organization Not Found!"));
        organization.setBusinessReportLink(parseS3Url(presignedUrl));
        organizationRepository.save(organization);

        return presignedUrl.toExternalForm();
    }

    public String getPresignedUrlForTaxReport(String registerNumber) {
        URL presignedUrl =  getPresignedUrl(registerNumber, TAX_REPORT_NAME);
        //DB 저장
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                                                            .orElseThrow(() -> new IllegalArgumentException("Organization Not Found!"));
        organization.setTaxReportLink(parseS3Url(presignedUrl));
        organizationRepository.save(organization);
        return presignedUrl.toExternalForm();

    }

    public String signUp(OrganizationSignUpRequestDto requestDto) {
        if (organizationRepository.findByRegisterNumber(requestDto.getRegisterNumber()).isPresent()) {
            return null;
        }
        return organizationRepository.save(convertToEntity(requestDto)).getName();
    }

    private Organization convertToEntity(OrganizationSignUpRequestDto requestDto) {
        return Organization.builder()
                            .name(requestDto.getName())
                            .password(requestDto.getPassword())
                            .account(requestDto.getAccount())
                            .email(requestDto.getEmail())
                            .phoneNumber(requestDto.getPhoneNumber())
                            .registerNumber(requestDto.getRegisterNumber())
                            .build();
    }

    private String parseS3Url(URL presignedUrl) {
        return presignedUrl.getProtocol() + "://" + presignedUrl.getHost() + presignedUrl.getPath();
    }

    private URL getPresignedUrl(String organizationName, String reportType) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME, organizationName + reportType)
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

    // 세입 등록, 이미 존재할 경우 업데이트
    @Transactional
    public Long saveTaxIncome(TaxIncomeSummaryRequestDto requestDto, String registerNumber){
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다.")); // 현재 기관 확인
        TaxIncomeSummary taxIncomeSummary = organization.getTaxIncomeSummary(); // 기관에 등록된 요약본 확인
        if (taxIncomeSummary == null) { // 널이면?
            TaxIncomeSummary newIncomeSummary = modelMapper.map(requestDto, TaxIncomeSummary.class); // DTO -> Entity
            incomeSummaryRepository.save(newIncomeSummary);
            organization.setTaxIncomeSummary(newIncomeSummary); // 저장 후 등록
            return newIncomeSummary.getId();
        } else { // 널 아니면
            modelMapper.map(requestDto, taxIncomeSummary); // 갱신
            return taxIncomeSummary.getId();
        }
    }

    // 세입 조회
    @Transactional(readOnly = true)
    public TaxIncomeSummaryResponseDto lookupTaxIncome(String registerNumber){
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다."));
        return modelMapper.map(organization.getTaxIncomeSummary(), TaxIncomeSummaryResponseDto.class);
    }

    // 세출 등록, 이미 존재할 경우 업데이트
    @Transactional
    public Long saveTaxOutcome(TaxOutcomeSummaryRequestDto requestDto, String registerNumber){
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다.")); // 현재 기관 확인
        TaxOutcomeSummary taxOutcomeSummary = organization.getTaxOutcomeSummary(); // 기관에 등록된 요약본 확인
        if (taxOutcomeSummary == null) { // 널이면?
            TaxOutcomeSummary newOutcomeSummary = modelMapper.map(requestDto, TaxOutcomeSummary.class); // DTO -> Entity
            outcomeSummaryRepository.save(newOutcomeSummary);
            organization.setTaxOutcomeSummary(newOutcomeSummary); // 저장 후 등록
            return newOutcomeSummary.getId();
        } else { // 널 아니면
            modelMapper.map(requestDto, taxOutcomeSummary); // 갱신
            return taxOutcomeSummary.getId();
        }
    }

    // 세출 조회
    @Transactional(readOnly = true)
    public TaxOutcomeSummaryResponseDto lookupTaxOutcome(String registerNumber){
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다."));
        return modelMapper.map(organization.getTaxOutcomeSummary(), TaxOutcomeSummaryResponseDto.class);
    }

    // 활동 등록
    @Transactional
    public ActivityResponseDto saveActivity(ActivityRequestDto activityDto, String registerNumber){
        Activity activity = modelMapper.map(activityDto, Activity.class);
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다."));
        activity.setOrganization(organization);

        Activity newActivity = activityRepository.save(activity);
        return this.modelMapper.map(newActivity, ActivityResponseDto.class);
    }

    // 활동 조회
    @Transactional(readOnly = true)
    public List<ActivityResponseDto> lookupActivity(String registerNumber){
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다."));
        return organization.getActivityList().stream()
                .map(activity -> modelMapper.map(activity, ActivityResponseDto.class))
                .collect(Collectors.toList());
    }

    // 캠페인 등록
    @Transactional
    public CampaignResponseDto saveCampaign(CampaginRequestDto campaginDto, String registerNumber){
        Campaign campaign = modelMapper.map(campaginDto, Campaign.class);
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다."));
        campaign.setOrganization(organization);

        Campaign newCampaign = campaignRepository.save(campaign);
        return this.modelMapper.map(newCampaign, CampaignResponseDto.class);
    }

    // 캠페인 조회
    @Transactional(readOnly = true)
    public List<CampaignResponseDto> lookupCampiagn(String registerNumber){
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다."));
        return organization.getCampaignList().stream()
                .map(campaign -> modelMapper.map(campaign, CampaignResponseDto.class))
                .collect(Collectors.toList());
    }

    // 캠페인 지출 내역 등록
    @Transactional
    public CampaignCostResponseDto saveCampaignCost(CampaignCostRequestDto campaignCostDto, String registerNumber){
        CampaignCost campaignCost = modelMapper.map(campaignCostDto, CampaignCost.class);
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다."));
        campaignCost.setOrganization(organization);

        CampaignCost newCampaignCost = costRepository.save(campaignCost);
        return this.modelMapper.map(newCampaignCost, CampaignCostResponseDto.class);
    }

    // 캠페인 지출 내역 조회
    @Transactional(readOnly = true)
    public List<CampaignCostResponseDto> lookupCampiagnCost(String registerNumber){
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다."));
        return organization.getCampaignCostList().stream()
                .map(campaignCost -> modelMapper.map(campaignCost, CampaignCostResponseDto.class))
                .collect(Collectors.toList());
    }

    public String findRegisterNumberByEmailAndName(String email, String name) {
        return organizationRepository.findByEmailAndName(email, name)
                .orElseThrow(() -> new IllegalArgumentException("기관 이름, 이메일과 일치하는 정보가 없습니다."))
                .getRegisterNumber();
    }

    public String findPassword(String registerNumber) {
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 존재하지 않습니다."));
        organization.setPassword(RandomStringUtils.randomAlphanumeric(10));
        return organizationRepository.save(organization).getPassword();
    }
}
