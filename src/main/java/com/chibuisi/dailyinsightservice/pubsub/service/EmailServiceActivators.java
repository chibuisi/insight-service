package com.chibuisi.dailyinsightservice.pubsub.service;

import com.chibuisi.dailyinsightservice.mail.model.TemplateHelper;
import com.chibuisi.dailyinsightservice.mail.service.serviceimpl.JavaMailService;
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
public class EmailServiceActivators {
    private final Logger LOGGER = Logger.getLogger(EmailServiceActivators.class.getSimpleName());
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JavaMailService javaMailService;

    // Create an outbound channel adapter to send messages from the output message channel to the topic `topic-two`.
    @Bean
    @ServiceActivator(inputChannel = "outputEmailChannel")
    public MessageHandler emailMessageSender(PubSubTemplate pubsubTemplate) {
        PubSubMessageHandler adapter = new PubSubMessageHandler(pubsubTemplate, "email");

        adapter.setSuccessCallback(
                ((ackId, message) ->
                        LOGGER.info("Mail Template was published via the outbound channel adapter to email!")));

        adapter.setFailureCallback(
                (cause, message) -> LOGGER.info("Error pubishing " + message + " due to " + cause));
//
        return adapter;
    }

    //Define what happens to the messages arriving in the message channel.
    @ServiceActivator(inputChannel = "inputEmailChannel")
    public void emailMessageReceiver(Message<?> message) throws MessagingException, JsonProcessingException {
        //LOGGER.info("Message arrived via an inbound channel! Payload: "+ message);
        String payload = (String) message.getPayload();
        TemplateHelper templateHelper = objectMapper.readValue(message.getPayload().toString(),
               TemplateHelper.class);
        //System.out.println(templateHelper);
        javaMailService.sendMail(templateHelper);
        BasicAcknowledgeablePubsubMessage originalMessage =
                    message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
            originalMessage.ack();
    }
}
