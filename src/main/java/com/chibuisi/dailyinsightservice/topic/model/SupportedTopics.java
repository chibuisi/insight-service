package com.chibuisi.dailyinsightservice.topic.model;

public enum SupportedTopics {
    MOTIVATION(1),
    TECHNOLOGY(2),
    FOOD(3),
    WORD(4),
    COMPANY(5);

    public final Integer value;

    SupportedTopics(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return this.value;
    }
    public String getName(){
        return this.name();
    }

    @Override
    public String toString() {
        return "SupportedTopics{" +
                name() +
                "=" + value +
                '}';
    }
}
