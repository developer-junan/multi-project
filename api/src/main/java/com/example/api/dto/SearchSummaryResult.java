package com.example.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchSummaryResult {
    private String keyword;
    private long count;
}