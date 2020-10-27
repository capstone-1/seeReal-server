package com.seereal.algi.service.util;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

import static com.seereal.algi.config.constant.S3Constants.*;

@Component
@RequiredArgsConstructor
public class S3Util {
    private final AmazonS3 s3Client;

    public String parseS3Url(URL presignedUrl) {
        return presignedUrl.getProtocol() + "://" + presignedUrl.getHost() + presignedUrl.getPath();
    }

    public String generateURL(String prefix, String id, String key) {
        return String.format("https://capstone-seereal.s3.ap-northeast-2.amazonaws.com/%s%s%s", prefix, id, key);
    }

    public URL getPresignedUrlForDonation(String donationName, String fileType) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME,  DONATION_PREFIX+ donationName + fileType)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getExpiration());
        generatePresignedUrlRequest.addRequestParameter(Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());
        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }

    public URL getPresignedUrlForCampaign(Long id, String fileType) {
        return getPresignedUrl(CAMPAIGN_PREFIX + id + fileType);
    }

    public URL getPresignedUrlForRegularDonation(Long id, String fileType) {
        return getPresignedUrl(DONATION_PREFIX + id + fileType);
    }

    public URL getPresignedUrlForOrganization(String registerNumber, String reportType) {
        return getPresignedUrl(registerNumber + reportType);
    }

    private URL getPresignedUrl(String key) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME, key)
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
