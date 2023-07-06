package com.chibuisi.dailyinsightservice.topic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicListResponse {
    private String nextPageToken;
    private String pageSize;
    private List<TopicResponse> topicResponses;
}
