package com.example.api.service;

import com.example.api.dto.SearchResult;
import com.example.api.dto.SearchSummaryResult;
import com.example.api.util.SortTypeEnum;
import com.example.core.dto.KakaoSearchResult;
import com.example.core.dto.NaverSearchResult;
import com.example.core.entity.SearchSummary;
import com.example.core.repository.SearchRepository;
import com.example.core.repository.httpclient.KakaoSearchHttpClient;
import com.example.core.repository.httpclient.NaverSearchHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {
    private final KakaoSearchHttpClient kakaoSearchHttpClient;
    private final NaverSearchHttpClient naverSearchHttpClient;

    private final SearchRepository searchRepository;

    private final Map<String, Long> concurrentHashMap = new ConcurrentHashMap<>();

    @Cacheable(cacheNames = "trends")
    @Transactional(readOnly = true)
    public List<SearchSummaryResult> findSearchTrends() {

        List<SearchSummary> trends = searchRepository.findTop10ByOrderByCountDesc();

        return trends.stream().map(summary -> SearchSummaryResult.builder()
                .keyword(summary.getKeyword())
                .count(summary.getCount())
                .build()).collect(Collectors.toList());
    }

    //@Cacheable(value = "keyword", key = "#keyword")
    public Page<SearchResult> searchBlogByQuery(String keyword, String blogUrl, SortTypeEnum sortType, Pageable pageable) {
        String specificBlogTargetQuery = getSpecificBlogTargetQuery(blogUrl, keyword);

        storeDefaultCountInCache(keyword);

        long count = storeSearchKeywordCountInCache(keyword);
        storeSearchKeyWordCountInDB(keyword, count);

        String sort = (sortType == null) ? SortTypeEnum.ACCURACY.getKakaoType() : sortType.getKakaoType();

        try {

            keyword = (specificBlogTargetQuery.equals(Strings.EMPTY) ? keyword : specificBlogTargetQuery);
            KakaoSearchResult kakaoSearchResult = kakaoSearchHttpClient.searchBlogByQuery(keyword, sort, pageable.getPageNumber(), pageable.getPageSize());
            return new PageImpl<>(getSearchResultByKakao(kakaoSearchResult), pageable, kakaoSearchResult.getMeta().getTotalCount());

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);

            sort = (sortType == null) ? SortTypeEnum.ACCURACY.getNaverType() : sortType.getNaverType();
            NaverSearchResult naverSearchResult = naverSearchHttpClient.searchBlogByQuery(keyword, sort, pageable.getPageNumber(), pageable.getPageSize());
            return new PageImpl<>(getSearchResultByNaver(naverSearchResult), pageable, naverSearchResult.getTotal());
        }
    }

    private String getSpecificBlogTargetQuery(String blogUrl, String keyword) {
        if (!ObjectUtils.isEmpty(blogUrl)) {
            return String.format("%s %s", blogUrl, keyword);
        }

        return Strings.EMPTY;
    }

    private void storeDefaultCountInCache(String keyword) {
        if (ObjectUtils.isEmpty(concurrentHashMap.get(keyword))) {
            SearchSummary summary = searchRepository.findById(keyword).orElse(null);

            if (ObjectUtils.isEmpty(summary)) {
                concurrentHashMap.putIfAbsent(keyword, 0L);
            } else {
                concurrentHashMap.putIfAbsent(keyword, summary.getCount());
            }
        }
    }

    private Long storeSearchKeywordCountInCache(String keyword) {
        return concurrentHashMap.merge(keyword, 1L, Long::sum);
    }

    @Transactional
    public void storeSearchKeyWordCountInDB(String keyword, long count) {
        SearchSummary summary = new SearchSummary();
        summary.setKeyword(keyword);
        summary.setCount(count);
        searchRepository.save(summary);
    }

    private List<SearchResult> getSearchResultByKakao(KakaoSearchResult kakaoSearchResult) {
        return kakaoSearchResult.getDocuments().stream().map(document -> SearchResult.builder()
                        .content(document.getContents())
                        .url(document.getUrl())
                        .registeredDt(document.getDateTime())
                        .title(document.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    private List<SearchResult> getSearchResultByNaver(NaverSearchResult naverSearchResult) {
        return naverSearchResult.getItems().stream().map(item -> SearchResult.builder()
                        .content(item.getDescription())
                        .url(item.getLink())
                        .registeredDt(item.getPostdate())
                        .title(item.getTitle())
                        .build())
                .collect(Collectors.toList());
    }
}