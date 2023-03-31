package com.chibuisi.dailyinsightservice.scheduler.repository;

import com.chibuisi.dailyinsightservice.scheduler.model.NewWeekOrMonth;
import com.chibuisi.dailyinsightservice.scheduler.model.enums.WeekOrMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewWeekOrMonthRepository extends JpaRepository<NewWeekOrMonth, Long> {
    public NewWeekOrMonth getNewWeekOrMonthByName(String code);
    public Optional<NewWeekOrMonth> findNewWeekOrMonthByName(String name);
    @Modifying(clearAutomatically = true)
    @Query("UPDATE NewWeekOrMonth nwm SET nwm.current= :newValue, nwm.last= :newValue-1 WHERE nwm.name= :name")
    Integer updateFrequencyCounter(@Param("name") String name,
                                   @Param("newValue") Integer newValue);
}
