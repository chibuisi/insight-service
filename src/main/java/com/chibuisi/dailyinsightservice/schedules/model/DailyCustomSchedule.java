package com.chibuisi.dailyinsightservice.schedules.model;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class DailyCustomSchedule extends Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private SupportedTopics topic;
    private String time;
    private String timezone;
    private ScheduleStatus status;
}
