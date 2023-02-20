package com.chibuisi.dailyinsightservice.usersubscription.controller;

import com.chibuisi.dailyinsightservice.usersubscription.model.Subscription;
import com.chibuisi.dailyinsightservice.usersubscription.model.SubscriptionRequestDto;
import com.chibuisi.dailyinsightservice.usersubscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public ResponseEntity subscribe(@RequestBody Subscription subscription){
        return new ResponseEntity(subscriptionService.subscribe(subscription), 
                HttpStatus.CREATED);
    }

    @GetMapping("/unsubscribe")
    public ResponseEntity unsubscribe(@RequestParam String email,
                                      @RequestParam String topic,
                                      @RequestParam String reason){
        subscriptionService.unsubscribe(email, topic, reason);
        return new ResponseEntity("Successful", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getSubscription(@RequestParam String email, 
                                          @RequestParam String topic){
        return new ResponseEntity(subscriptionService.getSubscriptionInfo(email, topic), 
                HttpStatus.OK);
    }
    
    @GetMapping("/subscriber")
    public ResponseEntity getUserSUbscribedTopics(@RequestParam String email){
        return new ResponseEntity(subscriptionService.getTopicsForSubscriber(email),
                HttpStatus.OK);
    }

    @GetMapping("/subscribers")
    public ResponseEntity getUsersSubscribedToTopic(@RequestParam String topic){
        return new ResponseEntity(subscriptionService.getSubscribersForTopic(topic),
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateSubscription(@RequestBody SubscriptionRequestDto subscriptionRequestDto){
        return new ResponseEntity(subscriptionService.updateSubscription(subscriptionRequestDto),
                HttpStatus.OK);
    }
}
