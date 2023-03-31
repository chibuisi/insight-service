package com.chibuisi.dailyinsightservice.topic.service;

import com.chibuisi.dailyinsightservice.topic.dto.TopicItemResponseDTO;
import com.chibuisi.dailyinsightservice.topic.model.TopicItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface TopicItemService {
    public TopicItem save(TopicItem topicItem);
    public TopicItemResponseDTO saveTopicItemList(List<TopicItem> topicItems, String topic);
    public TopicItem update(TopicItem topicItem);
    public Optional<TopicItem> get(Long topicItemId);
    public Optional<TopicItem> get(String topicItemId);
    public void deleteTopicItem(String topic, String title);
    public Optional<List<TopicItem>> getTopicItems(String topicName);
    //this should be paginated
    public List<List<TopicItem>> getTopicItems(List<String> topicNames);
    public TopicItem getTopicItemByTopicAndTitle(String topicName, String topicItemName);
    public TopicItemResponseDTO uploadFile(MultipartFile file, String topic);
}
