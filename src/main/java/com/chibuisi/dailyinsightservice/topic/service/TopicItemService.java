package com.chibuisi.dailyinsightservice.topic.service;

import com.chibuisi.dailyinsightservice.topic.model.TopicItem;

import java.util.List;
import java.util.Optional;

public interface TopicItemService {
    public TopicItem save(TopicItem topicItem);
    public TopicItem update(TopicItem topicItem);
    public Optional<TopicItem> get(Long topicItemId);
    public Optional<TopicItem> get(String topicItemId);
    default void delete(Long topicId){

    }
    public Optional<List<TopicItem>> getTopicItems(String topicName);
    //this should be paginated
    public List<List<TopicItem>> getTopicItems(List<String> topicNames);
}
