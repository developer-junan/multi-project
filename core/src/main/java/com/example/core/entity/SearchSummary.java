package com.example.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "search_summary")
public class SearchSummary {

    @Id
    private String keyword;

    @Column
    private long count;
}