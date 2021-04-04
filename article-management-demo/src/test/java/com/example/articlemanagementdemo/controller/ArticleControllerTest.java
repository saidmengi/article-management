package com.example.articlemanagementdemo.controller;

import com.example.articlemanagementdemo.dto.ArticleRequest;
import com.example.articlemanagementdemo.dto.ArticleResponse;
import com.example.articlemanagementdemo.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ArticleService articleService;

    @WithMockUser(value = "test")
    @Test
    void createTest() throws Exception {
        ArticleRequest request = new ArticleRequest("title","author", "articleContent",1);
        ArticleResponse response = new ArticleResponse(1L,"title","author", "articleContent", LocalDateTime.now(),1, null);
        doReturn(response).when(articleService).create(any());

        mockMvc.perform(post("/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.articleContent", is("articleContent")))
                .andExpect(jsonPath("$.starCount", is(1)));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
