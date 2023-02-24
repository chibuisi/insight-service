package com.chibuisi.dailyinsightservice.schedules.model.enums;

import java.util.stream.Stream;

public enum ReadyScheduleStatus {
    UNSENT("unsent"),
    SENT("sent");

    private String value;

    private ReadyScheduleStatus(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public String getValue(){
        return this.value;
    }

    public static ReadyScheduleStatus of(String name){
        return Stream.of(ReadyScheduleStatus.values())
                .filter(s -> s.getValue().equalsIgnoreCase(name)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
