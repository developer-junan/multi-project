package com.example.core.repository.httpclient;

import com.example.core.config.httpclient.CustomNaverFeignConfig;
import com.example.core.dto.NaverSearchResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naver-api", url = "https://openapi.naver.com/v1/search/blog.json", configuration = CustomNaverFeignConfig.class)
public interface NaverSearchHttpClient{

    @GetMapping
    NaverSearchResult searchBlogByQuery(@RequestParam(name = "query") String query,
                                        @RequestParam(name = "sort", required = false) String sort,
                                        @RequestParam(name = "start", required = false) int start,
                                        @RequestParam(name = "display", required = false) int display);
}