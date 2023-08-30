package com.chibuisi.dailyinsightservice.article.controller;

import com.chibuisi.dailyinsightservice.article.dto.CreateArticleRequest;
import com.chibuisi.dailyinsightservice.article.dto.UpdateArticleRequest;
import com.chibuisi.dailyinsightservice.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CREATE_ARTICLE')")
    public ResponseEntity<?> createArticle(@Valid @RequestBody CreateArticleRequest createArticleRequest) {
        return new ResponseEntity<>(articleService.createArticle(createArticleRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticle(@PathVariable Long id) {
        return new ResponseEntity<>(articleService.getArticle(id), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listArticle(
            @NotNull @RequestParam(defaultValue = "0") Integer pageNumber,
            @NotNull @RequestParam(defaultValue = "1") Integer pageSize,
            @NotNull @RequestParam String filter,
            @NotNull @RequestParam(defaultValue = "title:ASC") String orderBy
    ) {
        return new ResponseEntity<>(
                articleService.listArticles(pageNumber, pageSize, filter, orderBy), HttpStatus.OK);
    }


    @PutMapping("")
    public ResponseEntity<?> updateArticle(@Valid @RequestBody UpdateArticleRequest updateArticleRequest) {
        return new ResponseEntity<>(articleService.updateArticle(updateArticleRequest), HttpStatus.OK);
    }

    @PutMapping("/activeStatus")
    @PreAuthorize("hasRole('ROLE_MANAGE_ARTICLE')")
    public ResponseEntity<?> updateActiveStatus(@Valid @RequestBody UpdateArticleRequest updateArticleRequest) {
        String id = updateArticleRequest.getId();
        Boolean status = updateArticleRequest.getActiveStatus();
        articleService.updateArticleActiveStatus(id, status);
        return new ResponseEntity<>("Updated", HttpStatus.ACCEPTED);
    }

    @PutMapping("/featuredStatus")
    @PreAuthorize("hasRole('ROLE_FEATURE_ARTICLE')")
    public ResponseEntity<?> featureArticle(@Valid @RequestBody UpdateArticleRequest updateArticleRequest) {
        String id = updateArticleRequest.getId();
        Boolean featuredStatus = updateArticleRequest.getFeaturedStatus();
        articleService.updateArticleFeaturedStatus(id, featuredStatus);
        return new ResponseEntity<>("Updated", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_DELETE_ARTICLE')")
    public ResponseEntity<?> deleteArticle(@PathVariable String id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }


}
