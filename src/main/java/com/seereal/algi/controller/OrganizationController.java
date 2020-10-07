package com.seereal.algi.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.seereal.algi.dto.organization.OrganizationLoginDto;
import com.seereal.algi.dto.organization.OrganizationSignUpRequestDto;
import com.seereal.algi.security.token.JwtPostAuthorizationToken;
import com.seereal.algi.security.token.LoginPostAuthorizationToken;
import com.seereal.algi.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/organization/business-report-presigned-url")
    public String getPresignedUrlForBusinessReport(Authentication authentication) {
        JwtPostAuthorizationToken token = (JwtPostAuthorizationToken) authentication;
        return organizationService.getPresignedUrlForBusinessReport(token.getOrganizationContext().getUsername());
    }

    @GetMapping("/organization/tax-report-presigned-url")
    public String getPresignedUrlFortaxReport(Authentication authentication) {
        JwtPostAuthorizationToken token = (JwtPostAuthorizationToken) authentication;
        return organizationService.getPresignedUrlForTaxReport(token.getOrganizationContext().getUsername());
    }

    @PostMapping("/organization/signup")
    public ResponseEntity<String> signUp(@RequestBody OrganizationSignUpRequestDto requestDto) {
        String savedName = organizationService.signUp(requestDto);
        if (savedName == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(savedName, HttpStatus.OK);
    }

    @GetMapping("/organization/jwt-test")
    public String jwtTest(Authentication authentication) {
        JwtPostAuthorizationToken token = (JwtPostAuthorizationToken)authentication;
        return token.getOrganizationContext().getUsername().toString();
    }
}
