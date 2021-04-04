package com.example.articlemanagementdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
@Entity
@Table(name = "review")
@Data
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String reviewer;
    private String reviewContent;
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Article.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private Article article;

    @Column(name = "article_id")
    private Long articleId;
}
