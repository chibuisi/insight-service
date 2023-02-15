package com.chibuisi.dailyinsightservice.topic.service;

import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import com.chibuisi.dailyinsightservice.topic.model.Topic;
import com.chibuisi.dailyinsightservice.topic.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public Topic save(Topic topic) {
        topic.setDateAdded(LocalDateTime.now());
        Optional<SupportedTopics> supTopic = Arrays.stream(SupportedTopics.values())
                .filter(e -> e.getName().equalsIgnoreCase(topic.getName())).findFirst();
        if(supTopic.isPresent()){
            topic.setId(supTopic.get().getValue());
            topic.setName(supTopic.get().getName());
        }
        else{
            return null;
        }
        return topicRepository.save(topic);
    }

    @Override
    public List<Topic> save(List<Topic> topics) {
        List<Topic> topicSaved = new ArrayList<>();
        topics.forEach(topic -> {
            topicSaved.add(save(topic));
        });
        return topicSaved;
    }

    @Override
    public Topic update(Topic topic) {
        Optional<Topic> foundTopic = topicRepository.findById(topic.getId());
        if(foundTopic.isPresent()){
            Topic updatedTopic = foundTopic.get();
            updatedTopic.setDescription(topic.getDescription());
            topicRepository.save(updatedTopic);
            return updatedTopic;
        }
        return  null;
    }

    @Override
    public Topic get(Integer topicId) {
        return topicRepository.findById(topicId).orElse(null);
    }

    public Topic get(String name){
        return topicRepository.findTopicByName(name);
    }

    @Override
    public void delete(Integer topicId) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if(topic.isPresent())
            topicRepository.delete(topic.get());
    }

    @Override
    public void delete(String topicName) {
        topicRepository.delete(topicRepository.findTopicByName(topicName));
    }
}
