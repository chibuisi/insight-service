package com.chibuisi.dailyinsightservice.scheduler.service;

import com.chibuisi.dailyinsightservice.pubsub.service.PubSubMessagingGateways;
import com.chibuisi.dailyinsightservice.schedules.model.ReadySchedule;
import com.chibuisi.dailyinsightservice.schedules.service.ReadyScheduleService;
import com.chibuisi.dailyinsightservice.schedules.service.ScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class HourlyScheduleHelper {
    @Autowired
    private ReadyScheduleService readyScheduleService;
    @Autowired
    private PubSubMessagingGateways.PubSubOutboundGateway pubSubOutboundGateway;
    @Autowired
    private ObjectMapper objectMapper;
    @Async("publishToPubSubThreadPoolTaskExecutor")
    public void publishReadyScheduleToPubSub(ReadySchedule readySchedule){
        System.out.println("Publishing Ready Schedule to PubSub: "+ Thread.currentThread().getName());
        String payload = "";
        try {
            payload = objectMapper.writeValueAsString(readySchedule);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        pubSubOutboundGateway.sendReadyScheduleToPubSub(payload);
    }

    @Async("saveReadyScheduleThreadPoolTaskExecutor")
    @Transactional
    void saveReadySchedules(List<ReadySchedule> readySchedules){
        System.out.println("Saving Ready Schedule to Database: "+ Thread.currentThread().getName());
        readyScheduleService.saveReadySchedules(readySchedules);
    }


}
