package com.chibuisi.dailyinsightservice.scheduler.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class HourlySchedulers {
    @Scheduled(cron = "0 0 6 * * *")
    public void everyHourForDefaultTableScheduler(){
        System.out.println("everyHourForDefaultTableScheduler");
    }
    @Scheduled(cron = "0 0 * * * *")
    public void everyHourForDailyTableScheduler(){
        System.out.println("everyHourForDailyTableScheduler");
    }

    @Scheduled(cron = "0 0 * * * *")
    public void everyHourForWeeklyTableScheduler(){
        System.out.println("everyHourForWeeklyTableScheduler");
    }
    @Scheduled(cron = "0 0 * * * *")
    public void everyHourForMonthlyTableScheduler(){
        System.out.println("everyHourForMonthlyTableScheduler");
    }
}
