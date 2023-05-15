package com.chibuisi.dailyinsightservice;

import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleType;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;

import java.util.*;

public class TestThings {
    public static void main(String[] args) {
        ScheduleType scheduleType = ScheduleType.DAILY;
        List<ScheduleType> scheduleTypes = new ArrayList<>(
                Arrays.asList(ScheduleType.DAILY,ScheduleType.MONTHLY, ScheduleType.WEEKLY));
        //scheduleTypes.removeIf(e -> e.equals(scheduleType));
        scheduleTypes.forEach(e -> scheduleTypes.remove(e));
        System.out.println(scheduleTypes);
        Set<Character> set = new HashSet<>();
        set.add('c');
        set.remove(0);
    }
}
