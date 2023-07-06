package com.chibuisi.dailyinsightservice.topic.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SupportedTopic {
    MOTIVATION(1),
    TECHNOLOGY(2),
    FOOD(3),
    WORD(4),
    COMPANY(5);

    public final Integer value;

    SupportedTopic(Integer value){
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

    public static SupportedTopic of(String name){
        return Stream.of(SupportedTopic.values())
                .filter(s -> s.getName().equalsIgnoreCase(name)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<SupportedTopic> fromStringList(List<String> topicNames) {
        topicNames = topicNames.stream().map(String::toLowerCase).collect(Collectors.toList());
//        List<SupportedTopics> result = new ArrayList<>();
//        topicNames.forEach(e -> {
//            SupportedTopics supportedTopic = Stream.of(SupportedTopics.values())
//                    .filter(s -> s.getName().equalsIgnoreCase(e)).findFirst().orElse(null);
//            if(e != null)
//                result.add(supportedTopic);
//        });

        List<String> finalTopicNames = topicNames;
        return Arrays.stream(SupportedTopic.values())
                .filter(e -> finalTopicNames.contains(e.getName().toLowerCase()))
                .collect(Collectors.toList());
    }
}
