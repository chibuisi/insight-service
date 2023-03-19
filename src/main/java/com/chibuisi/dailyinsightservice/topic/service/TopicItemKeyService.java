package com.chibuisi.dailyinsightservice.topic.service;

import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import com.chibuisi.dailyinsightservice.topic.model.TopicItemKey;
import com.chibuisi.dailyinsightservice.topic.model.TopicItemKeyDTO;
import com.chibuisi.dailyinsightservice.topic.repository.TopicItemKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicItemKeyService {
    @Autowired
    private TopicItemKeyRepository topicItemKeyRepository;

    public List<TopicItemKey> getTopicItemKeys(String topic){
        SupportedTopics foundTopic = SupportedTopics.of(topic);
        return getTopicItemKeys(foundTopic.getValue());
    }
    public List<TopicItemKey> getTopicItemKeys(Integer topicId){
        return topicItemKeyRepository.findAllByTopicId(topicId).orElse(null);
    }

    public TopicItemKey saveTopicItemKey(TopicItemKey topicItemKey){
        return topicItemKeyRepository.save(topicItemKey);
    }

    public TopicItemKey saveTopicItemKey(TopicItemKeyDTO topicItemKeyDTO){
        SupportedTopics topic = SupportedTopics.of(topicItemKeyDTO.getTopic());
        TopicItemKey topicItemKey = TopicItemKey.builder()
                .topicId(topic.getValue()).keyName(topicItemKeyDTO.getKeyName())
                .description(topicItemKeyDTO.getDescription()).build();
        return saveTopicItemKey(topicItemKey);
    }
}
