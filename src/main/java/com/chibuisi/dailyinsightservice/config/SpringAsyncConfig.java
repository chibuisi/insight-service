package com.chibuisi.dailyinsightservice.config;

import com.chibuisi.dailyinsightservice.exception.CustomAsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class SpringAsyncConfig implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(10);
        threadPoolTaskExecutor.setThreadNamePrefix("my-task-exec-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler(){
        return new CustomAsyncExceptionHandler();
    }
    @Bean(name = "publishToPubSubThreadPoolTaskExecutor")
    public Executor publishToPubSubThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(1000);
        threadPoolTaskExecutor.setMaxPoolSize(2000000);
        threadPoolTaskExecutor.setQueueCapacity(2000000);

        threadPoolTaskExecutor.setThreadNamePrefix("pubsub-publish-task-exec-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
    @Bean(name = "saveReadyScheduleThreadPoolTaskExecutor")
    public Executor saveReadyScheduleThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(1000);
        threadPoolTaskExecutor.setMaxPoolSize(2000000);
        threadPoolTaskExecutor.setQueueCapacity(2000000);
        threadPoolTaskExecutor.setThreadNamePrefix("save-schedule-task-exec-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
    @Bean(name = "sendMailThreadPoolTaskExecutor")
    public Executor sendMailThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(500);
        threadPoolTaskExecutor.setMaxPoolSize(2000000);
        threadPoolTaskExecutor.setQueueCapacity(2000000);
        threadPoolTaskExecutor.setThreadNamePrefix("send-mail-task-exec-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
