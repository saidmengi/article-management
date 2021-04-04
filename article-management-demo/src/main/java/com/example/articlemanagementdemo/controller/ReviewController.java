package com.example.articlemanagementdemo.controller;

import com.example.articlemanagementdemo.dto.ReviewRequest;
import com.example.articlemanagementdemo.dto.ReviewResponse;
import com.example.articlemanagementdemo.entity.Review;
import com.example.articlemanagementdemo.search.CustomRsqlVisitor;
import com.example.articlemanagementdemo.service.ReviewService;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse create(@RequestBody ReviewRequest request) {
        return reviewService.create(request);
    }

    @GetMapping()
    public List<Review> search(@RequestParam String search) {
        Node rootNode = new RSQLParser().parse(search);
        Specification<Review> spec = rootNode.accept(new CustomRsqlVisitor<>());
        return reviewService.search(spec);
    }

    @GetMapping("/{reviewId}")
    public ReviewResponse findById(@PathVariable("reviewId") Long reviewId) {
        return reviewService.findById(reviewId);
    }

    @GetMapping("/article/{articleId}")
    public List<ReviewResponse> findAllByArticleId(@PathVariable("articleId") Long articleId) {
        return reviewService.findAllByArticleId(articleId);
    }

    @PutMapping("/{reviewId}")
    public ReviewResponse update(@PathVariable("reviewId") Long reviewId,@RequestBody ReviewRequest request) {
        return reviewService.update(reviewId, request);
    }

    @DeleteMapping("/{reviewId}")
    public void delete(@PathVariable("reviewId") Long reviewId) {
        reviewService.delete(reviewId);
    }
}
