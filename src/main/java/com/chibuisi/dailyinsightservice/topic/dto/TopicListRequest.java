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
public class TopicListRequest {
    private String pageToken;
    private String pageSize;
    private String orderBy;
    private List<String> filter;
}
