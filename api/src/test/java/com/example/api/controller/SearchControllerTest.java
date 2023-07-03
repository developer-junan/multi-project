package com.example.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SearchControllerTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() throws Exception {
        for (int i = 0; i < 10; i ++) {
            mvc.perform(MockMvcRequestBuilders.get("/search?keyword=카카오")
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    @Test
    void searchBlogByQuery() {
    }

    @Test
    @DisplayName("가장 많이 검색했던 검색어, 검색 횟수 조회")
    void findSearchTrends() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/search/trends")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].keyword").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].count").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].keyword").value("카카오"));
    }
}