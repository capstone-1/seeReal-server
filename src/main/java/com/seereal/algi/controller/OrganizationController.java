package com.seereal.algi.controller;

import com.seereal.algi.dto.activity.ActivityRequestDto;
import com.seereal.algi.dto.campaign.CampaginRequestDto;
import com.seereal.algi.dto.campaigncost.CampaignCostRequestDto;
import com.seereal.algi.dto.organization.OrganizationLoginDto;
import com.seereal.algi.dto.organization.OrganizationSignUpRequestDto;
import com.seereal.algi.dto.taxincome.TaxIncomeSummaryRequestDto;
import com.seereal.algi.dto.taxoutcome.TaxOutcomeSummaryRequestDto;
import com.seereal.algi.security.token.JwtPostAuthorizationToken;
import com.seereal.algi.security.token.LoginPostAuthorizationToken;
import com.seereal.algi.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/business-report-presigned-url")
    public String getPresignedUrlForBusinessReport() {
        //TODO: Get Organization Name from Session
        String organizationName = "test123";
        return organizationService.getPresignedUrlForBusinessReport(organizationName);
    }

    @GetMapping("/tax-report-presigned-url")
    public String getPresignedUrlFortaxReport() {
        //TODO: Get Organization Name from Session
        String organizationName = "test123";
        return organizationService.getPresignedUrlForTaxReport(organizationName);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody OrganizationSignUpRequestDto requestDto) {
        String savedName = organizationService.signUp(requestDto);
        if (savedName == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(savedName, HttpStatus.OK);
    }

    @PostMapping("/upload/tax-income")
    public ResponseEntity<?> uploadTaxIncome(@RequestBody TaxIncomeSummaryRequestDto requestDto){
        return ResponseEntity.ok(organizationService.saveTaxIncome(requestDto, "12345678"));
    }

    @PostMapping("/upload/tax-outcome")
    public ResponseEntity<?> uploadCampaign(@RequestBody TaxOutcomeSummaryRequestDto requestDto){
        return ResponseEntity.ok(organizationService.saveTaxOutcome(requestDto, "12345678"));
    }

    @PostMapping("/upload/activity")
    public ResponseEntity<?> uploadActivity(@RequestBody ActivityRequestDto requestDto){
        return ResponseEntity.ok(organizationService.saveActivity(requestDto, "12345678"));
    }

    @PostMapping("/upload/campaign")
    public ResponseEntity<?> uploadCampaign(@RequestBody CampaginRequestDto requestDto){
        return ResponseEntity.ok(organizationService.saveCampaign(requestDto, "12345678"));
    }

    @PostMapping("/upload/campaign-cost")
    public ResponseEntity<?> uploadCampaignCost(@RequestBody CampaignCostRequestDto requestDto){
        return ResponseEntity.ok(organizationService.saveCampaignCost(requestDto, "12345678"));
    }

    @GetMapping("/lookup/tax-income")
    public ResponseEntity<?> findTaxIncome(){
        //TODO: Get Register Number from Session
        String registerNumber = "12345678";
        return ResponseEntity.ok(organizationService.lookupTaxIncome(registerNumber));
    }

    @GetMapping("/lookup/tax-outcome")
    public ResponseEntity<?> findTaxOutcome(){
        //TODO: Get Register Number from Session
        String registerNumber = "12345678";
        //TODO: Service implementation
        return ResponseEntity.ok(organizationService.lookupTaxOutcome(registerNumber));
    }

    @GetMapping("/lookup/activity")
    public ResponseEntity<?> findAllActivity(){
        //TODO: Get Register Number from Session
        String registerNumber = "12345678";
        return ResponseEntity.ok(organizationService.lookupActivity(registerNumber));
    }

    @GetMapping("/lookup/campaign")
    public ResponseEntity<?> findAllCampaign(){
        //TODO: Get Register Number from Session
        String registerNumber = "12345678";
        return ResponseEntity.ok(organizationService.lookupCampiagn(registerNumber));
    }

    @GetMapping("/lookup/campaign-cost")
    public ResponseEntity<?> findAllCampaignCost() {
        //TODO: Get Register Number from Session
        String registerNumber = "12345678";
        return ResponseEntity.ok(organizationService.lookupCampiagnCost(registerNumber));
    }

    @PostMapping("/organization/login")
    public String login(@RequestBody OrganizationLoginDto requestDto, Authentication authentication) {
        LoginPostAuthorizationToken token = (LoginPostAuthorizationToken) authentication;
        return token.getOrganizationContext().toString();
    }

    @GetMapping("/organization/jwt-test")
    public String jwtTest(Authentication authentication) {
        JwtPostAuthorizationToken token = (JwtPostAuthorizationToken)authentication;
        return token.getOrganizationContext().getUsername().toString();
    }
}
