package com.chibuisi.dailyinsightservice.pubsub.service;

import com.chibuisi.dailyinsightservice.schedules.model.ReadySchedule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class PubSubServiceActivators {
    private final Logger LOGGER = Logger.getLogger(PubSubServiceActivators.class.getSimpleName());
    @Autowired
    private ObjectMapper objectMapper;

    // Create an outbound channel adapter to send messages from the output message channel to the topic `topic-two`.
    @Bean
    @ServiceActivator(inputChannel = "outputMessageChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
        PubSubMessageHandler adapter = new PubSubMessageHandler(pubsubTemplate, "email");

        adapter.setSuccessCallback(
                ((ackId, message) ->
                        LOGGER.info("Message was sent via the outbound channel adapter to email!")));

        adapter.setFailureCallback(
                (cause, message) -> LOGGER.info("Error sending " + message + " due to " + cause));

        return adapter;
    }

    //Define what happens to the messages arriving in the message channel.
    @ServiceActivator(inputChannel = "inputMessageChannel")
    public void messageReceiver(Message<?> message) throws MessagingException, JsonProcessingException {
        LOGGER.info("Message arrived via an inbound channel! Payload: "+ message);
        String payload = (String) message.getPayload();
        //ReadySchedule readySchedule1 = objectMapper.
        ReadySchedule readySchedule = objectMapper.readValue(message.getPayload().toString(),
               ReadySchedule.class);
        System.out.println(readySchedule);
        BasicAcknowledgeablePubsubMessage originalMessage =
                    message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
            originalMessage.ack();
    }
}
