package com.seereal.algi.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.seereal.algi.model.*;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

@Service
public class OrganizationService {
    private static String BUCKET_NAME = "capstone-seereal";
    private static String BUSINESS_REPORT_NAME = "/business-report";
    private static String TAX_REPORT_NAME = "/tax_report";

    private final OrganizationRepository organizationRepository;
    private final AmazonS3 s3Client;

    public OrganizationService(OrganizationRepository organizationRepository, AmazonS3 s3Client) {
        this.organizationRepository = organizationRepository;
        this.s3Client = s3Client;
    }

    public String getPresignedUrlForBusinessReport(String organizationName) {
        URL presignedUrl = getPresignedUrl(organizationName, BUSINESS_REPORT_NAME);
        //DB 저장
        Organization organization = organizationRepository.findByName(organizationName)
                                                            .orElseThrow(() -> new IllegalArgumentException("Organization Not Found!"));
        organization.setBusinessReportLink(parseS3Url(presignedUrl));
        organizationRepository.save(organization);

        return presignedUrl.toExternalForm();
    }

    public String getPresignedUrlForTaxReport(String organizationName) {
        URL presignedUrl =  getPresignedUrl(organizationName, TAX_REPORT_NAME);
        //DB 저장
        Organization organization = organizationRepository.findByName(organizationName)
                                                            .orElseThrow(() -> new IllegalArgumentException("Organization Not Found!"));
        organization.setTaxReportLink(parseS3Url(presignedUrl));
        organizationRepository.save(organization);
        return presignedUrl.toExternalForm();

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
}
