package com.chibuisi.dailyinsightservice.schedules.model.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ScheduleDay {
    SUNDAY("sunday"),
    MONDAY("monday"),
    TUESDAY("tuesday"),
    WEDNESDAY("wednesday"),
    THURSDAY("thursday"),
    FRIDAY("friday"),
    SATURDAY("saturday");

    private String value;

    private ScheduleDay(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public String getValue(){
        return this.value;
    }

    public static ScheduleDay of(String name){
        return Stream.of(ScheduleDay.values())
                .filter(s -> s.getValue().equalsIgnoreCase(name)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<ScheduleDay> of(List<String> scheduleDayStrings){
        List<ScheduleDay> scheduleDays = scheduleDayStrings.stream()
                .map(e -> ScheduleDay.of(e)).collect(Collectors.toList());
        return scheduleDays;
    }
}
