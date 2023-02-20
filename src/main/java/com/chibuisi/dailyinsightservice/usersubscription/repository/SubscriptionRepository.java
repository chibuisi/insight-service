package com.chibuisi.dailyinsightservice.usersubscription.repository;

import com.chibuisi.dailyinsightservice.usersubscription.model.Subscription;
import com.chibuisi.dailyinsightservice.usersubscription.model.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    public Subscription findByEmailAndTopic(String email, String topic);
    public List<Subscription> findByEmail(String email);
    public List<Subscription> findByEmailAndStatusIn(String email, List<SubscriptionStatus> statuses);
    public List<Subscription> findAllByTopicAndStatusIn(String topic, List<SubscriptionStatus> statuses);
}
