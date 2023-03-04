package com.chibuisi.dailyinsightservice.pubsub.service;

import com.chibuisi.dailyinsightservice.pubsub.model.PubSubMessageCommand;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CloudPubSubPublisher {
    private static final Logger logger = Logger.getLogger(CloudPubSubPublisher.class.getSimpleName());
    private final String PROJECT_ID = "project-cloud-322220";
    private final String TOPIC ="price";

    public String publish(String cryptoPrice) throws IOException {
        PubSubMessageCommand messageCommand = PubSubMessageCommand.builder().projectId(PROJECT_ID).topic(TOPIC).message(cryptoPrice.toString()).build();
        ByteString byteStr = ByteString.copyFrom(messageCommand.getMessage(), StandardCharsets.UTF_8);
        PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(byteStr).build();
        Publisher publisher = Publisher.newBuilder(ProjectTopicName.of(messageCommand.getProjectId(), messageCommand.getTopic())).build();
        String responseMessage;
        try {
            publisher.publish(pubsubMessage).get();
            responseMessage = "Message published.";
        } catch (InterruptedException | ExecutionException e) {
            logger.log(Level.SEVERE, "Error publishing Pub/Sub message: " + e.getMessage(), e);
            responseMessage = "Error publishing Pub/Sub message; see logs for more info.";
        }
        return responseMessage;
    }
}
