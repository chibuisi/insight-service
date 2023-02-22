package com.chibuisi.dailyinsightservice.scheduler.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class MidnightSchedulers {
    @Scheduled(cron = "0 0 0 * * *")
    public void everyMidnightForNewWeekScheduler(){
        System.out.println("everyMidnightForNewWeekScheduler");
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void everyMidnightForNewMonthScheduler(){
        System.out.println("everyMidnightForNewMonthScheduler");
    }
}
