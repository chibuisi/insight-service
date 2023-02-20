package com.chibuisi.dailyinsightservice.usersubscription.service.impl;

import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import com.chibuisi.dailyinsightservice.user.model.User;
import com.chibuisi.dailyinsightservice.user.service.UserService;
import com.chibuisi.dailyinsightservice.usersubscription.model.Subscription;
import com.chibuisi.dailyinsightservice.usersubscription.model.SubscriptionRequestDto;
import com.chibuisi.dailyinsightservice.usersubscription.model.SubscriptionStatus;
import com.chibuisi.dailyinsightservice.usersubscription.model.UnsubscribeReason;
import com.chibuisi.dailyinsightservice.usersubscription.repository.SubscriptionRepository;
import com.chibuisi.dailyinsightservice.usersubscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private UserService userService;

    @Override
    public Subscription subscribe(Subscription subscription) {
        Subscription existing = subscriptionRepository
                .findByEmailAndTopic(subscription.getEmail(), subscription.getTopic());
        if(existing == null){
            User user = userService.getUserByEmail(subscription.getEmail());
            if(user == null){
                user = User.builder().dateJoined(LocalDateTime.now())
                        .email(subscription.getEmail()).build();
                user = userService.saveUser(user);
            }
            LocalDateTime date = LocalDateTime.now();
            subscription.setDateSubscribed(date);
            subscription.setDateUpdated(date);
            subscription.setUserId(user.getId());
            subscription.setStatus(SubscriptionStatus.ACTIVE);
            Optional<SupportedTopics> supTopic = Arrays.stream(SupportedTopics.values())
                    .filter(e -> e.getName().equalsIgnoreCase(subscription.getTopic()))
                    .findFirst();
            if(supTopic.isPresent()){
                subscription.setTopic(supTopic.get().getName());
                subscription.setSupportedTopic(supTopic.get());
                return subscriptionRepository.save(subscription);
            }
        }
        else{
            return updateSubscription(SubscriptionRequestDto.builder().email(subscription.getEmail())
                            .status("active").topic(subscription.getTopic()).build());
        }
        return null;
    }

    @Override
    public void unsubscribe(String email, String topic, String reason) {
        Subscription existing = subscriptionRepository
                .findByEmailAndTopic(email, topic);
        if(existing != null){
            Optional<UnsubscribeReason> unsubReasonOpt = Arrays.stream(UnsubscribeReason.values())
                    .filter(e -> e.getName().equalsIgnoreCase(reason))
                    .findFirst();
            if(!unsubReasonOpt.isPresent()){
                existing.setUnsubscribeReason(UnsubscribeReason.UNKNOWN);
            }
            else existing.setUnsubscribeReason(unsubReasonOpt.get());
            existing.setStatus(SubscriptionStatus.INACTIVE);
            existing.setDateUnsubscribed(LocalDateTime.now());
            subscriptionRepository.save(existing);
        }
    }

    @Override
    public Subscription getSubscriptionInfo(String email, String topic) {
        return subscriptionRepository.findByEmailAndTopic(email, topic);
    }

    @Override
    public List<String> getTopicsForSubscriber(String email) {
        List<String> topics = new ArrayList<>();
        List<Subscription> subscriptions =
                subscriptionRepository.findByEmailAndStatusIn(email,
                        Arrays.asList(SubscriptionStatus.ACTIVE_PAID,
                                SubscriptionStatus.ACTIVE));
        subscriptions.forEach(e -> {
            topics.add(e.getTopic().toLowerCase());
        });
        return topics;
    }

    @Override
    public List<String> getSubscribersForTopic(String topic) {
        List<String> users = new ArrayList<>();
        List<Subscription> subscriptions =
                subscriptionRepository.findAllByTopicAndStatusIn(topic,
                        Arrays.asList(SubscriptionStatus.ACTIVE_PAID,
                                SubscriptionStatus.ACTIVE));
        subscriptions.forEach(e -> {
            users.add(e.getEmail());
        });
        return users;
    }

    @Override
    public Subscription updateSubscription(SubscriptionRequestDto subscriptionRequestDto) {
        Subscription existing = subscriptionRepository
                .findByEmailAndTopic(subscriptionRequestDto.getEmail(),
                        SupportedTopics.of(subscriptionRequestDto.getTopic()).getName());
        if(existing != null){
            SubscriptionStatus supStatus = SubscriptionStatus.of(subscriptionRequestDto.getStatus());
            if(!supStatus.equals(existing.getStatus())) {
                existing.setStatus(supStatus);
                LocalDateTime date = LocalDateTime.now();
                if ((existing.getStatus().getName().startsWith("ACTIVE"))){
                    existing.getRenewDates().add(date);
                    existing.setDateLastRenewed(date);
                    existing.setDateUnsubscribed(null);
                    existing.setUnsubscribeReason(null);
                }
                else{
                    existing.setUnsubscribeReason(UnsubscribeReason.of(subscriptionRequestDto.getReason()));
                }
                existing.setDateUpdated(date);
                return subscriptionRepository.save(existing);
            }
        }
        return null;
    }
}
