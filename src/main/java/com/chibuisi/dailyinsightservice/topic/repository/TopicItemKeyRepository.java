package com.chibuisi.dailyinsightservice.topic.repository;

import com.chibuisi.dailyinsightservice.topic.model.TopicItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicItemKeyRepository extends JpaRepository<TopicItemKey, Long> {
    public Optional<TopicItemKey> getTopicItemKeyById(Integer id);
    public Optional<List<TopicItemKey>> findAllByTopicId(Integer topicId);
    public Optional<TopicItemKey> findTopicItemKeyByKeyNameAndTopicId(String keyName, Integer topicId);
}
