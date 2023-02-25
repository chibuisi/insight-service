package com.chibuisi.dailyinsightservice.schedules.model;

import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleDay;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleStatus;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class WeeklyCustomSchedule extends Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private SupportedTopics topic;
    private Integer frequency;
    private Integer frequencyCounter;
    private ScheduleDay scheduleDay;
    private Integer time;
    private String timezone;
    private ScheduleStatus status;
}
