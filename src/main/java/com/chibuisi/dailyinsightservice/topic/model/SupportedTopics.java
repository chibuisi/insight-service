package com.chibuisi.dailyinsightservice.topic.model;

import java.util.stream.Stream;

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
        return this.getName();
    }

    public static SupportedTopics of(String name){
        return Stream.of(SupportedTopics.values())
                .filter(s -> s.getName().equalsIgnoreCase(name)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
