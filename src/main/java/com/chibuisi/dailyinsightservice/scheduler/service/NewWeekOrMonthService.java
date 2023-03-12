package com.chibuisi.dailyinsightservice.scheduler.service;

import com.chibuisi.dailyinsightservice.scheduler.model.NewWeekOrMonth;
import com.chibuisi.dailyinsightservice.scheduler.model.enums.WeekOrMonth;
import com.chibuisi.dailyinsightservice.scheduler.repository.NewWeekOrMonthRepository;
import com.chibuisi.dailyinsightservice.schedules.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
public class NewWeekOrMonthService {
    @Autowired
    private NewWeekOrMonthRepository newWeekOrMonthRepository;
    @Autowired
    private ScheduleService scheduleService;

    public void saveNewWeekOrMonth(List<NewWeekOrMonth> newWeekOrMonthList){
        newWeekOrMonthRepository.saveAll(newWeekOrMonthList);
    }

    public NewWeekOrMonth saveNewWeekOrMonth(NewWeekOrMonth newWeekOrMonth){
        return newWeekOrMonthRepository.save(newWeekOrMonth);
    }

    public NewWeekOrMonth getNewWeekOrMonth(WeekOrMonth weekOrMonth){
        return newWeekOrMonthRepository.getNewWeekOrMonthByName(weekOrMonth);
    }

    public int checkForNewWeek(){
        LocalDateTime localDateTime = LocalDateTime.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        Integer thisWeekNum = localDateTime.get(weekFields.weekOfYear());
        NewWeekOrMonth newWeekOrMonth = newWeekOrMonthRepository.getNewWeekOrMonthByName(WeekOrMonth.WEEK);
        int resultCount = 0;
        //if week num > or < than current then update current to week num
        if(thisWeekNum > newWeekOrMonth.getCurrent() || thisWeekNum < newWeekOrMonth.getCurrent()){
            newWeekOrMonth.setCurrent(thisWeekNum);
            updateNewWeekOrMonth(newWeekOrMonth);
            resultCount = updateCounterFrequencyForWeeklySchedules(thisWeekNum);
        }
        return resultCount;
    }

    private void updateNewWeekOrMonth(NewWeekOrMonth newWeekOrMonth){
        newWeekOrMonthRepository.save(newWeekOrMonth);
    }

    private int updateCounterFrequencyForWeeklySchedules(Integer freqCounterValue){
        return scheduleService.updateWeeklyCustomScheduleTable(freqCounterValue);
    }

    public int checkForNewMonth(){
        LocalDateTime localDateTime = LocalDateTime.now();
        Integer month = localDateTime.getMonthValue();
        NewWeekOrMonth newWeekOrMonth = newWeekOrMonthRepository.getNewWeekOrMonthByName(WeekOrMonth.MONTH);
        int resultCount = 0;
        if(month > newWeekOrMonth.getCurrent() || month < newWeekOrMonth.getCurrent()){
            newWeekOrMonth.setCurrent(month);
            updateNewWeekOrMonth(newWeekOrMonth);
            resultCount = updateCounterFrequencyForMonthlySchedules(month);
        }
        return resultCount;
    }

    private int updateCounterFrequencyForMonthlySchedules(Integer freqCounterValue){
        return scheduleService.updateMonthlyCustomScheduleTable(freqCounterValue);
    }
}
