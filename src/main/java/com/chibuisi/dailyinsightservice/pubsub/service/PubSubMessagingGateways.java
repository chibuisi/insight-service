package com.chibuisi.dailyinsightservice.pubsub.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

@Component
public class PubSubMessagingGateways {
    @MessagingGateway
    public interface PubSubOutboundGateway{
        @Gateway(requestChannel = "outputScheduleChannel")
        void sendReadyScheduleToPubSub(String readyScheduleString);
    }
//    @MessagingGateway
//    public interface PubSubInboundGateway{
//        @Gateway(requestChannel = "inputMessageChannel")
//        public void sendMessage(String message);
//    }
}
