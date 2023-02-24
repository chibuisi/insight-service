package com.chibuisi.dailyinsightservice.schedules.model;

import com.chibuisi.dailyinsightservice.schedules.model.enums.ReadyScheduleStatus;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleType;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ReadySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Integer time;
    private SupportedTopics topic;
    private String timezone;
    private LocalDateTime dateProcessed;
    private LocalDateTime dateSent;
    private ReadyScheduleStatus status;
    private ScheduleType scheduleType;
}
