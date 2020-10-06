package com.seereal.algi.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.seereal.algi.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
}
