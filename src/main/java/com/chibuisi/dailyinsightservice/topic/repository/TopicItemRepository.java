package com.chibuisi.dailyinsightservice.topic.repository;

import com.chibuisi.dailyinsightservice.topic.model.TopicItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicItemRepository extends JpaRepository<TopicItem, Long> {
    public Optional<List<TopicItem>> findAllByTopicName(String name);
    public Optional<TopicItem> findByTitle(String title);
    public Optional<TopicItem> findTopicItemByTag(String tag);
}
