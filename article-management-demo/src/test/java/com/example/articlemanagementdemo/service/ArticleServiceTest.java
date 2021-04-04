package com.example.articlemanagementdemo.service;

import com.example.articlemanagementdemo.dto.ArticleRequest;
import com.example.articlemanagementdemo.dto.ArticleResponse;
import com.example.articlemanagementdemo.entity.Article;
import com.example.articlemanagementdemo.repository.ArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @MockBean
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("Test create article success")
    void createTest() {
        Article article = new Article(1L, "title", "author",  "authorContent",LocalDateTime.now(),1,null);
        doReturn(article).when(articleRepository).save(any());
        ArticleRequest articleRequest = ArticleRequest.builder()
                .title("title")
                .author("author")
                .articleContent("authorContent")
                .starCount(1)
                .build();
        ArticleResponse returnedArticle = articleService.create(articleRequest);
        Assertions.assertNotNull(returnedArticle, "The saved article should not be null");
        Assertions.assertEquals(1, returnedArticle.getStarCount(), "The starCount should be 1");
    }

    @Test
    void findByIdTest() {
        Article article = new Article(1L, "title", "author",  "authorContent",LocalDateTime.now(),1,null);
        doReturn(Optional.of(article)).when(articleRepository).findById(1l);

        ArticleResponse returnedArticleResponse = articleService.findById(1L);
        Assertions.assertEquals(returnedArticleResponse.getId(), 1L, "The article returned was not the equel as the mock");
    }

    @Test
    void findAllTest() {
        Article article1 = new Article(1L, "title1", "author1",  "authorContent1",LocalDateTime.now(),1,null);
        Article article2 = new Article(2L, "title2", "author2",  "authorContent2",LocalDateTime.now(),1,null);
        doReturn(Arrays.asList(article1, article2)).when(articleRepository).findAll();

        List<ArticleResponse> articles = articleService.findAll();
        Assertions.assertEquals(2, articles.size(), "findAll should return 2 widgets");
    }
}
