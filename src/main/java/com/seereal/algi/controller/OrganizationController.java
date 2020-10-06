package com.seereal.algi.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.seereal.algi.dto.organization.OrganizationSignUpRequestDto;
import com.seereal.algi.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/organization/business-report-presigned-url")
    public String getPresignedUrlForBusinessReport() {
        //TODO: Get Organization Name from Session
        String organizationName = "test123";
        return organizationService.getPresignedUrlForBusinessReport(organizationName);
    }

    @GetMapping("/organization/tax-report-presigned-url")
    public String getPresignedUrlFortaxReport() {
        //TODO: Get Organization Name from Session
        String organizationName = "test123";
        return organizationService.getPresignedUrlForTaxReport(organizationName);
    }

    @PostMapping("/organization/signup")
    public ResponseEntity<String> signUp(@RequestBody OrganizationSignUpRequestDto requestDto) {
        String savedName = organizationService.signUp(requestDto);
        if (savedName == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(savedName, HttpStatus.OK);
    }
}
