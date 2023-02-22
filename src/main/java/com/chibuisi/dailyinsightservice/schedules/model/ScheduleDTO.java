package com.chibuisi.dailyinsightservice.schedules.model;

import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleDTO {
    private Long userId;
    private String topic;
    private String time;
    private String timezone;
    private String scheduleType;
    private Integer frequency;

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
                .frequency(scheduleDTO.getFrequency())
                .frequencyCounter(scheduleDTO.getFrequency())
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
                .frequency(scheduleDTO.getFrequency())
                .frequencyCounter(scheduleDTO.getFrequency())
                .build();
        return monthlyCustomSchedule;
    }
}
