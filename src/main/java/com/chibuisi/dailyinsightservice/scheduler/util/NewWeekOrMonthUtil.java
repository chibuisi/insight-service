package com.chibuisi.dailyinsightservice.scheduler.util;

import com.chibuisi.dailyinsightservice.scheduler.model.NewWeekOrMonth;
import com.chibuisi.dailyinsightservice.scheduler.model.enums.WeekOrMonth;
import com.chibuisi.dailyinsightservice.scheduler.service.NewWeekOrMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Component
public class NewWeekOrMonthUtil {
    @Autowired
    private NewWeekOrMonthService newWeekOrMonthService;

    private void updateNewWeekTable(){
        NewWeekOrMonth week = newWeekOrMonthService.getNewWeekOrMonth(WeekOrMonth.WEEK);
        LocalDateTime localDateTime = LocalDateTime.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        Integer thisWeekNum = localDateTime.get(weekFields.weekOfYear());
        if(week == null || week.getCurrent() <= 0){
            week = NewWeekOrMonth.builder()
                    .name(WeekOrMonth.WEEK).current(thisWeekNum).last(thisWeekNum-1).build();
            newWeekOrMonthService.saveNewWeekOrMonth(week);
        }

    }
    private void updateNewMonthTable(){
        NewWeekOrMonth month = newWeekOrMonthService.getNewWeekOrMonth(WeekOrMonth.MONTH);
        LocalDateTime localDateTime = LocalDateTime.now();
        Integer monthNum = localDateTime.getMonthValue();
        if(month == null || month.getCurrent() <= 0){
            month = NewWeekOrMonth.builder()
                    .name(WeekOrMonth.MONTH).current(monthNum).last(monthNum-1).build();
            newWeekOrMonthService.saveNewWeekOrMonth(month);
        }
    }

    public void updateNewWeekOrMonthTable(){
            updateNewWeekTable();
            updateNewMonthTable();
    }
}
