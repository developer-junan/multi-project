package com.example.api.service;

import com.example.api.util.SortTypeEnum;
import com.example.core.dto.KakaoSearchResult;
import com.example.core.dto.NaverSearchResult;
import com.example.core.exception.ApiException;
import com.example.core.repository.httpclient.KakaoSearchHttpClient;
import com.example.core.repository.httpclient.NaverSearchHttpClient;
import feign.FeignException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "feign.client.config.default.loggerLevel=Full")
class SearchServiceTest {

    @Autowired
    NaverSearchHttpClient naverSearchHttpClient;

    @Autowired
    KakaoSearchHttpClient kakaoSearchHttpClient;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("카카오 블로그 API 연동 성공")
    @Disabled
    void successSearchKakaoBlog() {
        String query = "겨울";
        String sort = SortTypeEnum.ACCURACY.getKakaoType();
        int page = 1;
        int size = 10;

        KakaoSearchResult result = kakaoSearchHttpClient.searchBlogByQuery(query, sort, page, size);

        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("카카오 블로그 API 연동 실패")
    @Disabled
    void failSearchKakaBlog() {
        String query = "겨울";
        String sort = "invalidValue";
        int page = 1;
        int size = 10;

        Assertions.assertThrows(FeignException.class, () -> {
            kakaoSearchHttpClient.searchBlogByQuery(query, sort, page, size);
        });
    }

    @Test
    @DisplayName("네이버 블로그 API 연동 성공")
    @Disabled
    void successSearchNaverBlog() {
        String query = "겨울";
        String sort = SortTypeEnum.ACCURACY.getNaverType();
        int start = 1;
        int display = 10;

        NaverSearchResult result = naverSearchHttpClient.searchBlogByQuery(query, sort, start, display);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(start, result.getStart());
        Assertions.assertEquals(display, result.getDisplay());
    }

    @Test
    @DisplayName("네이버 블로그 API 연동 실패")
    @Disabled
    void failSearchNaverBlog() {
        String query = "겨울";
        String sort = "invalidValue";
        int start = 1;
        int display = 10;

        Assertions.assertThrows(ApiException.class, () -> {
            naverSearchHttpClient.searchBlogByQuery(query, sort, start, display);
        });
    }
}