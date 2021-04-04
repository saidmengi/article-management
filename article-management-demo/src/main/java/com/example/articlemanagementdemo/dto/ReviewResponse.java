package com.example.articlemanagementdemo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewResponse {
    private Long id;
    private String reviewer;
    private String reviewContent;
    private Long articleId;
    private LocalDateTime createdDate;
}
