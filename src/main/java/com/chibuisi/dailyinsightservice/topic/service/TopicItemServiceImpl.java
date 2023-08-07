package com.chibuisi.dailyinsightservice.topic.service;

import com.chibuisi.dailyinsightservice.topic.dto.TopicItemResponseDTO;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopic;
import com.chibuisi.dailyinsightservice.article.model.Article;
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

@Service
public class TopicItemServiceImpl implements TopicItemService{
    @Value("${topic.items.tag}")
    private String tag;
    @Autowired
    private TopicItemRepository topicItemRepository;

    //please save multiple records for a specific topic at a time
    //only one person should use this endpoint service at a time to avoid duplicate offsets in the db
    public TopicItemResponseDTO saveTopicItemList(List<Article> articles, String topic){
        if(articles == null || articles.size() == 0)
            return TopicItemResponseDTO.builder().savedTopicItems(new ArrayList<>()).duplicateTopicItems(new ArrayList<>())
                    .build();
        TopicItemResponseDTO responseDTO = TopicItemResponseDTO.builder().build();
        SupportedTopic validTopic = SupportedTopic.of(topic);
        List<Article> duplicates = new ArrayList<>();
        List<Article> saved = new ArrayList<>();
        articles.forEach(e -> {
            Optional<Article> existing = Optional.empty();
            if(existing.isPresent())
                duplicates.add(e);
            else{
                if(e.getPublicationDate() == null)
                    e.setPublicationDate(LocalDateTime.now());
//                e.setTopicName(validTopic.getName());
                Article article = topicItemRepository.save(e);
                saved.add(article);
            }
        });
        responseDTO.setSavedTopicItems(saved);
        responseDTO.setDuplicateTopicItems(duplicates);
        return responseDTO;
    }

    public TopicItemResponseDTO uploadFile(MultipartFile file, String topic){
        SupportedTopic validTopic = SupportedTopic.of(topic);
        List<Article> articles = ExcelHelper.rowsToObject(file, validTopic.getName());
        return saveTopicItemList(articles, topic);
    }

    public void deleteTopicItem(String topic, String title){
        Optional<Article> topicItem = Optional.empty();
        if(topicItem.isPresent())
            topicItemRepository.delete(topicItem.get());
    }

    @Override
    public Optional<Article> get(Long topicItemId) {
        return topicItemRepository.findById(topicItemId);
    }

    @Override
    public Optional<Article> get(String title) {
        return topicItemRepository.findByTitle(title);
    }

//    public Optional<Article> findTopicItemByOffset(Long userPickOffset){
//        return topicItemRepository.findByOffset(userPickOffset);
//    }

    public Article getTopicItemByTopicAndTitle(String topicName, String topicItemName) {
        SupportedTopic topic = SupportedTopic.of(topicName);
        Optional<Article> optionalTopicItem = Optional.empty();
        if(optionalTopicItem.isPresent())
            return optionalTopicItem.get();
        return null;
    }

    @Override
    public Optional<List<Article>> getTopicItems(String topicName) {
        return Optional.empty();
    }

    @Override
    public List<List<Article>> getTopicItems(List<String> topicNames) {
        List<List<Article>> foundTopicItems = new ArrayList<>();
        topicNames.forEach(topicName -> {
            Optional<List<Article>> optionalTopicItems = getTopicItems(topicName);
            if(optionalTopicItems.isPresent()){
                foundTopicItems.add(optionalTopicItems.get());
            }
        });
        return foundTopicItems;
    }

}
