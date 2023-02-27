package com.chibuisi.dailyinsightservice.scheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class MidnightSchedulers {
    @Autowired
    private NewWeekOrMonthService newWeekOrMonthService;

    @Scheduled(cron = "0 0 0 * * *")
    public void everyMidnightForNewWeekScheduler(){
        System.out.println("everyMidnightForNewWeekScheduler");
        int count = newWeekOrMonthService.checkForNewWeek();
        System.out.println("Updated frequency counters of "+count+" records");
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void everyMidnightForNewMonthScheduler(){
        System.out.println("everyMidnightForNewMonthScheduler");
        int count = newWeekOrMonthService.checkForNewMonth();
        System.out.println("Updated frequency counters of "+count+" records");
    }
}
