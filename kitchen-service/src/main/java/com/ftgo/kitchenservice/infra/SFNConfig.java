package com.ftgo.kitchenservice.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sfn.SfnClient;

@Configuration
public class SFNConfig {
    @Value("${cloud.aws.region.static}")
    private String defaultRegion;

    @Value("${cloud.aws.credentials.profile-name}")
    private String profileName;

    @Bean
    SfnClient sfnClient() {
        return SfnClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create(profileName))
                .region(Region.of(defaultRegion))
                .build();
    }
}
