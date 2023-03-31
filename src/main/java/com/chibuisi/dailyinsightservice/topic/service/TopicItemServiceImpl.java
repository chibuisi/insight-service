package com.chibuisi.dailyinsightservice.topic.service;

import com.chibuisi.dailyinsightservice.topic.dto.TopicItemResponseDTO;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import com.chibuisi.dailyinsightservice.topic.model.TopicItem;
import com.chibuisi.dailyinsightservice.topic.repository.TopicItemRepository;
import com.chibuisi.dailyinsightservice.util.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

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

    //please save multiple records for a specific topic at a time
    //only one person should use this endpoint service at a time to avoid duplicate offsets in the db
    public TopicItemResponseDTO saveTopicItemList(List<TopicItem> topicItems, String topic){
        if(topicItems == null || topicItems.size() == 0)
            return TopicItemResponseDTO.builder().savedTopicItems(new ArrayList<>()).duplicateTopicItems(new ArrayList<>())
                    .build();
        TopicItemResponseDTO responseDTO = TopicItemResponseDTO.builder().build();
        SupportedTopics validTopic = SupportedTopics.of(topic);
        Long maximum = topicItemRepository.findLargestOffset(validTopic.getName());
        AtomicLong max;
        if(maximum != null)
            max = new AtomicLong(maximum+1);
        else
            max = new AtomicLong(1L);
        List<TopicItem> duplicates = new ArrayList<>();
        List<TopicItem> saved = new ArrayList<>();
        topicItems.forEach(e -> {
            Optional<TopicItem> existing = topicItemRepository.findTopicItemByTopicNameAndTitle(validTopic.getName(), e.getTitle());
            if(existing.isPresent())
                duplicates.add(e);
            else{
                Long l = max.get();
                e.setOffset(l);
                if(e.getDateAdded() == null)
                    e.setDateAdded(LocalDateTime.now());
                e.setTopicName(validTopic.getName());
                TopicItem topicItem = topicItemRepository.save(e);
                saved.add(topicItem);
                max.incrementAndGet();
            }
        });
        responseDTO.setSavedTopicItems(saved);
        responseDTO.setDuplicateTopicItems(duplicates);
        return responseDTO;
    }

    public TopicItemResponseDTO uploadFile(MultipartFile file, String topic){
        SupportedTopics validTopic = SupportedTopics.of(topic);
        List<TopicItem> topicItems = ExcelHelper.rowsToObject(file, validTopic.getName());
        return saveTopicItemList(topicItems, topic);
    }

    @Override
    public TopicItem update(TopicItem topicItem) {
        SupportedTopics topic = SupportedTopics.of(topicItem.getTitle());
        TopicItem existing = getTopicItemByTopicAndTitle(topic.getName(), topicItem.getTitle());
        if(existing != null){
            existing.setTopicItemProperties(topicItem.getTopicItemProperties());
            existing.setTag(topicItem.getTag());
            existing.setDateTag(topicItem.getDateTag());
            existing = save(existing);
        }
        return existing;
    }

    public void deleteTopicItem(String topic, String title){
        Optional<TopicItem> topicItem = topicItemRepository.findTopicItemByTopicNameAndTitle(topic, title);
        if(topicItem.isPresent())
            topicItemRepository.delete(topicItem.get());
    }

    @Override
    public Optional<TopicItem> get(Long topicItemId) {
        return topicItemRepository.findById(topicItemId);
    }

    @Override
    public Optional<TopicItem> get(String title) {
        return topicItemRepository.findByTitle(title);
    }

    public Optional<TopicItem> findTopicItemByOffset(Long userPickOffset){
        return topicItemRepository.findByOffset(userPickOffset);
    }

    public TopicItem getTopicItemByTopicAndTitle(String topicName, String topicItemName) {
        SupportedTopics topic = SupportedTopics.of(topicName);
        Optional<TopicItem> optionalTopicItem = topicItemRepository.findTopicItemByTopicNameAndTitle(topic.getName(), topicItemName);
        if(optionalTopicItem.isPresent())
            return optionalTopicItem.get();
        return null;
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
