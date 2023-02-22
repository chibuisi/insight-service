package com.chibuisi.dailyinsightservice.schedules.repository;

import com.chibuisi.dailyinsightservice.schedules.model.DailyCustomSchedule;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyCustomScheduleRepository extends JpaRepository<DailyCustomSchedule, Long> {
    public DailyCustomSchedule findDailyCustomScheduleByUserIdAndTopic(Long userId, SupportedTopics topic);
    public List<DailyCustomSchedule> findDailyCustomSchedulesByUserId(Long userId);
}
