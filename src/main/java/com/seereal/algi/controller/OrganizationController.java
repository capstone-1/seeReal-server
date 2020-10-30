package com.seereal.algi.controller;

import com.seereal.algi.dto.activity.ActivityDto;
import com.seereal.algi.dto.campaign.CampaignDto;
import com.seereal.algi.dto.campaigncost.CampaignCostDto;
import com.seereal.algi.dto.organization.OrganizationSignUpRequestDto;
import com.seereal.algi.dto.taxincome.TaxIncomeSummaryRequestDto;
import com.seereal.algi.dto.taxoutcome.TaxOutcomeSummaryRequestDto;
import com.seereal.algi.security.token.JwtPostAuthorizationToken;
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
    public ResponseEntity<String> getPresignedUrlForBusinessReport(Authentication authentication) {
        JwtPostAuthorizationToken token = (JwtPostAuthorizationToken) authentication;
        return new ResponseEntity<>(organizationService.getPresignedUrlForBusinessReport(token.getOrganizationContext().getUsername()), HttpStatus.OK);
    }

    @GetMapping("/tax-report-presigned-url")
    public ResponseEntity<String> getPresignedUrlFortaxReport(Authentication authentication) {
        JwtPostAuthorizationToken token = (JwtPostAuthorizationToken) authentication;
        return new ResponseEntity<>(organizationService.getPresignedUrlForTaxReport(token.getOrganizationContext().getUsername()), HttpStatus.OK);
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
    public ResponseEntity<?> uploadTaxIncome(@RequestBody TaxIncomeSummaryRequestDto requestDto) {
        return ResponseEntity.ok(organizationService.saveTaxIncome(requestDto, "12345678"));
    }
//    public ResponseEntity<?> uploadTaxIncome(@RequestBody TaxIncomeSummaryRequestDto requestDto, Authentication authentication){
//        JwtPostAuthorizationToken token = (JwtPostAuthorizationToken) authentication;
//        System.out.println(token.getOrganizationContext().getUsername());
//        return ResponseEntity.ok(organizationService.saveTaxIncome(requestDto, token.getOrganizationContext().getUsername()));
//    }

    @PostMapping("/upload/tax-outcome")
    public ResponseEntity<?> uploadCampaign(@RequestBody TaxOutcomeSummaryRequestDto requestDto) {
        return ResponseEntity.ok(organizationService.saveTaxOutcome(requestDto, "12345678"));
    }
//    public ResponseEntity<?> uploadCampaign(@RequestBody TaxOutcomeSummaryRequestDto requestDto, Authentication authentication){
//        JwtPostAuthorizationToken token = (JwtPostAuthorizationToken) authentication;
//        System.out.println(token.getOrganizationContext().getUsername());
//        return ResponseEntity.ok(organizationService.saveTaxOutcome(requestDto, token.getOrganizationContext().getUsername()));
//    }

    @PostMapping("/upload/activity")
    public ResponseEntity<?> uploadActivity(@RequestBody ActivityDto.RequestList requestDto){
        return ResponseEntity.ok(organizationService.saveActivity(requestDto, "12345678"));
    }

    @PostMapping("/upload/campaign")
    public ResponseEntity<?> uploadCampaign(@RequestBody CampaignDto.RequestList requestDto){
        return ResponseEntity.ok(organizationService.saveCampaign(requestDto, "12345678"));
    }

    @PostMapping("/upload/campaign-cost")
    public ResponseEntity<?> uploadCampaignCost(@RequestBody CampaignCostDto.RequestList requestDto){
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
        return ResponseEntity.ok(organizationService.lookupCampaign(registerNumber));
    }

    @GetMapping("/lookup/campaign-cost")
    public ResponseEntity<?> findAllCampaignCost() {
        //TODO: Get Register Number from Session
        String registerNumber = "12345678";
        return ResponseEntity.ok(organizationService.lookupCampiagnCost(registerNumber));
    }

    @GetMapping("/jwt-test")
    public String jwtTest(Authentication authentication) {
        JwtPostAuthorizationToken token = (JwtPostAuthorizationToken)authentication;
        return token.getOrganizationContext().getUsername();
    }

    @GetMapping("/find/register-number")
    public ResponseEntity<String> findRegisterNumberByEmail(@RequestParam String email, @RequestParam String name) {
        return ResponseEntity.ok(organizationService.findRegisterNumberByEmailAndName(email, name));
    }

    @GetMapping("/find/password")
    public ResponseEntity<String> findPassword(@RequestParam String registerNumber) {
        return ResponseEntity.ok(organizationService.findPassword(registerNumber));
    }
}
