package com.chibuisi.dailyinsightservice.scheduler.model.enums;

public enum WeekOrMonth {
    WEEK("W"),
    MONTH("M");

    private Integer value;
    private String code;

    private WeekOrMonth(String code){
        this.code = code;
    }
    private WeekOrMonth(){
    }

    public Integer getValue(){
        return this.value;
    }
    public String getCode(){
        return this.code;
    }

    @Override
    public String toString() {
        return this.name();
    }

}
