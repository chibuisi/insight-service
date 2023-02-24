package com.chibuisi.dailyinsightservice.schedules.model;

import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class Schedule {
    private ScheduleStatus status;
}
