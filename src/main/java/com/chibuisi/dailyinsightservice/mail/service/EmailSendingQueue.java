package com.chibuisi.dailyinsightservice.mail.service;

import com.chibuisi.dailyinsightservice.mail.service.serviceimpl.JavaMailService;
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

import javax.mail.internet.MimeMessage;
import java.util.logging.Logger;

@Component
public class EmailSendingQueue {
    private final Logger LOGGER = Logger.getLogger(EmailSendingQueue.class.getSimpleName());
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JavaMailService javaMailService;

    // Create an outbound channel adapter to send messages from the output message channel to the topic `topic-two`.
    @Bean
    @ServiceActivator(inputChannel = "outputEmailChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
        PubSubMessageHandler adapter = new PubSubMessageHandler(pubsubTemplate, "email");

        adapter.setSuccessCallback(
                ((ackId, message) ->
                        LOGGER.info("Mail Template was published via the outbound channel adapter to email!")));

        adapter.setFailureCallback(
                (cause, message) -> LOGGER.info("Error pubishing " + message + " due to " + cause));

        return adapter;
    }

    //Define what happens to the messages arriving in the message channel.
    @ServiceActivator(inputChannel = "inputEmailChannel")
    public void messageReceiver(Message<?> message) throws MessagingException, JsonProcessingException {
        LOGGER.info("Message arrived via an inbound channel! Payload: "+ message);
        String payload = (String) message.getPayload();
        MimeMessage mimeMessage = objectMapper.readValue(message.getPayload().toString(),
               MimeMessage.class);
        System.out.println(mimeMessage);
        javaMailService.sendMail(mimeMessage);
        BasicAcknowledgeablePubsubMessage originalMessage =
                    message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
            originalMessage.ack();
    }
}
