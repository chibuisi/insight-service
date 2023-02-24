package com.chibuisi.dailyinsightservice.schedules.repository;

import com.chibuisi.dailyinsightservice.schedules.model.ReadySchedule;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ReadyScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadyScheduleRepository extends JpaRepository<ReadySchedule, Long> {
    public List<ReadySchedule> getReadySchedulesByStatus(ReadyScheduleStatus status);
}
