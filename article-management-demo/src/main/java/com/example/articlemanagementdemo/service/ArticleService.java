package com.example.articlemanagementdemo.service;

import com.example.articlemanagementdemo.dto.ArticleRequest;
import com.example.articlemanagementdemo.dto.ArticleResponse;
import com.example.articlemanagementdemo.entity.Article;
import com.example.articlemanagementdemo.exception.DataNotFoundException;
import com.example.articlemanagementdemo.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleResponse create(ArticleRequest articleRequest) {
        Article article = articleRepository.save(mapRequestModelToArticleEntity(articleRequest));
        return mapEntityToResponse(article);
    }

    public ArticleResponse findById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new DataNotFoundException("Article not found with this id:" + articleId));
        return mapEntityToResponse(article);
    }

    public List<ArticleResponse> findAll() {
        List<Article> articles = articleRepository.findAll();

        return articles.stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    public List<Article> search(Specification<Article> spec) {
        return articleRepository.findAll(spec);
    }

    public ArticleResponse update(Long articleId, ArticleRequest articleRequest) {
        Article existArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new DataNotFoundException("Article not found with this id:" + articleId));
        existArticle.setArticleContent(articleRequest.getArticleContent());
        existArticle.setTitle(articleRequest.getTitle());
        existArticle.setAuthor(articleRequest.getAuthor());
        existArticle.setStarCount(articleRequest.getStarCount());
        Article updatedArticle = articleRepository.save(existArticle);
        return mapEntityToResponse(updatedArticle);
    }

    public void delete(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    private ArticleResponse mapEntityToResponse(Article article) {
        return ArticleResponse.builder()
                .articleContent(article.getArticleContent())
                .author(article.getAuthor())
                .id(article.getId())
                .publishDate(article.getPublishDate())
                .reviews(article.getReviews())
                .starCount(article.getStarCount())
                .title(article.getTitle())
                .build();
    }

    private Article mapRequestModelToArticleEntity(ArticleRequest articleRequest) {
        Article article = new Article();
        article.setArticleContent(articleRequest.getArticleContent());
        article.setAuthor(articleRequest.getAuthor());
        article.setTitle(articleRequest.getTitle());
        article.setStarCount(articleRequest.getStarCount());
        return article;
    }
}
