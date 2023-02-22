package com.chibuisi.dailyinsightservice.schedules.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class Schedule {
    private ScheduleStatus status;
}
