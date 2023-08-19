package com.chibuisi.dailyinsightservice.article.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ArticleResponseDto {
    private String id;
    private String topicId;
    private String category;
    private String title;
    private String authorUserId;
    private String authorName;
    private String content; //todo create object
    private String publicationDate;
    private String tags; //todo create object
    private String keywords; //todo create object
    private String featuredImageLink;
    private String summary;
    private String metaDescription;
    private String seoTitle;
    private String readTime;
    private String readTimes;
    private String wordCount;
    private boolean isAdvancedTopic;
}
