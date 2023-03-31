package com.chibuisi.dailyinsightservice.pubsub.service;

import com.chibuisi.dailyinsightservice.schedules.model.ReadySchedule;
import com.chibuisi.dailyinsightservice.template.TemplateService;
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
public class ScheduleServiceActivators {
    private final Logger LOGGER = Logger.getLogger(ScheduleServiceActivators.class.getSimpleName());
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TemplateService templateService;

    // Create an outbound channel adapter to send messages from the output message channel to the topic `topic-two`.
    @Bean
    @ServiceActivator(inputChannel = "outputScheduleChannel")
    public MessageHandler scheduleMessageSender(PubSubTemplate pubsubTemplate) {
        PubSubMessageHandler adapter = new PubSubMessageHandler(pubsubTemplate, "schedule");

        adapter.setSuccessCallback(
                ((ackId, message) ->
                        LOGGER.info("Message was sent via the schedule outbound channel adapter to email pubsub topic!")));

        adapter.setFailureCallback(
                (cause, message) -> LOGGER.info("Error sending " + message + " due to " + cause));

        return adapter;
    }

    //Define what happens to the messages arriving in the message channel.
    @ServiceActivator(inputChannel = "inputScheduleChannel")
    public void scheduleMessageReceiver(Message<?> message) throws MessagingException, JsonProcessingException {
        LOGGER.info("ready schedule arrived via an schedule inbound channel!");
        BasicAcknowledgeablePubsubMessage originalMessage =
                message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
        originalMessage.ack();
        ReadySchedule readySchedule = objectMapper.readValue(message.getPayload().toString(),
               ReadySchedule.class);
        templateService.createTemplate(readySchedule);
    }
}
