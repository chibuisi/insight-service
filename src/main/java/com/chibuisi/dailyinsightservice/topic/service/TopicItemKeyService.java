package com.chibuisi.dailyinsightservice.topic.service;

import com.chibuisi.dailyinsightservice.topic.model.SupportedTopic;
import com.chibuisi.dailyinsightservice.topic.model.TopicItemKey;
import com.chibuisi.dailyinsightservice.topic.model.TopicItemKeyDTO;
import com.chibuisi.dailyinsightservice.topic.repository.TopicItemKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicItemKeyService {
    @Autowired
    private TopicItemKeyRepository topicItemKeyRepository;

    public List<TopicItemKey> getTopicItemKeys(String topic){
        SupportedTopic foundTopic = SupportedTopic.of(topic);
        return getTopicItemKeys(foundTopic.getValue());
    }
    public List<TopicItemKey> getTopicItemKeys(Integer topicId){
        return topicItemKeyRepository.findAllByTopicId(topicId).orElse(null);
    }

    public TopicItemKey saveTopicItemKey(TopicItemKey topicItemKey){
        Optional<TopicItemKey> existing = topicItemKeyRepository
                .findTopicItemKeyByKeyNameAndTopicId(topicItemKey.getKeyName(), topicItemKey.getTopicId());
        if (existing.isPresent())
            return existing.get();
        return topicItemKeyRepository.save(topicItemKey);
    }

    public List<TopicItemKey> saveTopicItemKeys(List<TopicItemKeyDTO> topicItemKeyDTOS){
        List<TopicItemKey> topicItemKeys = new ArrayList<>();
        topicItemKeyDTOS.forEach(e -> {
            topicItemKeys.add(saveTopicItemKey(e));
        });
        return topicItemKeys;
    }

    public TopicItemKey saveTopicItemKey(TopicItemKeyDTO topicItemKeyDTO){
        SupportedTopic topic = SupportedTopic.of(topicItemKeyDTO.getTopic());
        TopicItemKey topicItemKey = TopicItemKey.builder()
                .topicId(topic.getValue()).keyName(topicItemKeyDTO.getKeyName())
                .description(topicItemKeyDTO.getDescription()).build();
        return saveTopicItemKey(topicItemKey);
    }
}
