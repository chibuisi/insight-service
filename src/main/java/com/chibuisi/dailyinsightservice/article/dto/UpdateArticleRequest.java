package com.chibuisi.dailyinsightservice.article.dto;


import com.chibuisi.dailyinsightservice.topic.model.TopicItemProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateArticleRequest {
    @NotBlank(message = "article id is required")
    @Digits(integer = 10, fraction = 0, message = "article id must have a valid value")
    private String id;
    @NotBlank(message = "topic id is required")
    @Digits(integer = 10, fraction = 0, message = "topic id must have a valid value")
    private String topicId;
    @Size(max = 255, message = "title must be maximum {max} characters")
    private String title;
    @NotBlank(message = "category is required")
    @Size(max = 255, message = "category must be maximum {max} characters")
    private String category;
    @NotBlank(message = "author User Id is required")
    @Digits(integer = 10, fraction = 0, message = "author User Id must have a valid value")
    private String authorUserId;
    private String content;
    private List<@NotNull(message = "tags item must not be null") String> tags; //todo create object
    private List<@NotNull(message = "keyword item must not be null") String> keywords; //todo create object
    private String summary;
    private String metaDescription;
    private String seoTitle;
    private boolean isAdvancedTopic;
    private String readTime;
    private List<TopicItemProperties> topicItemProperties;
    private Boolean activeStatus;
    private String featuredImageLink;
    private Boolean featuredStatus;
}
