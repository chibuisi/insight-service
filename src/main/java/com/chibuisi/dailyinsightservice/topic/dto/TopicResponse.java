package com.chibuisi.dailyinsightservice.topic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicResponse {
    private String name;
    private String description;
    private String detail;
    private String imageUrl;
    private String keywords;
    private String imageLinks;
    private String createdBy;
    private String createdDate;
    private String category;
}
