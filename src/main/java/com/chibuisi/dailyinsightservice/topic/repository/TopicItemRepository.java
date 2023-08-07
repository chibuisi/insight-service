package com.chibuisi.dailyinsightservice.topic.repository;

import com.chibuisi.dailyinsightservice.article.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TopicItemRepository extends JpaRepository<Article, Long> {
    public Optional<Article> findByTitle(String title);
//    public Optional<Article> findByOffset(Long offset);
//    @Query("SELECT max(t.offset) FROM Article t where t.topicName = :topicName")
//    Long findLargestOffset(@Param("topicName") String topicName);

}
