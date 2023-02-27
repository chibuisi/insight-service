package com.chibuisi.dailyinsightservice.scheduler.repository;

import com.chibuisi.dailyinsightservice.scheduler.model.NewWeekOrMonth;
import com.chibuisi.dailyinsightservice.scheduler.model.enums.WeekOrMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewWeekOrMonthRepository extends JpaRepository<NewWeekOrMonth, Long> {
    public NewWeekOrMonth getNewWeekOrMonthByName(WeekOrMonth name);
}
