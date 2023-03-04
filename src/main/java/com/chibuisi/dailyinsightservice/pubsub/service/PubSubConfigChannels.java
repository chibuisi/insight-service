package com.chibuisi.dailyinsightservice.pubsub.service;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.AckMode;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubMessageSource;
import com.google.cloud.spring.pubsub.support.BasicAcknowledgeablePubsubMessage;
import com.google.cloud.spring.pubsub.support.GcpPubSubHeaders;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class PubSubConfigChannels {

    @Bean
    public MessageChannel inputMessageChannel() {
        return new PublishSubscribeChannel();
    }

    //synchronous pull
//    @Bean
//    public PubSubInboundChannelAdapter inboundChannelAdapter(
//            @Qualifier("inputMessageChannel") MessageChannel messageChannel,
//            PubSubTemplate pubSubTemplate) {
//        PubSubInboundChannelAdapter adapter =
//                new PubSubInboundChannelAdapter(pubSubTemplate,
//                        "projects/is-daily-insights-dev/subscriptions/emailsubscription");
//        adapter.setOutputChannel(messageChannel);
//        adapter.setAckMode(AckMode.MANUAL);
//        adapter.setPayloadType(String.class);
//        adapter.healthCheckEnabled();
//        adapter.isAutoStartup();
//        return adapter;
//    }

    //asynchronous pull
    @Bean
    @InboundChannelAdapter(value = "inputMessageChannel", poller = @Poller(fixedDelay = "0", maxMessagesPerPoll = "1000"))
    public MessageSource<Object> synchronousPubSubMessageSource(PubSubTemplate pubSubTemplate) {
        PubSubMessageSource messageSource = new PubSubMessageSource(pubSubTemplate,
                "projects/is-daily-insights-dev/subscriptions/emailsubscription");
        messageSource.setAckMode(AckMode.MANUAL);
        messageSource.setPayloadType(String.class);
        messageSource.setMaxFetchSize(500);
        return messageSource;
    }


}
