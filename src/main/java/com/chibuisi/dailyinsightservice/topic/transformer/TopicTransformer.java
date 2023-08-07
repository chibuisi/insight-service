package com.chibuisi.dailyinsightservice.topic.transformer;

import com.chibuisi.dailyinsightservice.topic.dto.TopicRequest;
import com.chibuisi.dailyinsightservice.topic.dto.TopicResponse;
import com.chibuisi.dailyinsightservice.topic.model.Topic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TopicTransformer {
    public static Topic fromTopicRequest(TopicRequest topicRequest) {
        String imageLinks = topicRequest.getImageLinks() == null ? null : String.join(",", topicRequest.getImageLinks());
        String keywords = topicRequest.getKeywords() == null ? null : String.join(",", topicRequest.getKeywords());
        return Topic.builder()
                .name(topicRequest.getName().toLowerCase())
                .description(topicRequest.getDescription())
                .detail(topicRequest.getDetail())
                .imageUrl(topicRequest.getImageUrl())
                .imageLinks(imageLinks)
                .createdBy(topicRequest.getCreatedBy())
                .keywords(keywords)
                .category(topicRequest.getCategory())
                .build();
    }

    public static List<Topic> fromRequestDtoList(List<TopicRequest> topicRequests) {
        List<Topic> topics = new ArrayList<>();
        topicRequests.forEach(topicRequest -> topics.add(fromTopicRequest(topicRequest)));
        return topics;
    }

    public static TopicResponse fromTopic(Topic topic) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return TopicResponse.builder()
                .id(String.valueOf(topic.getId()))
                .name(topic.getName())
                .description(topic.getDescription())
                .detail(topic.getDetail())
                .createdBy(topic.getCreatedBy())
                .imageUrl(topic.getImageUrl())
                .imageLinks(topic.getImageLinks())
                .keywords(topic.getKeywords())
                .createdDate(topic.getCreatedDate().format(formatter))
                .createdBy(topic.getCreatedBy())
                .category(topic.getCategory())
                .build();
    }

    public static List<TopicResponse> fromTopicList(List<Topic> topics) {
        List<TopicResponse> responseDtos = new ArrayList<>();
        topics.forEach(topic -> responseDtos.add(
                fromTopic(topic)
        ));
        return responseDtos;
    }

    public static Topic updateTopic(Topic topic, Topic topicRequest) {
        String imageLinks = topicRequest.getImageLinks() == null ? null : String.join(",", topicRequest.getImageLinks());
        if (topicRequest.getDescription() != null) {
            topic.setDescription(topicRequest.getDescription());
        }
        if (topicRequest.getDetail() != null) {
            topic.setDetail(topicRequest.getDetail());
        }
        if (topicRequest.getImageLinks() != null) {
            topic.setImageLinks(topicRequest.getImageLinks());
        }
        if (topicRequest.getImageUrl() != null) {
            topic.setImageUrl(imageLinks);
        }
        if (topicRequest.getKeywords() != null) {
            topic.setKeywords(topicRequest.getKeywords());
        }
        if (topicRequest.getCategory() != null) {
            topic.setCategory(topicRequest.getCategory());
        }
        topic.setModifiedDate(LocalDateTime.now());

        return topic;
    }
}
