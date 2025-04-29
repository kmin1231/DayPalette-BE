// config/S3Config.java

package com.khu.cloud.diary.config;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
 @Slf4j
 @Configuration
 public class S3Config {
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;
 
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;
 
    @Value("${cloud.aws.region.static}")
    private String region;
    
    // AWS SDK v2
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }
}