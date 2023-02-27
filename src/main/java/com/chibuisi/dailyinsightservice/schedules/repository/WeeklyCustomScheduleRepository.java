package com.chibuisi.dailyinsightservice.schedules.repository;

import com.chibuisi.dailyinsightservice.schedules.model.WeeklyCustomSchedule;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleDay;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleStatus;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeeklyCustomScheduleRepository extends JpaRepository<WeeklyCustomSchedule, Long> {
    public List<WeeklyCustomSchedule> findAllWeeklyCustomSchedulesByUserIdAndTopic(Long userId, SupportedTopics topic);
    public WeeklyCustomSchedule findWeeklyCustomScheduleByUserIdAndTopicAndScheduleDay(
            Long userId, SupportedTopics topic, ScheduleDay day);
    public List<WeeklyCustomSchedule> findWeeklyCustomSchedulesByUserId(Long userId);
    public List<WeeklyCustomSchedule> findWeeklySchedulesByUserIdAndTopic(Long userId, SupportedTopics topic);
    public List<WeeklyCustomSchedule> findWeeklyCustomSchedulesByTopic(SupportedTopics topic);
    public void deleteAllByUserIdAndTopic(Long userId, SupportedTopics topic);
    public List<WeeklyCustomSchedule>
    findWeeklyCustomSchedulesByStatusAndTimeAndFrequencyCounterEquals(
            ScheduleStatus scheduleStatus, Integer time, Integer freq);
    @Modifying(clearAutomatically = true)
    @Query("UPDATE WeeklyCustomSchedule wcs SET wcs.frequencyCounter= :newValue WHERE wcs.frequencyCounter= :frequencyCounter")
    Integer updateFrequencyCounter(@Param("frequencyCounter") Integer frequencyCounter,
                                   @Param("newValue") Integer newValue);
}
