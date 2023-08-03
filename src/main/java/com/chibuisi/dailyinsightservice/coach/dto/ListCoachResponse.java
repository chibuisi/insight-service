package com.chibuisi.dailyinsightservice.coach.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListCoachResponse {
    private String nextPageToken;
    private String pageSize;
    private String totalPagesSize;
    private List<?> topicResponses;
}
