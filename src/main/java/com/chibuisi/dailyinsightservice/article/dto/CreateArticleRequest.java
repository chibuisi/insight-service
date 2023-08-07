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
public class CreateArticleRequest {
    @NotNull(message = "topic id is required")
    @Digits(integer = 10, fraction = 0, message = "topic id must have a valid value")
    private String topicId;
    @NotBlank(message = "title is required")
    @Size(max = 255, message = "title must be maximum {max} characters")
    private String title;
    @NotNull(message = "author User Id is required")
    @Digits(integer = 10, fraction = 0, message = "author User Id must have a valid value")
    private Long authorUserId;
    @NotBlank(message = "title is required")
    @Size(max = 100000, message = "title must be maximum {max} characters")
    private String content;
    @NotBlank(message = "category is required")
    @Size(max = 255, message = "category must be maximum {max} characters")
    private String category;
    @NotNull(message = "tags must not be null")
    @Size(max = 10, message = "tag(s) must not exceed {max}")
    private List<@Size(max = 50, message = "tag(s) must be between {min} and {max} characters") String> tags; //todo create object
    @NotNull(message = "keywords must not be null")
    @Size(max = 10, message = "keyword(s) must not exceed {max}")
    private List<@Size(max = 50, message = "keyword(s) must be between {min} and {max} characters") String> keywords; //todo create object
    @NotBlank(message = "summary is required")
    @Size(max = 10000, message = "summary must be maximum {max} characters")
    private String summary;
    @NotBlank(message = "meta description is required")
    @Size(max = 10000, message = "meta description must be maximum {max} characters")
    private String metaDescription;
    @NotBlank(message = "seo title is required")
    @Size(max = 10000, message = "seo title must be maximum {max} characters")
    private String seoTitle;
    @NotNull(message = "is advanced topic must not be null")
    private boolean isAdvancedArticle;
    private List<TopicItemProperties> topicItemProperties;
}
