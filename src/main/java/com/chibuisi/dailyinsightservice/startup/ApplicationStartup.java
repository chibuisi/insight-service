package com.chibuisi.dailyinsightservice.startup;

import com.chibuisi.dailyinsightservice.pubsub.service.PubSubMessagingGateways;
import com.chibuisi.dailyinsightservice.scheduler.model.NewWeekOrMonth;
import com.chibuisi.dailyinsightservice.scheduler.model.enums.WeekOrMonth;
import com.chibuisi.dailyinsightservice.scheduler.service.NewWeekOrMonthService;
import com.chibuisi.dailyinsightservice.scheduler.util.NewWeekOrMonthUtil;
import com.chibuisi.dailyinsightservice.schedules.model.ReadySchedule;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ReadyScheduleStatus;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleType;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class ApplicationStartup 
implements ApplicationListener<ApplicationReadyEvent> {

  @Autowired
  private PubSubMessagingGateways.PubSubOutboundGateway pubSubOutboundGateway;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private NewWeekOrMonthUtil newWeekOrMonthUtil;

  private String timezone = "MST";

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    newWeekOrMonthUtil.updateNewWeekOrMonthTable();
    publishPubSubMessage();
  }

  private void publishPubSubMessage(){
    ReadySchedule readySchedule = ReadySchedule.builder()
            .id(1L).userId(5L).topic(SupportedTopics.COMPANY)
            .status(ReadyScheduleStatus.PROCESSED)
            .dateProcessed(LocalDateTime.now())
            .scheduleType(ScheduleType.DAILY).time(6).timezone("mst")
            .build();
    try {
      pubSubOutboundGateway
              .sendReadyScheduleToPubSub(objectMapper.writeValueAsString(readySchedule));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

}