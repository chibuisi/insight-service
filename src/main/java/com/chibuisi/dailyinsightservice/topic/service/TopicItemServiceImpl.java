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
    @Override
    public Article save(Article article) {
        Optional<Article> existing = topicItemRepository.findByTitle(article.getTitle());
        if(existing.isPresent()){
            return null;
        }
        SupportedTopic topic = SupportedTopic.of(article.getTopicName());
        article.setPublicationDate(LocalDateTime.now());
        article.setTopicName(topic.getName());
        return topicItemRepository.save(article);
    }

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
            Optional<Article> existing = topicItemRepository.findTopicItemByTopicNameAndTitle(validTopic.getName(), e.getTitle());
            if(existing.isPresent())
                duplicates.add(e);
            else{
                if(e.getPublicationDate() == null)
                    e.setPublicationDate(LocalDateTime.now());
                e.setTopicName(validTopic.getName());
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

    @Override
    public Article update(Article article) {
        SupportedTopic topic = SupportedTopic.of(article.getTopicName());
        Article existing = getTopicItemByTopicAndTitle(topic.getName(), article.getTitle());
        if(existing != null){
            existing.setTopicItemProperties(article.getTopicItemProperties());
            existing.setTag(article.getTag());
            existing.setDateTag(article.getDateTag());
            existing = save(existing);
        }
        return existing;
    }

    public void deleteTopicItem(String topic, String title){
        Optional<Article> topicItem = topicItemRepository.findTopicItemByTopicNameAndTitle(topic, title);
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
        Optional<Article> optionalTopicItem = topicItemRepository.findTopicItemByTopicNameAndTitle(topic.getName(), topicItemName);
        if(optionalTopicItem.isPresent())
            return optionalTopicItem.get();
        return null;
    }

    @Override
    public Optional<List<Article>> getTopicItems(String topicName) {
        return topicItemRepository.findAllByTopicName(topicName);
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

    public Article findByLatestTag(){
        Optional<Article> optionalTopicItem = topicItemRepository.findTopicItemByTag(tag);
        if(optionalTopicItem.isPresent()){
            return optionalTopicItem.get();
        }
        return null;
    }

    public Article findByDateTag(){
        LocalDate localDate = LocalDate.now();
        Optional<Article> optionalTopicItem = topicItemRepository.findTopicItemByTag(localDate.toString());
        if(optionalTopicItem.isPresent()){
            return optionalTopicItem.get();
        }
        return null;
    }
}
