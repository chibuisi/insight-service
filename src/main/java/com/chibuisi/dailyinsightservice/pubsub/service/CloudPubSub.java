package com.chibuisi.dailyinsightservice.pubsub.service;

import com.google.cloud.pubsub.v1.MessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;

public class CloudPubSub {
    @Autowired
    public void messageChannelAdapter() {

//        pubSubConfig.getSubscriptionConfig().stream().forEach(subscription -> {
//
//            //log.info("Creating messageChannelAdapter for subscription: {}", subscription.getName());
//
//            String subscriptionId = subscription.getName();
//            ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(PROJECT_ID, subscriptionId);
//            Subscriber subscriber = null;
//
//            try {
//                // create a subscriber bound to the asynchronous message receiver
//                subscriber = Subscriber.newBuilder(subscriptionName, messageReceiver()).build();
//                subscriber.startAsync().awaitRunning();
//
//                String beanName = subscriber.toString();
//
//                applicationContext.getAutowireCapableBeanFactory().initializeBean(subscriber, beanName);
//
//            } finally {
//            }
//        });
    }

    public MessageReceiver messageReceiver() {

        return (message, consumer) -> {

            String messageStr = null;

            try {

                messageStr = message.getData().toStringUtf8();

                System.out.println("Payload: {}"+ message);

            } finally {
                System.out.println("-=- Acking message: {}"+ messageStr);
                consumer.ack();
                System.out.println("-=- Message Acked");
            }
        };
    }
}
