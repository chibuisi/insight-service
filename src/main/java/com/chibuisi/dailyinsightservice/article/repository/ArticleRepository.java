package com.chibuisi.dailyinsightservice.article.repository;

import com.chibuisi.dailyinsightservice.article.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findArticleByTitleAndCategory(String title, String category);
    Page<Article> findArticlesByTitleLike(String title, Pageable pageable);
    Page<Article> findArticlesByCategoryLike(String category, Pageable pageable);
    Page<Article> findArticlesBy(Pageable pageable);
}
