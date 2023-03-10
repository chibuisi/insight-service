package com.chibuisi.dailyinsightservice.topic.service;

import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import com.chibuisi.dailyinsightservice.topic.model.TopicItem;
import com.chibuisi.dailyinsightservice.topic.repository.TopicItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TopicItemServiceImpl implements TopicItemService{
    @Value("${topic.items.tag}")
    private String tag;
    @Autowired
    private TopicItemRepository topicItemRepository;
    @Override
    public TopicItem save(TopicItem topicItem) {
        Optional<TopicItem> existing = topicItemRepository.findByTitle(topicItem.getTitle());
        if(existing.isPresent()){
            return null;
        }
        SupportedTopics topic = SupportedTopics.of(topicItem.getTopicName());
        topicItem.setDateAdded(LocalDateTime.now());
        topicItem.setTopicName(topic.getName());
        return topicItemRepository.save(topicItem);
    }

    @Override
    public TopicItem update(TopicItem topicItem) {
        return topicItemRepository.save(topicItem);
    }

    @Override
    public Optional<TopicItem> get(Long topicItemId) {
        return topicItemRepository.findById(topicItemId);
    }

    @Override
    public Optional<TopicItem> get(String title) {
        return topicItemRepository.findByTitle(title);
    }

    @Override
    public Optional<List<TopicItem>> getTopicItems(String topicName) {
        return topicItemRepository.findAllByTopicName(topicName);
    }

    @Override
    public List<List<TopicItem>> getTopicItems(List<String> topicNames) {
        List<List<TopicItem>> foundTopicItems = new ArrayList<>();
        topicNames.forEach(topicName -> {
            Optional<List<TopicItem>> optionalTopicItems = getTopicItems(topicName);
            if(optionalTopicItems.isPresent()){
                foundTopicItems.add(optionalTopicItems.get());
            }
        });
        return foundTopicItems;
    }

    public TopicItem findByLatestTag(){
        Optional<TopicItem> optionalTopicItem = topicItemRepository.findTopicItemByTag(tag);
        if(optionalTopicItem.isPresent()){
            return optionalTopicItem.get();
        }
        return null;
    }

    public TopicItem findByDateTag(){
        LocalDate localDate = LocalDate.now();
        Optional<TopicItem> optionalTopicItem = topicItemRepository.findTopicItemByTag(localDate.toString());
        if(optionalTopicItem.isPresent()){
            return optionalTopicItem.get();
        }
        return null;
    }
}
