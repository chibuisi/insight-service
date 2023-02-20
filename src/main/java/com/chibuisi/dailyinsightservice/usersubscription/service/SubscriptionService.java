package com.chibuisi.dailyinsightservice.usersubscription.service;

import com.chibuisi.dailyinsightservice.topic.model.Topic;
import com.chibuisi.dailyinsightservice.user.model.User;
import com.chibuisi.dailyinsightservice.usersubscription.model.Subscription;
import com.chibuisi.dailyinsightservice.usersubscription.model.SubscriptionRequestDto;

import java.util.List;

public interface SubscriptionService {
    public Subscription subscribe(Subscription subscription);
    public void unsubscribe(String email, String topic, String reason);
    public Subscription getSubscriptionInfo(String email, String topic);
    public List<String> getTopicsForSubscriber(String email);
    public List<String> getSubscribersForTopic(String topic);
    public Subscription updateSubscription(SubscriptionRequestDto subscriptionRequestDto);
}
