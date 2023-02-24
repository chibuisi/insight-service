package com.chibuisi.dailyinsightservice.usersubscription.service.impl;

import com.chibuisi.dailyinsightservice.schedules.model.ScheduleDTO;
import com.chibuisi.dailyinsightservice.schedules.service.ScheduleService;
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
    @Autowired
    private ScheduleService scheduleService;

    @Override
    public Subscription subscribe(Subscription subscription) {
        User user = userService.getUserByEmail(subscription.getEmail());
        SupportedTopics supportedTopics = SupportedTopics.of(subscription.getTopic());
        if(user == null){
            user = User.builder().dateJoined(LocalDateTime.now())
                    .email(subscription.getEmail()).build();
            user = userService.saveUser(user);
        }
        ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                .userId(user.getId()).topic(supportedTopics.getName()).build();
        Subscription existing = subscriptionRepository
                .findByEmailAndTopic(subscription.getEmail(), subscription.getTopic());
        if(existing == null){
            LocalDateTime date = LocalDateTime.now();
            subscription.setDateSubscribed(date);
            subscription.setDateUpdated(date);
            subscription.setUserId(user.getId());
            subscription.setStatus(SubscriptionStatus.ACTIVE);
            subscription.setTopic(supportedTopics.getName());
            subscription.setSupportedTopic(supportedTopics);
            scheduleService.saveSchedule(scheduleDTO);
            return subscriptionRepository.save(subscription);
        }
        else{
            scheduleService.saveSchedule(scheduleDTO);
            return updateSubscription(SubscriptionRequestDto.builder().email(subscription.getEmail())
                            .status("active").topic(subscription.getTopic()).build());
        }
    }

    @Override
    public void unsubscribe(String email, String topic, String reason) {
        Subscription existing = subscriptionRepository
                .findByEmailAndTopic(email, topic);
        if(existing != null){
            UnsubscribeReason unsubscribeReason = UnsubscribeReason.of(reason);
            existing.setUnsubscribeReason(unsubscribeReason);
            existing.setStatus(SubscriptionStatus.INACTIVE);
            existing.setDateUnsubscribed(LocalDateTime.now());
            subscriptionRepository.save(existing);
            scheduleService.deleteAllScheduleForUser(existing.getUserId(), topic);
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
