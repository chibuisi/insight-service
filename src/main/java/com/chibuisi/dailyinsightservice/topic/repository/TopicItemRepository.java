package com.chibuisi.dailyinsightservice.topic.repository;

import com.chibuisi.dailyinsightservice.topic.model.PickOffset;
import com.chibuisi.dailyinsightservice.topic.model.TopicItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TopicItemRepository extends JpaRepository<TopicItem, Long> {
    public Optional<TopicItem> findTopicItemByTopicNameAndTitle(String topic, String title);
    public Optional<List<TopicItem>> findAllByTopicName(String name);
    public Optional<TopicItem> findByTitle(String title);
    public Optional<TopicItem> findTopicItemByTag(String tag);
    public Optional<TopicItem> findByOffset(Long offset);
    @Query("SELECT max(t.offset) FROM TopicItem t where t.topicName = :topicName")
    Long findLargestOffset(@Param("topicName") String topicName);

}
