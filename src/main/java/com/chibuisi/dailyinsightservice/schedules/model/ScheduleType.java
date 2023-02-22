package com.chibuisi.dailyinsightservice.schedules.model;

import java.util.stream.Stream;

public enum ScheduleType {
    DEFAULT("default"),
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly");

    private String value;

    private ScheduleType(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public String getValue(){
        return this.value;
    }

    public static ScheduleType of(String name){
        return Stream.of(ScheduleType.values())
                .filter(s -> s.getValue().equalsIgnoreCase(name)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
