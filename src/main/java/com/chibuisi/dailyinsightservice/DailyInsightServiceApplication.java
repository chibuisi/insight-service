package com.chibuisi.dailyinsightservice;

import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DailyInsightServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyInsightServiceApplication.class, args);
    }

}
