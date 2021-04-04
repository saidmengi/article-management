package com.example.articlemanagementdemo.controller;

import com.example.articlemanagementdemo.dto.ArticleRequest;
import com.example.articlemanagementdemo.dto.ArticleResponse;
import com.example.articlemanagementdemo.entity.Article;
import com.example.articlemanagementdemo.search.CustomRsqlVisitor;
import com.example.articlemanagementdemo.service.ArticleService;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@AllArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleResponse create(@RequestBody ArticleRequest articleRequest) {
        return articleService.create(articleRequest);
    }

    @GetMapping("/{articleId}")
    public ArticleResponse findById(@PathVariable("articleId") Long articleId) {
        return articleService.findById(articleId);
    }

    @GetMapping
    public List<ArticleResponse> findAll() {
        return articleService.findAll();
    }

    @GetMapping("/search")
    public List<Article> search(@RequestParam String criteria) {
        Node rootNode = new RSQLParser().parse(criteria);
        Specification<Article> spec = rootNode.accept(new CustomRsqlVisitor<>());
        return articleService.search(spec);
    }

    @PutMapping("/{articleId}")
    public ArticleResponse update(@PathVariable("articleId") Long articleId, @RequestBody ArticleRequest articleRequest) {
        return articleService.update(articleId, articleRequest);
    }

    @DeleteMapping("/{articleId}")
    public void delete(@PathVariable("articleId") Long articleId) {
        articleService.delete(articleId);
    }
}
