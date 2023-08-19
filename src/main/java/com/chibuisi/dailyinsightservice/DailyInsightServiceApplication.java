package com.chibuisi.dailyinsightservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableRetry
public class DailyInsightServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyInsightServiceApplication.class, args);
    }

}
