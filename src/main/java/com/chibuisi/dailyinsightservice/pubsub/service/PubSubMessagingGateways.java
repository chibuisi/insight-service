package com.chibuisi.dailyinsightservice.pubsub.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

@Component
public class PubSubMessagingGateways {
    @MessagingGateway
    public interface PubSubOutboundGateway{
        @Gateway(requestChannel = "outputMessageChannel")
        void sendToPubSub(String text);
    }
//    @MessagingGateway
//    public interface PubSubInboundGateway{
//        @Gateway(requestChannel = "inputMessageChannel")
//        public void sendMessage(String message);
//    }
}
