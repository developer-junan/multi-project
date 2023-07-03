package com.example.core.repository;

import com.example.core.entity.SearchSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<SearchSummary, String> {
    List<SearchSummary> findTop10ByOrderByCountDesc();
}