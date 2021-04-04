package com.example.articlemanagementdemo.dto;

import com.example.articlemanagementdemo.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {
    private Long id;
    private String title;
    private String author;
    private String articleContent;
    private LocalDateTime publishDate;
    private int starCount;
    List<Review> reviews;
}
