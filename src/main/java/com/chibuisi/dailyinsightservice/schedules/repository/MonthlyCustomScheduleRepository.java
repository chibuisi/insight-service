package com.chibuisi.dailyinsightservice.schedules.repository;

import com.chibuisi.dailyinsightservice.schedules.model.MonthlyCustomSchedule;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyCustomScheduleRepository extends JpaRepository<MonthlyCustomSchedule, Long> {
    public MonthlyCustomSchedule findMonthlyCustomScheduleByUserIdAndTopic(Long userId, SupportedTopics topic);
}
