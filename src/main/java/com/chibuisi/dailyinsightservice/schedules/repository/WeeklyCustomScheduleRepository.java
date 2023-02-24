package com.chibuisi.dailyinsightservice.schedules.repository;

import com.chibuisi.dailyinsightservice.schedules.model.WeeklyCustomSchedule;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleDay;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeeklyCustomScheduleRepository extends JpaRepository<WeeklyCustomSchedule, Long> {
    public List<WeeklyCustomSchedule> findAllWeeklyCustomSchedulesByUserIdAndTopic(Long userId, SupportedTopics topic);
    public List<WeeklyCustomSchedule> findAllWeeklyCustomSchedulesByUserIdAndTopicAndScheduleDayIn(
            Long userId, SupportedTopics topic, List<ScheduleDay> days);
    public List<WeeklyCustomSchedule> findWeeklyCustomSchedulesByUserId(Long userId);
    public List<WeeklyCustomSchedule> findWeeklySchedulesByUserIdAndTopic(Long userId, SupportedTopics topic);
    public List<WeeklyCustomSchedule> findWeeklyCustomSchedulesByTopic(SupportedTopics topic);
    public void deleteAllByUserIdAndTopic(Long userId, SupportedTopics topic);
}
