package com.chibuisi.dailyinsightservice.startup;

import com.chibuisi.dailyinsightservice.pubsub.service.PubSubMessagingGateways;
import com.chibuisi.dailyinsightservice.scheduler.model.NewWeekOrMonth;
import com.chibuisi.dailyinsightservice.scheduler.model.enums.WeekOrMonth;
import com.chibuisi.dailyinsightservice.scheduler.service.NewWeekOrMonthService;
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
  private NewWeekOrMonthService newWeekOrMonthService;
  private String timezone = "MST";
  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    publishPubSubMessage();
  }

  private void publishPubSubMessage(){
    ReadySchedule readySchedule = ReadySchedule.builder()
            .id(3L).userId(1L).topic(SupportedTopics.COMPANY)
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

  public static void main(String [] args){
    ObjectMapper objectMapper = new ObjectMapper();
    ReadySchedule readySchedule = ReadySchedule.builder()
            .id(3L).userId(1L).topic(SupportedTopics.COMPANY)
            .status(ReadyScheduleStatus.PROCESSED)
            .dateProcessed(LocalDateTime.now())
            .scheduleType(ScheduleType.DAILY).time(6).timezone("mst")
            .build();
    try {
      System.out.println(objectMapper.writeValueAsString(readySchedule));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    System.out.println(readySchedule);
  }

  private void initiailizeNewWeekOrMonthTable(){
    LocalDateTime localDateTime = LocalDateTime.now();
    WeekFields weekFields = WeekFields.of(Locale.getDefault());
    Integer weekNum = localDateTime.get(weekFields.weekOfYear());
    Integer monthValue = localDateTime.getMonthValue();
    NewWeekOrMonth week = NewWeekOrMonth.builder()
            .last(weekNum).current(weekNum).name(WeekOrMonth.WEEK).build();
    NewWeekOrMonth month = NewWeekOrMonth.builder()
            .last(monthValue).current(monthValue).name(WeekOrMonth.MONTH).build();
    List<NewWeekOrMonth> newWeekOrMonthList = Arrays.asList(week,month);
    newWeekOrMonthService.saveNewWeekOrMonth(newWeekOrMonthList);
  }
}