package com.seereal.algi.service;

import com.seereal.algi.dto.activity.ActivityDto;
import com.seereal.algi.dto.campaign.CampaignDto;
import com.seereal.algi.dto.campaigncost.CampaignCostDto;
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
import com.seereal.algi.service.util.GCSUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static com.seereal.algi.config.constant.GCSConstants.BUSINESS_REPORT_NAME;
import static com.seereal.algi.config.constant.GCSConstants.TAX_REPORT_NAME;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationService {
    private static Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    private final ActivityRepository activityRepository;
    private final CampaignRepository campaignRepository;
    private final CampaignCostRepository costRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final OrganizationRepository organizationRepository;
    private final TaxIncomeSummaryRepository incomeSummaryRepository;
    private final TaxOutcomeSummaryRepository outcomeSummaryRepository;
    private final GCSUtil GCSUtil;

    public String getPresignedUrlForBusinessReport(String registerNumber) {
        URL presignedUrl = GCSUtil.getPresignedUrlForOrganization(registerNumber, BUSINESS_REPORT_NAME);
        //DB 저장
//        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
//                                                            .orElseThrow(() -> new IllegalArgumentException("Organization Not Found!"));
//        organization.setBusinessReportLink(GCSUtil.parseS3Url(presignedUrl));
//        organizationRepository.save(organization);

        return presignedUrl.toExternalForm();
    }

    public String getPresignedUrlForTaxReport(String registerNumber) {
        URL presignedUrl = GCSUtil.getPresignedUrlForOrganization(registerNumber, TAX_REPORT_NAME);
        //DB 저장
//        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
//                                                            .orElseThrow(() -> new IllegalArgumentException("Organization Not Found!"));
//        organization.setTaxReportLink(GCSUtil.parseS3Url(presignedUrl));
//        organizationRepository.save(organization);
        return presignedUrl.toExternalForm();

    }

    public String signUp(OrganizationSignUpRequestDto requestDto) {
        if (organizationRepository.findByRegisterNumber(requestDto.getRegisterNumber()).isPresent()) {
            return null;
        }
        Organization organization = requestDto.toEntity();
        organization.passwordEncode(passwordEncoder);
        return organizationRepository.save(organization).getName();
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
    public boolean saveActivity(ActivityDto.RequestList activityDto, String registerNumber){
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다."));
        activityDto.getActivityRequests().forEach(dto -> {
            Activity activity = modelMapper.map(dto, Activity.class);
            activity.setOrganization(organization);
            activityRepository.save(activity);
        });

        return true;
    }

    // 활동 조회
    @Transactional(readOnly = true)
    public List<ActivityDto.Response> lookupActivity(String registerNumber){
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다."));
        return organization.getActivityList().stream()
                .map(activity -> modelMapper.map(activity, ActivityDto.Response.class))
                .collect(Collectors.toList());
    }

    // 캠페인 등록
    @Transactional
    public boolean saveCampaign(CampaignDto.RequestList campaignDto, String registerNumber){
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다."));
        campaignDto.getCampaignRequests().forEach(dto -> {
            Campaign campaign = modelMapper.map(dto, Campaign.class);
            campaign.setOrganization(organization);
            campaignRepository.save(campaign);
        });

        return true;
    }

    // 캠페인 조회
    @Transactional(readOnly = true)
    public List<CampaignDto.Response> lookupCampaign(String registerNumber){
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다."));
        return organization.getCampaignList().stream()
                .map(campaign -> modelMapper.map(campaign, CampaignDto.Response.class))
                .collect(Collectors.toList());
    }

    // 캠페인 지출 내역 등록
    @Transactional
    public boolean saveCampaignCost(CampaignCostDto.RequestList campaignCostDto, String registerNumber){
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다."));

        campaignCostDto.getCampaignCostRequests().forEach(dto -> {
            CampaignCost campaignCost = modelMapper.map(dto, CampaignCost.class);
            campaignCost.setOrganization(organization);
            costRepository.save(campaignCost);
        });

        return true;
    }

    // 캠페인 지출 내역 조회
    @Transactional(readOnly = true)
    public List<CampaignCostDto.Response> lookupCampiagnCost(String registerNumber){
        Organization organization = organizationRepository.findByRegisterNumber(registerNumber)
                .orElseThrow(() -> new IllegalArgumentException("기관 정보가 검색되지 않습니다."));
        return organization.getCampaignCostList().stream()
                .map(campaignCost -> modelMapper.map(campaignCost, CampaignCostDto.Response.class))
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
        String randNum = RandomStringUtils.randomAlphanumeric(10);
        organization.setPassword(passwordEncoder.encode(randNum));
        organizationRepository.save(organization);
        return randNum;
    }
}
