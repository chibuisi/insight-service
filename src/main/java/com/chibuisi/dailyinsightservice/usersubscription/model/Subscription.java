package com.chibuisi.dailyinsightservice.usersubscription.model;

import com.chibuisi.dailyinsightservice.topic.model.SupportedTopic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String email;
    private String topic;
    private SupportedTopic supportedTopic;
    private SubscriptionStatus status;
    private LocalDateTime dateSubscribed;
    private LocalDateTime dateUpdated;
    private UnsubscribeReason unsubscribeReason;
    private LocalDateTime dateUnsubscribed;
    private LocalDateTime dateLastRenewed;
    @ElementCollection
    private List<LocalDateTime> renewDates;
}
