package com.chibuisi.dailyinsightservice.scheduler.service;

import com.chibuisi.dailyinsightservice.pubsub.service.PubSubMessagingGateways;
import com.chibuisi.dailyinsightservice.schedules.model.*;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ReadyScheduleStatus;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleType;
import com.chibuisi.dailyinsightservice.schedules.service.ReadyScheduleService;
import com.chibuisi.dailyinsightservice.schedules.service.ScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class HourlySchedulers {
    @Autowired
    private ReadyScheduleService readyScheduleService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private PubSubMessagingGateways.PubSubOutboundGateway pubSubOutboundGateway;
    @Autowired
    private ObjectMapper objectMapper;

    private String timezone = "MST";

    @Scheduled(cron = "0 0 6 * * *")//every six am
    public void everySixAMForDefaultTableScheduler(){
        System.out.println("everyHourForDefaultTableScheduler");
        List<DefaultSchedule> defaultSchedules =
                scheduleService.getActiveDefaultSchedules();
        List<ReadySchedule> readySchedules = new ArrayList<>();
        defaultSchedules.forEach(e -> {
            ReadySchedule readySchedule = ReadySchedule.builder()
                    .time(6).scheduleType(ScheduleType.DAILY)
                    .dateProcessed(LocalDateTime.now()).status(ReadyScheduleStatus.PROCESSED)
                    .topic(e.getTopic()).userId(e.getUserId()).build();
            readySchedules.add(readySchedule);
        });
        publishReadySchedulesToPubSub(readySchedules);
        saveReadySchedules(readySchedules);
        scheduleService.saveDefaultSchedules(defaultSchedules);
    }
    @Scheduled(cron = "0 0 * * * *")//every hour
    public void everyHourForDailyTableScheduler(){
        System.out.println("everyHourForDailyTableScheduler");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timezone, ZoneId.SHORT_IDS));
        List<DailyCustomSchedule> dailyCustomSchedules =
                scheduleService.getActiveDailyCustomSchedules(zonedDateTime.getHour());
        //transform as ready schedules
        List<ReadySchedule> readySchedules = new ArrayList<>();
        dailyCustomSchedules.forEach(e -> {
            ReadySchedule readySchedule = ReadySchedule.builder()
                    .time(e.getTime()).scheduleType(ScheduleType.DAILY)
                    .dateProcessed(LocalDateTime.now()).status(ReadyScheduleStatus.PROCESSED)
                    .topic(e.getTopic()).userId(e.getUserId()).build();
            readySchedules.add(readySchedule);
            if(e.getFrequency() > e.getFrequencyCounter()){
                e.setFrequencyCounter(e.getFrequency());
            }
        });
        publishReadySchedulesToPubSub(readySchedules);
        saveReadySchedules(readySchedules);
        scheduleService.saveDailySchedules(dailyCustomSchedules);
    }
    @Scheduled(cron = "0 0 * * * *")//every hour
    public void everyHourForWeeklyTableScheduler(){
        System.out.println("everyHourForWeeklyTableScheduler");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timezone, ZoneId.SHORT_IDS));
        List<WeeklyCustomSchedule> weeklyCustomSchedules =
                scheduleService.getActiveWeeklyCustomSchedules(zonedDateTime.getHour());
        List<ReadySchedule> readySchedules = new ArrayList<>();
        weeklyCustomSchedules.forEach(e -> {
            ReadySchedule readySchedule = ReadySchedule.builder()
                    .time(e.getTime()).scheduleType(ScheduleType.DAILY)
                    .dateProcessed(LocalDateTime.now()).status(ReadyScheduleStatus.PROCESSED)
                    .topic(e.getTopic()).userId(e.getUserId()).build();
            readySchedules.add(readySchedule);
            if(e.getFrequency() > e.getFrequencyCounter()){
                e.setFrequencyCounter(e.getFrequency());
            }
        });
        publishReadySchedulesToPubSub(readySchedules);
        saveReadySchedules(readySchedules);
        scheduleService.saveWeeklySchedules(weeklyCustomSchedules);
    }
    @Scheduled(cron = "0 0 * * * *")//every hour
    public void everyHourForMonthlyTableScheduler(){
        System.out.println("everyHourForMonthlyTableScheduler");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timezone, ZoneId.SHORT_IDS));
        List<MonthlyCustomSchedule> monthlyCustomSchedules =
                scheduleService.getActiveMonthlyCustomSchedules(zonedDateTime.getHour());
        List<ReadySchedule> readySchedules = new ArrayList<>();
        monthlyCustomSchedules.forEach(e -> {
            ReadySchedule readySchedule = ReadySchedule.builder()
                    .time(e.getTime()).scheduleType(ScheduleType.DAILY)
                    .dateProcessed(LocalDateTime.now()).status(ReadyScheduleStatus.PROCESSED)
                    .topic(e.getTopic()).userId(e.getUserId()).build();
            readySchedules.add(readySchedule);
            if(e.getFrequency() > e.getFrequencyCounter()){
                e.setFrequencyCounter(e.getFrequency());
            }
        });
        publishReadySchedulesToPubSub(readySchedules);
        saveReadySchedules(readySchedules);
        scheduleService.saveMonthlySchedules(monthlyCustomSchedules);
    }

    @Async
    void publishReadySchedulesToPubSub(List<ReadySchedule> readySchedules){
        readySchedules.forEach(readySchedule -> {
            String payload = "";
            try {
                payload = objectMapper.writeValueAsString(readySchedule);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            pubSubOutboundGateway.sendReadyScheduleToPubSub(payload);
        });
    }

    @Async
    @Transactional
    void saveReadySchedules(List<ReadySchedule> readySchedules){
        readyScheduleService.saveReadySchedules(readySchedules);
    }

    //@Scheduled(cron = "0 */30 * * * *")//every 30 minutes
    public void temporaryCleanUpReadySchedules(){
        System.out.println("Clean Up Schedule");
        readyScheduleService.cleanUpAllTableData();
    }

}
