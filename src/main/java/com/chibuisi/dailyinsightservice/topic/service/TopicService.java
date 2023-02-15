package com.chibuisi.dailyinsightservice.topic.service;

import com.chibuisi.dailyinsightservice.topic.model.Topic;

import java.util.List;

public interface TopicService {
    public Topic save(Topic topic);
    public List<Topic> save(List<Topic> topics);
    public Topic update(Topic topic);
    public Topic get(Integer topicId);
    public Topic get(String name);
    default void delete(Integer topic){}
    default void delete(String topicName){}
}
