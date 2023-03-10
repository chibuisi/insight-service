package com.chibuisi.dailyinsightservice.mail.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

@Component
public class EmailGateway {
    @MessagingGateway
    public interface EmailOutboundGateway{
        @Gateway(requestChannel = "outputEmailChannel")
        void sendMailTemplateToPubSub(String mailTemplate);
    }
}
