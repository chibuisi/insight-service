package com.chibuisi.dailyinsightservice.schedules.repository;

import com.chibuisi.dailyinsightservice.schedules.model.DailyCustomSchedule;
import com.chibuisi.dailyinsightservice.schedules.model.DefaultSchedule;
import com.chibuisi.dailyinsightservice.schedules.model.WeeklyCustomSchedule;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyCustomScheduleRepository extends JpaRepository<DailyCustomSchedule, Long> {
    public DailyCustomSchedule findDailyCustomScheduleByUserIdAndTopic(Long userId, SupportedTopics topic);
    public List<DailyCustomSchedule> findDailyCustomSchedulesByUserId(Long userId);
    public List<DailyCustomSchedule> findDailySchedulesByUserIdAndTopic(Long userId, SupportedTopics topic);
    public List<DailyCustomSchedule> findDailyCustomSchedulesByTopic(SupportedTopics topic);
    public void deleteAllByUserIdAndTopic(Long userId, SupportedTopics topic);
}
