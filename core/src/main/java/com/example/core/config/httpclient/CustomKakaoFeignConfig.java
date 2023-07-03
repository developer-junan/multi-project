package com.example.core.config.httpclient;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class CustomKakaoFeignConfig {

    @Value("${kakao.token}")
    private String token;

    @Bean
    Logger.Level feignKakaoLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor kakaoRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            requestTemplate.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            requestTemplate.header(HttpHeaders.AUTHORIZATION, String.format("KakaoAK %s", token));
        };
    }
}