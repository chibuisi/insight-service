package com.chibuisi.dailyinsightservice.topic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListTopicRequest {
    @JsonProperty("token")
    private String pageToken;
    @JsonProperty("size")
    private String pageSize;
    @JsonProperty("order")
    private String orderBy;
    @JsonProperty("filter")
    private List<String> filter;
}
