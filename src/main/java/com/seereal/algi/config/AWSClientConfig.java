package com.seereal.algi.config;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSClientConfig {
    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                                    .withCredentials(new ProfileCredentialsProvider())
                                    .withRegion(Regions.AP_NORTHEAST_2)
                                    .build();
    }
}
