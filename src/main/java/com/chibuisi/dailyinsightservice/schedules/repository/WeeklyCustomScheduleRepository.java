package com.chibuisi.dailyinsightservice.schedules.repository;

import com.chibuisi.dailyinsightservice.schedules.model.DailyCustomSchedule;
import com.chibuisi.dailyinsightservice.schedules.model.MonthlyCustomSchedule;
import com.chibuisi.dailyinsightservice.schedules.model.WeeklyCustomSchedule;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeeklyCustomScheduleRepository extends JpaRepository<WeeklyCustomSchedule, Long> {
    public WeeklyCustomSchedule findWeeklyCustomScheduleByUserIdAndTopic(Long userId, SupportedTopics topic);
    public List<WeeklyCustomSchedule> findWeeklyCustomSchedulesByUserId(Long userId);
    public List<WeeklyCustomSchedule> findWeeklySchedulesByUserIdAndTopic(Long userId, SupportedTopics topic);
    public List<WeeklyCustomSchedule> findWeeklyCustomSchedulesByTopic(SupportedTopics topic);
}
