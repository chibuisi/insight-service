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
    private ScheduleService scheduleService;
    @Autowired
    private HourlyScheduleHelper hourlyScheduleHelper;

    @Scheduled(cron = "0 0 6 * * *")//every six am
    public void everySixAMForDefaultTableScheduler(){
        System.out.println("everyHourForDefaultTableScheduler: "+Thread.currentThread().getName());
        List<DefaultSchedule> defaultSchedules =
                scheduleService.getActiveDefaultSchedules();
        if(defaultSchedules != null && defaultSchedules.size() > 0){
            //List<ReadySchedule> readySchedules = new ArrayList<>();
            defaultSchedules.forEach(e -> {
                ReadySchedule readySchedule = ReadySchedule.builder()
                        .time(6).scheduleType(ScheduleType.DAILY)
                        .dateProcessed(LocalDateTime.now()).status(ReadyScheduleStatus.PROCESSED)
                        .topic(e.getTopic()).userId(e.getUserId()).build();
                hourlyScheduleHelper.publishReadyScheduleToPubSub(readySchedule);
                //readySchedules.add(readySchedule);
                hourlyScheduleHelper.saveReadySchedule(readySchedule);
            });
            //hourlyScheduleHelper.saveReadySchedules(readySchedules);
            scheduleService.saveDefaultSchedules(defaultSchedules);
        }
    }
    @Scheduled(cron = "0 0/54 1-23 * * *")//every hour except 00:00AM
    public void everyHourForDailyTableScheduler(){
        System.out.println("everyHourForDailyTableScheduler: " + Thread.currentThread().getName());
        List<DailyCustomSchedule> dailyCustomSchedules =
                scheduleService.getActiveDailyCustomSchedules();
        //transform as ready schedules if list not empty
        if(dailyCustomSchedules != null && dailyCustomSchedules.size() > 0){
            //List<ReadySchedule> readySchedules = new ArrayList<>();
            dailyCustomSchedules.forEach(e -> {
                ReadySchedule readySchedule = ReadySchedule.builder()
                        .time(e.getTime()).scheduleType(ScheduleType.DAILY)
                        .dateProcessed(LocalDateTime.now()).status(ReadyScheduleStatus.PROCESSED)
                        .topic(e.getTopic()).userId(e.getUserId()).build();
                hourlyScheduleHelper.publishReadyScheduleToPubSub(readySchedule);
                //readySchedules.add(readySchedule);
                hourlyScheduleHelper.saveReadySchedule(readySchedule);
                if(e.getFrequency() > e.getFrequencyCounter()){
                    e.setFrequencyCounter(e.getFrequency());
                }
            });
            //hourlyScheduleHelper.saveReadySchedules(readySchedules);
            scheduleService.saveDailySchedules(dailyCustomSchedules);
        }

    }
    @Scheduled(cron = "0 0 1-23 * * *")//every hour except 00:00AM
    public void everyHourForWeeklyTableScheduler(){
        System.out.println("everyHourForWeeklyTableScheduler: " + Thread.currentThread().getName());
        List<WeeklyCustomSchedule> weeklyCustomSchedules =
                scheduleService.getActiveWeeklyCustomSchedules();
        if(weeklyCustomSchedules != null && weeklyCustomSchedules.size() > 0){
            //List<ReadySchedule> readySchedules = new ArrayList<>();
            weeklyCustomSchedules.forEach(e -> {
                ReadySchedule readySchedule = ReadySchedule.builder()
                        .time(e.getTime()).scheduleType(ScheduleType.DAILY)
                        .dateProcessed(LocalDateTime.now()).status(ReadyScheduleStatus.PROCESSED)
                        .topic(e.getTopic()).userId(e.getUserId()).build();
                hourlyScheduleHelper.publishReadyScheduleToPubSub(readySchedule);
                //readySchedules.add(readySchedule);
                hourlyScheduleHelper.saveReadySchedule(readySchedule);
                if(e.getFrequency() > e.getFrequencyCounter()){
                    e.setFrequencyCounter(e.getFrequency());
                }
            });
            //hourlyScheduleHelper.saveReadySchedules(readySchedules);
            scheduleService.saveWeeklySchedules(weeklyCustomSchedules);
        }
    }
    @Scheduled(cron = "0 0 1-23 * * *")//every hour except 00:00AM
    public void everyHourForMonthlyTableScheduler(){
        System.out.println("everyHourForMonthlyTableScheduler: " + Thread.currentThread().getName());
        List<MonthlyCustomSchedule> monthlyCustomSchedules =
                scheduleService.getActiveMonthlyCustomSchedules();
        if(monthlyCustomSchedules != null && monthlyCustomSchedules.size() > 0){
            //List<ReadySchedule> readySchedules = new ArrayList<>();
            monthlyCustomSchedules.forEach(e -> {
                ReadySchedule readySchedule = ReadySchedule.builder()
                        .time(e.getTime()).scheduleType(ScheduleType.DAILY)
                        .dateProcessed(LocalDateTime.now()).status(ReadyScheduleStatus.PROCESSED)
                        .topic(e.getTopic()).userId(e.getUserId()).build();
                //readySchedules.add(readySchedule);
                hourlyScheduleHelper.publishReadyScheduleToPubSub(readySchedule);
                //readySchedules.add(readySchedule);
                hourlyScheduleHelper.saveReadySchedule(readySchedule);
                if(e.getFrequency() > e.getFrequencyCounter()){
                    e.setFrequencyCounter(e.getFrequency());
                }
            });
            //hourlyScheduleHelper.saveReadySchedules(readySchedules);
            scheduleService.saveMonthlySchedules(monthlyCustomSchedules);
        }
    }

}
