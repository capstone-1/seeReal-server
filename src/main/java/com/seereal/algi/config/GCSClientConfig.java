package com.seereal.algi.config;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.seereal.algi.config.constant.GCSConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GCSClientConfig {

    @Bean
    public Storage storage() {
        return StorageOptions.newBuilder().setProjectId(GCSConstants.PROJECT_ID).build().getService();
    }
}
