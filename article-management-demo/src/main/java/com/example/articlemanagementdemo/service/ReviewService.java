package com.example.articlemanagementdemo.service;

import com.example.articlemanagementdemo.dto.ReviewRequest;
import com.example.articlemanagementdemo.dto.ReviewResponse;
import com.example.articlemanagementdemo.entity.Article;
import com.example.articlemanagementdemo.entity.Review;
import com.example.articlemanagementdemo.exception.DataNotFoundException;
import com.example.articlemanagementdemo.repository.ArticleRepository;
import com.example.articlemanagementdemo.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ArticleRepository articleRepository;

    public ReviewResponse create(ReviewRequest request) {
        Article article = articleRepository.findById(request.getArticleId())
                .orElseThrow(() -> new DataNotFoundException("Article not found with articleId: " + request.getArticleId()));
        Review review = reviewRepository.save(mapDtoToEntity(request, article));
        return mapEntityToDto(review);
    }

    public List<Review> search(Specification<Review> spec) {
        return reviewRepository.findAll(spec);
    }

    public ReviewResponse findById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new DataNotFoundException("Review not found with reviewId: " + reviewId));
        return mapEntityToDto(review);
    }

    public List<ReviewResponse> findAllByArticleId(Long articleId) {
        return reviewRepository.findByArticleId(articleId).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public ReviewResponse update(Long reviewId, ReviewRequest request) {
        Review existedReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new DataNotFoundException("Review not found with reviewId: " + reviewId));

        existedReview.setReviewContent(request.getReviewContent());
        existedReview.setReviewer(request.getReviewer());

        return mapEntityToDto(reviewRepository.save(existedReview));
    }

    public void delete(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    private ReviewResponse mapEntityToDto(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .reviewer(review.getReviewer())
                .reviewContent(review.getReviewContent())
                .articleId(review.getArticleId())
                .createdDate(review.getCreateDate())
                .build();
    }

    private Review mapDtoToEntity(ReviewRequest request, Article article) {
        Review review = new Review();
        review.setReviewer(request.getReviewer());
        review.setReviewContent(request.getReviewContent());
        review.setArticle(article);
        review.setArticleId(article.getId());
        return review;
    }
}
