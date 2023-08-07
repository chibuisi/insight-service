package com.chibuisi.dailyinsightservice.article.controller;

import com.chibuisi.dailyinsightservice.article.dto.CreateArticleRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @PostMapping
    @PreAuthorize("hasRole('ROLE_CREATE_ARTICLE')")
    public ResponseEntity<?> createArticle(@Valid @RequestBody CreateArticleRequest createArticleRequest) {
        return new ResponseEntity<>(createArticleRequest, HttpStatus.CREATED);
    }
}
