package com.chibuisi.dailyinsightservice.article.dto;


import com.chibuisi.dailyinsightservice.topic.model.TopicItemProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateArticleRequest {
    private String id;
    private String title;
    private String content;
    private String category;
    private String dateTag;
    private List<String> tags; //todo create object
    private List<String> keywords; //todo create object
    private String summary;
    private String metaDescription;
    private String seoTitle;
    private boolean isAdvancedTopic;
    private List<TopicItemProperties> topicItemProperties;
}
