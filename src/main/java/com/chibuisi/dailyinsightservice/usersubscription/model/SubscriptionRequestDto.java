package com.chibuisi.dailyinsightservice.usersubscription.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionRequestDto {
    private String topic;
    private String email;
    private String status;
    private String reason;
}
