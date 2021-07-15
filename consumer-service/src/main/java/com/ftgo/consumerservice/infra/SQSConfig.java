package com.ftgo.consumerservice.infra;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SQSConfig {
    @Value("${cloud.aws.sqs.region}")
    private String sqsRegion;

    @Value("${cloud.aws.credentials.profile-name}")
    private String profileName;

    @Bean
    AmazonSQSAsync amazonSQS() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withRegion(sqsRegion)
                .withCredentials(new ProfileCredentialsProvider(profileName))
                .build();
    }

}
