package com.app.quest.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.app.quest.utils.ProcessUtils;
import io.findify.s3mock.S3Mock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Slf4j
@Configuration
public class S3Config {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.mock.port}")
    private int port;

    // XXX: dirty detour
    private S3Mock s3Mock;

    // XXX: circular bean injection
//    @Bean
//    public S3Mock s3MockDev() {
//        return new S3Mock.Builder()
//                .withPort(port)
//                .withInMemoryBackend()
//                .build();
//    }

    @Profile("dev")
    @PostConstruct
    public void startS3Mock() throws IOException {
        s3Mock = new S3Mock.Builder().withPort(port).withInMemoryBackend().build();
        port = ProcessUtils.isRunningPort(port) ? ProcessUtils.findAvailableRandomPort() : port;
        s3Mock.start();
//        this.s3MockDev().start();
        log.info("인메모리 S3 Mock 서버 시작 : port: {}", port);
    }

    @Profile("dev")
    @PreDestroy
    public void destroyS3Mock() {
        s3Mock.shutdown();
//        this.s3MockDev().shutdown();
        log.info("인메모리 S3 Mock 서버 종료 : port: {}", port);
    }

    @Bean
    @Primary
    public AmazonS3 amazonS3Client() {
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(getUri(), region);
        AmazonS3 client = AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();
        client.createBucket(bucket);
        return client;
    }

    private String getUri() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .build()
                .toUriString();
    }
}
