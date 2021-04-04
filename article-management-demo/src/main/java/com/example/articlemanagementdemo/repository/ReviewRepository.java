package com.example.articlemanagementdemo.repository;

import com.example.articlemanagementdemo.entity.Review;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    List<Review> findByArticleId(Long articleId);
}
