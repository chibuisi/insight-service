package com.chibuisi.dailyinsightservice.featuredItems.service;

import com.chibuisi.dailyinsightservice.coach.dto.CoachDto;
import com.chibuisi.dailyinsightservice.coach.service.CoachService;
import com.chibuisi.dailyinsightservice.featuredItems.model.FeaturedItemsResponse;
import com.chibuisi.dailyinsightservice.topic.dto.TopicResponse;
import com.chibuisi.dailyinsightservice.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeaturedItemsService {

    @Autowired
    private TopicService topicService;
    @Autowired
    private CoachService coachService;

    @Value("${app.defaults.numOfFeaturedTopics:20}")
    private int numOfFeaturedTopics;


    public FeaturedItemsResponse getFeaturedItems() {
        List<TopicResponse> featuredTopics = topicService.getFeaturedTopics(0, numOfFeaturedTopics);

        List<CoachDto> featuredCoaches = coachService.getActiveFeaturedCoaches(0,3);

        return FeaturedItemsResponse.builder()
                .topics(featuredTopics)
                .coaches(featuredCoaches)
                .articles(null)
                .build();
    }
}
