package com.example.articlemanagementdemo.repository;

import com.example.articlemanagementdemo.entity.Article;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.List;

@ExtendWith(DBUnitExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class ArticleRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    ArticleRepository articleRepository;

    public ConnectionHolder getConnectionHolder() {
        return () -> dataSource.getConnection();
    }

    @Test
    @DataSet("articles.yml")
    void findAllTest() {
        List<Article> articles = articleRepository.findAll();
        Assertions.assertEquals(2, articles.size(), "Expected 2 articles in the database");
    }
}
