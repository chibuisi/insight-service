package com.chibuisi.dailyinsightservice.topic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TopicItemKeyDTO {
    private String topic;
    private String keyName;
    private String description;
}
