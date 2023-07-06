package com.chibuisi.dailyinsightservice.schedules.repository;

import com.chibuisi.dailyinsightservice.schedules.model.DailyCustomSchedule;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleStatus;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyCustomScheduleRepository extends JpaRepository<DailyCustomSchedule, Long> {
    public DailyCustomSchedule findDailyCustomScheduleByUserIdAndTopic(Long userId, SupportedTopic topic);
    public List<DailyCustomSchedule> findDailyCustomSchedulesByUserId(Long userId);
    public List<DailyCustomSchedule> findDailySchedulesByUserIdAndTopic(Long userId, SupportedTopic topic);
    public List<DailyCustomSchedule> findDailyCustomSchedulesByTopic(SupportedTopic topic);
    public void deleteAllByUserIdAndTopic(Long userId, SupportedTopic topic);
    public List<DailyCustomSchedule> findDailyCustomSchedulesByStatusAndTimeAndFrequencyCounterEquals(
            ScheduleStatus scheduleStatus, Integer time, Integer freq);
}
