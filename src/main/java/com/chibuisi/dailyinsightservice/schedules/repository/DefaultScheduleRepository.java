package com.chibuisi.dailyinsightservice.schedules.repository;

import com.chibuisi.dailyinsightservice.schedules.model.DefaultSchedule;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleStatus;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefaultScheduleRepository extends JpaRepository<DefaultSchedule, Long> {
    public DefaultSchedule findDefaultScheduleByUserIdAndTopic(Long userId, SupportedTopics topic);
    public List<DefaultSchedule> findDefaultSchedulesByUserId(Long userId);
    public List<DefaultSchedule> findDefaultSchedulesByUserIdAndTopic(Long userId, SupportedTopics topic);
    public List<DefaultSchedule> findDefaultCustomSchedulesByTopic(SupportedTopics topic);
    public void deleteAllByUserIdAndTopic(Long userId, SupportedTopics topic);
    public List<DefaultSchedule> findDefaultSchedulesByStatus(ScheduleStatus scheduleStatus);
}
