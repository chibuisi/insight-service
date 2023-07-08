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
public class ListTopicResponse {
    private String nextPageToken;
    private String pageSize;
    private String totalPagesSize;
    private List<TopicResponse> topicResponses;
}
