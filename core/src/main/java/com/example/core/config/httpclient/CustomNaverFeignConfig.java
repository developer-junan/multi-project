package com.example.core.config.httpclient;

import com.example.core.exception.ApiException;
import com.example.core.exception.ErrorTypeEnum;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class CustomNaverFeignConfig {

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    @Bean
    Logger.Level feignNaverLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor naverRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            requestTemplate.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            requestTemplate.header("X-Naver-Client-Id", clientId);
            requestTemplate.header("X-Naver-Client-Secret", clientSecret);
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {

        return (methodKey, response) -> {
            int status = response.status();
            String message = response.body().toString();
            if (status != HttpStatus.OK.value()) {
                throw new ApiException(status, ErrorTypeEnum.ERROR_0003, message);
            }

            return new RuntimeException(message);
        };
    }
}