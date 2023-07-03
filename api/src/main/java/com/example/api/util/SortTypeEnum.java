package com.example.api.util;

import lombok.Getter;

@Getter
public enum SortTypeEnum {
    ACCURACY("accuracy", "sim"),
    RECENTLY("recency", "date");

    private String kakaoType;
    private String naverType;

    SortTypeEnum(String kakaoType, String naverType) {
        this.kakaoType = kakaoType;
        this.naverType = naverType;
    }
}