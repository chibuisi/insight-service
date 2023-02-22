package com.chibuisi.dailyinsightservice.schedules.repository;

import com.chibuisi.dailyinsightservice.schedules.model.WeeklyCustomSchedule;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyCustomScheduleRepository extends JpaRepository<WeeklyCustomSchedule, Long> {
    public WeeklyCustomSchedule findWeeklyCustomScheduleByUserIdAndTopic(Long userId, SupportedTopics topic);
}
