package com.example.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class SearchResult {
    private String title;
    private String content;
    private String url;
    private Date registeredDt;
}