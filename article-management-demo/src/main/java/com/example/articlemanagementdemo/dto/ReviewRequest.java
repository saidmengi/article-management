package com.example.articlemanagementdemo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewRequest {
    private String reviewer;
    private String reviewContent;
    private Long articleId;
}
