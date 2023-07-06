package com.chibuisi.dailyinsightservice.topic.dto;

import com.chibuisi.dailyinsightservice.article.model.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicItemResponseDTO {
    private List<Article> savedTopicItems;
    private List<Article> duplicateTopicItems;
}
