package com.chibuisi.dailyinsightservice.article.dto;

import com.chibuisi.dailyinsightservice.coach.dto.CoachResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ArticleResponseDto {
    private String topicName;
    private String category;
    private String title;
    private String authorUserId;
    private String authorName;
    private String content; //todo create object
    private LocalDateTime publicationDate;
    private List<String> tags; //todo create object
    private List<String> keywords; //todo create object
    private Long ownerUserId;
    private String featuredImage;
    private String summary;
    private String metaDescription;
    private String seoTitle;
    private String readTime;
    private String readTimes;
    private String wordCount;
    private boolean isAdvancedTopic;
}
