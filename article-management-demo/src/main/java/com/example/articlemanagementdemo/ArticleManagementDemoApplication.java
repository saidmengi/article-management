package com.example.articlemanagementdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ArticleManagementDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleManagementDemoApplication.class, args);
    }

}
