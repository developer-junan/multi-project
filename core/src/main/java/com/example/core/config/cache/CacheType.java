package com.example.core.config.cache;

import lombok.Getter;

@Getter
public enum CacheType {

    SEARCH_KEYWORD("keyword", 60),
    SEARCH_TRENDS("trends", 60);

    private final String cacheName;
    private final int expireAfterAccess;

    CacheType(String cacheName, int expireAfterAccess) {
        this.cacheName = cacheName;
        this.expireAfterAccess = expireAfterAccess;
    }
}