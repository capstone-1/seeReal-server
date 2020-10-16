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

import static com.seereal.algi.config.constant.S3Constants.BUCKET_NAME;
import static com.seereal.algi.config.constant.S3Constants.CAMPAIGN_PREFIX;

@Component
@RequiredArgsConstructor
public class S3Util {
    private final AmazonS3 s3Client;

    public String parseS3Url(URL presignedUrl) {
        return presignedUrl.getProtocol() + "://" + presignedUrl.getHost() + presignedUrl.getPath();
    }

    public URL getPresignedUrl(String campaignName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME, CAMPAIGN_PREFIX + campaignName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getExpiration());
        generatePresignedUrlRequest.addRequestParameter(Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());
        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }

    public URL getPresignedUrl(String organizationName, String reportType) {
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
