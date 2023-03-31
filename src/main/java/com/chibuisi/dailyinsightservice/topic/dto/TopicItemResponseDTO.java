package com.chibuisi.dailyinsightservice.topic.dto;

import com.chibuisi.dailyinsightservice.topic.model.TopicItem;
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
    private List<TopicItem> savedTopicItems;
    private List<TopicItem> duplicateTopicItems;
}
