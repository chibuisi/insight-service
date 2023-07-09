package com.chibuisi.dailyinsightservice.article.model;

import com.chibuisi.dailyinsightservice.coach.dto.CoachDto;
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
    private String authorName;
    private String content; //todo create object
    private LocalDateTime publicationDate;
    private String tags; //todo create object
    private String keywords; //todo create object
    private Long ownerUserId;
    private String featuredImage;
    private String summary;
    private String metaDescription;
    private String seoTitle;
    private String authorBio;
    private String relatedLinks; //todo create object
    private String comments; //todo create object
    private String readTime;
    private Long numberTimesRead;
    private Long wordCount;
    private List<CoachDto> coaches;
}
