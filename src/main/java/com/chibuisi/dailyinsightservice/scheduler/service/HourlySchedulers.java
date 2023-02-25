package com.chibuisi.dailyinsightservice.scheduler.service;

import com.chibuisi.dailyinsightservice.schedules.model.DailyCustomSchedule;
import com.chibuisi.dailyinsightservice.schedules.model.ReadySchedule;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ReadyScheduleStatus;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleType;
import com.chibuisi.dailyinsightservice.schedules.service.ReadyScheduleService;
import com.chibuisi.dailyinsightservice.schedules.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class HourlySchedulers {
    @Autowired
    private ReadyScheduleService readyScheduleService;
    @Autowired
    private ScheduleService scheduleService;

    @Scheduled(cron = "0 0 6 * * *")//every six am
    public void everyHourForDefaultTableScheduler(){
        System.out.println("everyHourForDefaultTableScheduler");
    }
    @Scheduled(cron = "0 0 * * * *")//every hour
    public void everyHourForDailyTableScheduler(){
        System.out.println("everyHourForDailyTableScheduler");
        List<DailyCustomSchedule> dailyCustomSchedules =
                scheduleService.getActiveDailyCustomSchedules();
        //get > 1 frequencies
        List<DailyCustomSchedule> unreadySchedulesForComingDays = dailyCustomSchedules
                .stream().filter(e -> e.getFrequencyCounter() > 1).collect(Collectors.toList());
        dailyCustomSchedules.removeIf(dailyCustomSchedule -> unreadySchedulesForComingDays.contains(dailyCustomSchedule));
        LocalDateTime localDateTime = LocalDateTime.now();
        Integer hour = localDateTime.getHour();
        dailyCustomSchedules.stream().filter(e -> e.getTime() == hour);
        //transform as ready schedules
        List<ReadySchedule> readySchedules = new ArrayList<>();
        dailyCustomSchedules.forEach(e -> {
            ReadySchedule readySchedule = ReadySchedule.builder()
                    .time(hour).scheduleType(ScheduleType.DAILY)
                    .dateProcessed(LocalDateTime.now()).status(ReadyScheduleStatus.PROCESSED)
                    .topic(e.getTopic()).userId(e.getUserId()).build();
            readySchedules.add(readySchedule);
        });
        readyScheduleService.saveReadySchedules(readySchedules);
        //reduce by 1
        unreadySchedulesForComingDays.forEach(e -> e.setFrequencyCounter(e.getFrequencyCounter()-1));
        dailyCustomSchedules.forEach(e -> {
            if(e.getFrequency() > e.getFrequencyCounter())
                e.setFrequencyCounter(e.getFrequency());
        });
        scheduleService.saveDailySchedules(unreadySchedulesForComingDays);
        scheduleService.saveDailySchedules(dailyCustomSchedules);
    }

    @Scheduled(cron = "0 0 * * * *")//every hour
    public void everyHourForWeeklyTableScheduler(){
        System.out.println("everyHourForWeeklyTableScheduler");
    }
    @Scheduled(cron = "0 0 * * * *")//every hour
    public void everyHourForMonthlyTableScheduler(){
        System.out.println("everyHourForMonthlyTableScheduler");
    }

    @Scheduled(cron = "0 0/30 * * * *")//every 30 minutes
    public void temporaryCleanUpReadySchedules(){
        System.out.println("Clean Up Schedule");
        readyScheduleService.cleanUpAllTableData();
    }

}
