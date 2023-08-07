package com.chibuisi.dailyinsightservice.topic.service;

import com.chibuisi.dailyinsightservice.topic.dto.TopicItemResponseDTO;
import com.chibuisi.dailyinsightservice.article.model.Article;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface TopicItemService {
//    public Article save(Article article);
    public TopicItemResponseDTO saveTopicItemList(List<Article> articles, String topic);
//    public Article update(Article article);
    public Optional<Article> get(Long topicItemId);
    public Optional<Article> get(String topicItemId);
    public void deleteTopicItem(String topic, String title);
    public Optional<List<Article>> getTopicItems(String topicName);
    //this should be paginated
    public List<List<Article>> getTopicItems(List<String> topicNames);
    public Article getTopicItemByTopicAndTitle(String topicName, String topicItemName);
    public TopicItemResponseDTO uploadFile(MultipartFile file, String topic);
}
