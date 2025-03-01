package com.chibuisi.dailyinsightservice.schedules.repository;

import com.chibuisi.dailyinsightservice.schedules.model.MonthlyCustomSchedule;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleStatus;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyCustomScheduleRepository extends JpaRepository<MonthlyCustomSchedule, Long> {
    public List<MonthlyCustomSchedule> findMonthlyCustomScheduleByUserIdAndTopic(Long userId, SupportedTopic topic);
    public MonthlyCustomSchedule
    findMonthlyCustomScheduleByUserIdAndTopicAndDay(Long userId, SupportedTopic topic, Integer day);
    public List<MonthlyCustomSchedule> findMonthlyCustomSchedulesByUserId(Long userId);
    public List<MonthlyCustomSchedule> findMonthlySchedulesByUserIdAndTopic(Long userId, SupportedTopic topic);
    public List<MonthlyCustomSchedule> findMonthlyCustomSchedulesByTopic(SupportedTopic topic);
    public void deleteAllByUserIdAndTopic(Long userId, SupportedTopic topic);
    public List<MonthlyCustomSchedule> findMonthlyCustomSchedulesByStatusAndTimeAndDayAndFrequencyCounterEquals(
            ScheduleStatus scheduleStatus, Integer time, Integer day, Integer freq);
    @Modifying(clearAutomatically = true)
    @Query("UPDATE MonthlyCustomSchedule mcs SET mcs.frequencyCounter= :newValue WHERE mcs.frequencyCounter= :frequencyCounter")
    Integer updateFrequencyCounter(@Param("frequencyCounter") Integer frequencyCounter,
                                   @Param("newValue") Integer newValue);
}
