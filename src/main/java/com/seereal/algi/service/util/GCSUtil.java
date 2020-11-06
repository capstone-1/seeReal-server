package com.seereal.algi.service.util;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.HttpMethod;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.seereal.algi.config.constant.GCSConstants.*;

@Component
@RequiredArgsConstructor
public class GCSUtil {
    private final Storage storage;

    public String parseS3Url(URL presignedUrl) {
        // Define resource
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(BUCKET_NAME, presignedUrl.getPath())).build();
        return storage.signUrl(blobInfo, 15, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature()).toExternalForm();
    }

    public String generateURL(String prefix, String id, String key) {
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(BUCKET_NAME, prefix + id + key)).build();
        return storage.signUrl(blobInfo, 15, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature()).toExternalForm();
//        return String.format("https://capstone-seereal.s3.ap-northeast-2.amazonaws.com/%s%s%s", prefix, id, key);
    }

    public URL getPresignedUrlForDonation(String donationName, String fileType) {

        return getPresignedUrl(DONATION_PREFIX+ donationName + fileType);
//        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME,  DONATION_PREFIX+ donationName + fileType)
//                .withMethod(HttpMethod.PUT)
//                .withExpiration(getExpiration());
//        generatePresignedUrlRequest.addRequestParameter(Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());
//        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
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
        // Define Resource
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(BUCKET_NAME, key)).build();

        // Generate Signed URL
        Map<String, String> extensionHeaders = new HashMap<>();
        extensionHeaders.put("Content-Type", "application/octet-stream");

        return storage.signUrl(
                        blobInfo,
                        15,
                        TimeUnit.MINUTES,
                        Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
                        Storage.SignUrlOption.withExtHeaders(extensionHeaders),
                        Storage.SignUrlOption.withV4Signature());

//        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME, key)
//                .withMethod(HttpMethod.PUT)
//                .withExpiration(getExpiration());
//        generatePresignedUrlRequest.addRequestParameter(Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());
//        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);
    }
    private Date getExpiration() {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + 1000 * 60 * 5);
        return expiration;
    }
}
