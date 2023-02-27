package com.chibuisi.dailyinsightservice.scheduler.model.enums;

public enum WeekOrMonth {
    WEEK("week"),
    MONTH("month");

    private String value;

    private WeekOrMonth(String value){
        this.value = value;
    }
}
