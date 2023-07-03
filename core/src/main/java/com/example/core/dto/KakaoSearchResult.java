package com.example.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class KakaoSearchResult {

    private Meta meta;
    private List<Document> documents;

    @Getter
    public static class Meta {

        @JsonProperty("total_count")
        private int totalCount;

        @JsonProperty("pageable_count")
        private int pageableCount;

        @JsonProperty("is_end")
        private boolean isEnd;
    }

    @Getter
    public static class Document {
        private String title;
        private String contents;
        private String url;
        private String blogname;
        private String thumbnail;
        private Date dateTime;
    }
}