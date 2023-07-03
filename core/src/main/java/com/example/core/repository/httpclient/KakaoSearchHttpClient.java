package com.example.core.repository.httpclient;

import com.example.core.config.httpclient.CustomKakaoFeignConfig;
import com.example.core.dto.KakaoSearchResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-api", url = "https://dapi.kakao.com/v2/search", configuration = CustomKakaoFeignConfig.class)
public interface KakaoSearchHttpClient {

    @GetMapping(value = "/blog")
    KakaoSearchResult searchBlogByQuery(@RequestParam(name = "query") String query,
                                        @RequestParam(name = "sort", required = false) String sort,
                                        @RequestParam(name = "page", required = false) int page,
                                        @RequestParam(name = "page", required = false) int size);
}