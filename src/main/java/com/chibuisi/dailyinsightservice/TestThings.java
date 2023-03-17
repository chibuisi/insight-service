package com.chibuisi.dailyinsightservice;

import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleType;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestThings {
    public static void main(String[] args) {
        ScheduleType scheduleType = ScheduleType.DAILY;
        List<ScheduleType> scheduleTypes = new ArrayList<>(
                Arrays.asList(ScheduleType.DAILY,ScheduleType.MONTHLY, ScheduleType.WEEKLY));
        //scheduleTypes.removeIf(e -> e.equals(scheduleType));
        scheduleTypes.forEach(e -> scheduleTypes.remove(e));
        System.out.println(scheduleTypes);
    }
}
