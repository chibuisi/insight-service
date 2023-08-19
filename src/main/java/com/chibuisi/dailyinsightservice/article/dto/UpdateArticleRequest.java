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
//    @NotNull(message = "title field is required")
    private String title;
    @NotBlank(message = "category is required")
    @Size(max = 255, message = "category must be maximum {max} characters")
    private String category;
    @NotBlank(message = "author User Id is required")
    @Digits(integer = 10, fraction = 0, message = "author User Id must have a valid value")
    private String authorUserId;
//    @NotNull(message = "content field must not be null")
    private String content;
    @NotNull(message = "tags field must not be null")
    private List<@NotNull(message = "tags item must not be null") String> tags; //todo create object
//    @NotNull(message = "keywords field must not be null")
    private List<@NotNull(message = "keyword item must not be null") String> keywords; //todo create object
//    @NotNull(message = "summary field must not be null")
    private String summary;
//    @NotNull(message = "meta description field must not be null")
    private String metaDescription;
//    @NotNull(message = "seo title field must not be null")
    private String seoTitle;
//    @NotNull(message = "is advanced topic field must not be null")
    private boolean isAdvancedTopic;
    private List<TopicItemProperties> topicItemProperties;
    private Boolean active;
    private String featuredImageLink;
    private Boolean featured;
}
