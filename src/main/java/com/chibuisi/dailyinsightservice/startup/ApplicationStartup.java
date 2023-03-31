package com.chibuisi.dailyinsightservice.startup;

import com.chibuisi.dailyinsightservice.scheduler.service.HourlyScheduleHelper;
import com.chibuisi.dailyinsightservice.schedules.model.ReadySchedule;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ReadyScheduleStatus;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleType;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ApplicationStartup 
implements ApplicationListener<ApplicationReadyEvent> {

  @Autowired
  private HourlyScheduleHelper hourlyScheduleHelper;

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    //publishPubSubMessage();
  }

  private void publishPubSubMessage(){
    ReadySchedule readySchedule1 = ReadySchedule.builder()
            .id(1L).userId(1L).topic(SupportedTopics.WORD)
            .status(ReadyScheduleStatus.PROCESSED)
            .dateProcessed(LocalDateTime.now())
            .scheduleType(ScheduleType.DAILY).time(8).timezone("mst")
            .build();
//    ReadySchedule readySchedule2 = ReadySchedule.builder()
//            .id(2L).userId(2L).topic(SupportedTopics.COMPANY)
//            .status(ReadyScheduleStatus.PROCESSED)
//            .dateProcessed(LocalDateTime.now())
//            .scheduleType(ScheduleType.DAILY).time(20).timezone("mst")
//            .build();
//    ReadySchedule readySchedule3 = ReadySchedule.builder()
//            .id(3L).userId(3L).topic(SupportedTopics.COMPANY)
//            .status(ReadyScheduleStatus.PROCESSED)
//            .dateProcessed(LocalDateTime.now())
//            .scheduleType(ScheduleType.DAILY).time(20).timezone("mst")
//            .build();
    hourlyScheduleHelper.publishReadyScheduleToPubSub(readySchedule1);
    //hourlyScheduleHelper.publishReadyScheduleToPubSub(readySchedule2);
    //hourlyScheduleHelper.publishReadyScheduleToPubSub(readySchedule3);
  }

}