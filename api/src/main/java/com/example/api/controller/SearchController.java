package com.example.api.controller;

import com.example.api.dto.SearchResult;
import com.example.api.dto.SearchSummaryResult;
import com.example.api.service.SearchService;
import com.example.api.util.SortTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    public Page<SearchResult> searchBlogByQuery(@RequestParam String keyword,
                                                @RequestParam(required = false) String blogUrl,
                                                @RequestParam(required = false) SortTypeEnum sortType,
                                                @PageableDefault(page = 1, size = 10) Pageable pageable) {

        return searchService.searchBlogByQuery(keyword, blogUrl, sortType, pageable);
    }

    @GetMapping("/trends")
    public List<SearchSummaryResult> findSearchTrends() {
        return searchService.findSearchTrends();
    }
}
