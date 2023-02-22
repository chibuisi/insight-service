package com.chibuisi.dailyinsightservice.schedules.service;

import com.chibuisi.dailyinsightservice.schedules.model.*;
import com.chibuisi.dailyinsightservice.schedules.repository.DailyCustomScheduleRepository;
import com.chibuisi.dailyinsightservice.schedules.repository.DefaultScheduleRepository;
import com.chibuisi.dailyinsightservice.schedules.repository.MonthlyCustomScheduleRepository;
import com.chibuisi.dailyinsightservice.schedules.repository.WeeklyCustomScheduleRepository;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private DefaultScheduleRepository defaultScheduleRepository;
    @Autowired
    private DailyCustomScheduleRepository dailyCustomScheduleRepository;
    @Autowired
    private WeeklyCustomScheduleRepository weeklyCustomScheduleRepository;
    @Autowired
    private MonthlyCustomScheduleRepository monthlyCustomScheduleRepository;

    public Schedule saveSchedule(ScheduleDTO scheduleDTO){
        SupportedTopics topic = SupportedTopics.of(scheduleDTO.getTopic());
        Schedule schedule = getScheduleInstance(scheduleDTO);
        if(schedule instanceof DailyCustomSchedule){
            DailyCustomSchedule existing = dailyCustomScheduleRepository
                    .findDailyCustomScheduleByUserIdAndTopic(scheduleDTO.getUserId(),
                            topic);
            if(existing != null)
                return null;
            DailyCustomSchedule dailyCustomSchedule = (DailyCustomSchedule) schedule;
            dailyCustomSchedule.setStatus(ScheduleStatus.ACTIVE);
            return dailyCustomScheduleRepository.save(dailyCustomSchedule);
        }
        else if(schedule instanceof WeeklyCustomSchedule){
            WeeklyCustomSchedule existing = weeklyCustomScheduleRepository
                    .findWeeklyCustomScheduleByUserIdAndTopic(scheduleDTO.getUserId(),
                            topic);
            if(existing != null)
                return null;
            WeeklyCustomSchedule weeklyCustomSchedule = (WeeklyCustomSchedule) schedule;
            weeklyCustomSchedule.setStatus(ScheduleStatus.ACTIVE);
            return weeklyCustomScheduleRepository.save(weeklyCustomSchedule);
        }
        else if(schedule instanceof MonthlyCustomSchedule){
            MonthlyCustomSchedule existing = monthlyCustomScheduleRepository
                    .findMonthlyCustomScheduleByUserIdAndTopic(scheduleDTO.getUserId(),
                            topic);
            if(existing != null)
                return null;
            MonthlyCustomSchedule monthlyCustomSchedule = (MonthlyCustomSchedule) schedule;
            monthlyCustomSchedule.setStatus(ScheduleStatus.ACTIVE);
            return monthlyCustomScheduleRepository.save(monthlyCustomSchedule);
        }
        else {
            DefaultSchedule existing = defaultScheduleRepository
                    .findDefaultScheduleByUserIdAndTopic(scheduleDTO.getUserId(), topic);
            if(existing != null)
                return null;
            DefaultSchedule defaultSchedule = (DefaultSchedule) schedule;
            defaultSchedule.setStatus(ScheduleStatus.ACTIVE);
            return defaultScheduleRepository.save(defaultSchedule);
        }
    }

    public void deleteSchedule(Long userId, String topic, String scheduleType){
        ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                .userId(userId).topic(topic).scheduleType(scheduleType).build();
        SupportedTopics foundTopic = SupportedTopics.of(scheduleDTO.getTopic());
        Schedule schedule = getScheduleInstance(scheduleDTO);
        if(schedule instanceof DailyCustomSchedule){
            DailyCustomSchedule existing = dailyCustomScheduleRepository
                    .findDailyCustomScheduleByUserIdAndTopic(scheduleDTO.getUserId(),
                            foundTopic);
            if(existing == null)
                return;
            existing.setStatus(ScheduleStatus.INACTIVE);
            dailyCustomScheduleRepository.save(existing);
        }
        else if(schedule instanceof WeeklyCustomSchedule){
            WeeklyCustomSchedule existing = weeklyCustomScheduleRepository
                    .findWeeklyCustomScheduleByUserIdAndTopic(scheduleDTO.getUserId(),
                            foundTopic);
            if(existing != null)
                return;
            WeeklyCustomSchedule weeklyCustomSchedule = (WeeklyCustomSchedule) schedule;
            existing.setStatus(ScheduleStatus.INACTIVE);
            weeklyCustomScheduleRepository.save(weeklyCustomSchedule);
        }
        else if(schedule instanceof MonthlyCustomSchedule){
            MonthlyCustomSchedule existing = monthlyCustomScheduleRepository
                    .findMonthlyCustomScheduleByUserIdAndTopic(scheduleDTO.getUserId(),
                            foundTopic);
            if(existing != null)
                return;
            MonthlyCustomSchedule monthlyCustomSchedule = (MonthlyCustomSchedule) schedule;
            existing.setStatus(ScheduleStatus.INACTIVE);
            monthlyCustomScheduleRepository.save(monthlyCustomSchedule);
        }
        else {
            DefaultSchedule existing = defaultScheduleRepository
                    .findDefaultScheduleByUserIdAndTopic(scheduleDTO.getUserId(), foundTopic);
            if(existing != null)
                return;
            DefaultSchedule defaultSchedule = (DefaultSchedule) schedule;
            existing.setStatus(ScheduleStatus.INACTIVE);
            defaultScheduleRepository.save(defaultSchedule);
        }
    }



    private Schedule getScheduleInstance(ScheduleDTO scheduleDTO){
        ScheduleType scheduleType = ScheduleType.of(scheduleDTO.getScheduleType());
        SupportedTopics topic = SupportedTopics.of(scheduleDTO.getTopic());
        switch (scheduleType){
            case DAILY:{
                DailyCustomSchedule dailyCustomSchedule =
                        ScheduleDTO.createDailyCustomSchedule(scheduleDTO);
                return dailyCustomSchedule;
            }
            case WEEKLY:{
                WeeklyCustomSchedule weeklyCustomSchedule =
                        ScheduleDTO.createWeeklyCustomSchedule(scheduleDTO);
                return weeklyCustomSchedule;
            }
            case MONTHLY:{
                MonthlyCustomSchedule monthlyCustomSchedule =
                        ScheduleDTO.createMonthlyCustomSchedule(scheduleDTO);
                return monthlyCustomSchedule;
            }
            default:{
                DefaultSchedule defaultSchedule =
                        ScheduleDTO.createDefaultSchedule(scheduleDTO);
                return defaultSchedule;
            }
        }
    }
}
