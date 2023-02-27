package com.chibuisi.dailyinsightservice.schedules.model;

import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDTO {
    private Long userId;
    private String topic;
    private Integer time;
    private String timezone;
    private String scheduleType;
    private Integer frequency;
    private String scheduleDay;
    private Integer monthDay;

    public static DefaultSchedule createDefaultSchedule(ScheduleDTO scheduleDTO){
        DefaultSchedule defaultSchedule = DefaultSchedule.builder()
                .userId(scheduleDTO.getUserId())
                .topic(SupportedTopics.of(scheduleDTO.getTopic()))
                .timezone(scheduleDTO.getTimezone())
                .build();
        return defaultSchedule;
    }

    public static DailyCustomSchedule createDailyCustomSchedule
            (ScheduleDTO scheduleDTO){
        DailyCustomSchedule dailyCustomSchedule = DailyCustomSchedule.builder()
                .userId(scheduleDTO.getUserId())
                .topic(SupportedTopics.of(scheduleDTO.getTopic()))
                .timezone(scheduleDTO.getTimezone())
                .frequency(getValidMonthDay(scheduleDTO))
                .frequencyCounter(getValidMonthDay(scheduleDTO))
                .time(scheduleDTO.getTime())
                .build();
        return dailyCustomSchedule;
    }

    public static WeeklyCustomSchedule createWeeklyCustomSchedule
            (ScheduleDTO scheduleDTO){
        WeeklyCustomSchedule weeklyCustomSchedule = WeeklyCustomSchedule.builder()
                .userId(scheduleDTO.getUserId())
                .topic(SupportedTopics.of(scheduleDTO.getTopic()))
                .timezone(scheduleDTO.getTimezone())
                .time(scheduleDTO.getTime())
                .frequency(getValidWeeklyFrequency(scheduleDTO.getFrequency()))
                .frequencyCounter(getValidWeeklyFrequency(scheduleDTO.getFrequency()))
                .build();
        return weeklyCustomSchedule;
    }

    public static MonthlyCustomSchedule createMonthlyCustomSchedule
            (ScheduleDTO scheduleDTO){
        MonthlyCustomSchedule monthlyCustomSchedule = MonthlyCustomSchedule.builder()
                .userId(scheduleDTO.getUserId())
                .topic(SupportedTopics.of(scheduleDTO.getTopic()))
                .timezone(scheduleDTO.getTimezone())
                .time(scheduleDTO.getTime())
                .frequency(getValidMonthlyFrequency(scheduleDTO.getFrequency()))
                .frequencyCounter(getValidMonthlyFrequency(scheduleDTO.getFrequency()))
                .build();
        return monthlyCustomSchedule;
    }

    public static Integer getValidWeeklyFrequency(Integer value){
        if(value == null || value < 1 || value > 4)
            throw new IllegalArgumentException("Weekly Interval is between 1 and 4");
        return value;
    }
    public static Integer getValidMonthlyFrequency(Integer value){
        if(value == null || value < 1 || value > 12)
            throw new IllegalArgumentException("Monthly Interval is between 1 and 12");
        return value;
    }

    public static Integer getValidMonthDay(ScheduleDTO scheduleDTO){
        Integer monthDay = scheduleDTO.getMonthDay();
        if(monthDay == null || monthDay < 1 || monthDay > 31)
            throw new IllegalArgumentException("Month day is between 1 and 31");
        return monthDay;
    }
}
