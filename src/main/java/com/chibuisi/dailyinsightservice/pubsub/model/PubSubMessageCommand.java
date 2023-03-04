package com.chibuisi.dailyinsightservice.pubsub.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PubSubMessageCommand {
    private String projectId;
    private String topic;
    private String message;
}
