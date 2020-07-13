package com.poc.tekton.app.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsCredentialsConfig {

    private final AwsCredentialsProperties awsCredentialsProperties;

    public AwsCredentialsConfig(final AwsCredentialsProperties awsCredentialsProperties) {
        this.awsCredentialsProperties = awsCredentialsProperties;
    }

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {

        return new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsCredentialsProperties.getAccessKey(),
                        awsCredentialsProperties.getSecretKey()));

    }
}
