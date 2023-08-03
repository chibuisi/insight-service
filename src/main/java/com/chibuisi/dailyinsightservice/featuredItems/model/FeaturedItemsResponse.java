package com.chibuisi.dailyinsightservice.featuredItems.model;

import com.chibuisi.dailyinsightservice.article.model.ArticleResponseDto;
import com.chibuisi.dailyinsightservice.coach.dto.CoachDto;
import com.chibuisi.dailyinsightservice.coach.dto.CoachResponseDto;
import com.chibuisi.dailyinsightservice.topic.dto.TopicResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeaturedItemsResponse {
    private List<TopicResponse> topics;
    private List<ArticleResponseDto> articles;
    private List<CoachResponseDto> coaches;
}
