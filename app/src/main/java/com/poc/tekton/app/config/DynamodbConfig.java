package com.poc.tekton.app.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.ConversionSchemas;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverterFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamodbConfig {

    @Value("${aws.dynamodb.target-region}")
    private String targetRegion;

    @Value("${aws.dynamodb.target-endpoint}")
    private String targetEndPoint;

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        return DynamoDBMapperConfig.builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.EVENTUAL)
                .withPaginationLoadingStrategy(DynamoDBMapperConfig.PaginationLoadingStrategy.LAZY_LOADING)
                .withBatchWriteRetryStrategy(DynamoDBMapperConfig.DefaultBatchWriteRetryStrategy.INSTANCE)
                .withBatchLoadRetryStrategy(DynamoDBMapperConfig.DefaultBatchLoadRetryStrategy.INSTANCE)
                .withTypeConverterFactory(DynamoDBTypeConverterFactory.standard())
                .withConversionSchema(ConversionSchemas.V2_COMPATIBLE)
                .build();
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig config) {
        return new DynamoDBMapper(amazonDynamoDB, config);
    }

    @Bean
    @Qualifier("amazonDynamoDB")
    public AmazonDynamoDB amazonDynamoDB(final AWSCredentialsProvider awsCredentialsProvider) {
        return AmazonDynamoDBClientBuilder.standard()
                .withClientConfiguration(clientConfig())
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(this.targetEndPoint,
                        this.targetRegion))
                .withCredentials(awsCredentialsProvider).build();
    }

    private ClientConfiguration clientConfig() {
        return new ClientConfiguration();
    }
}