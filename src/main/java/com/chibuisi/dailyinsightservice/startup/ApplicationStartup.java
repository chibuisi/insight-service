package com.chibuisi.dailyinsightservice.startup;

import com.chibuisi.dailyinsightservice.pubsub.service.PubSubMessagingGateways;
import com.chibuisi.dailyinsightservice.scheduler.model.NewWeekOrMonth;
import com.chibuisi.dailyinsightservice.scheduler.model.enums.WeekOrMonth;
import com.chibuisi.dailyinsightservice.scheduler.service.NewWeekOrMonthService;
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
  private NewWeekOrMonthService newWeekOrMonthService;
  private String timezone = "MST";
  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    publishPubSubMessage();
  }

  private void publishPubSubMessage(){
    pubSubOutboundGateway.sendToPubSub("Test and Read this Message");
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