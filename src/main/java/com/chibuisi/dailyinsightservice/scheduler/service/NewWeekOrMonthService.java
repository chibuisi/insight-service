package com.chibuisi.dailyinsightservice.scheduler.service;

import com.chibuisi.dailyinsightservice.scheduler.model.NewWeekOrMonth;
import com.chibuisi.dailyinsightservice.scheduler.model.enums.WeekOrMonth;
import com.chibuisi.dailyinsightservice.scheduler.repository.NewWeekOrMonthRepository;
import com.chibuisi.dailyinsightservice.schedules.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional
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
        return newWeekOrMonthRepository.getNewWeekOrMonthByName(weekOrMonth.getCode());
    }

    public int checkForNewWeek(){
        LocalDateTime localDateTime = LocalDateTime.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        Integer thisWeekNum = localDateTime.get(weekFields.weekOfYear());
        Optional<NewWeekOrMonth> optionalNewWeekOrMonth = newWeekOrMonthRepository.findNewWeekOrMonthByName(WeekOrMonth.WEEK.getCode());
        NewWeekOrMonth newWeekOrMonth = null;
        int resultCount = 0;
        if(optionalNewWeekOrMonth.isPresent()) {
            newWeekOrMonth = optionalNewWeekOrMonth.get();
            //if week num > or < than current then update current to week num
            if(thisWeekNum > newWeekOrMonth.getCurrent() || thisWeekNum < newWeekOrMonth.getCurrent()){
                newWeekOrMonth.setCurrent(thisWeekNum);
                newWeekOrMonth.setLast(thisWeekNum-1);
                //saveNewWeekOrMonth(newWeekOrMonth);
                newWeekOrMonthRepository.updateFrequencyCounter(newWeekOrMonth.getName(), thisWeekNum);
                resultCount = updateCounterFrequencyForWeeklySchedules(thisWeekNum);
            }
        }
        else {
            newWeekOrMonth = NewWeekOrMonth.builder().current(thisWeekNum).last(thisWeekNum-1).name("W").build();
            saveNewWeekOrMonth(newWeekOrMonth);
        }
        return resultCount;
    }

    private int updateCounterFrequencyForWeeklySchedules(Integer freqCounterValue){
        return scheduleService.updateWeeklyCustomScheduleTable(freqCounterValue);
    }

    public int checkForNewMonth(){
        LocalDateTime localDateTime = LocalDateTime.now();
        Integer month = localDateTime.getMonthValue();
        Optional<NewWeekOrMonth> optionalNewWeekOrMonth = newWeekOrMonthRepository.findNewWeekOrMonthByName(WeekOrMonth.MONTH.getCode());
        NewWeekOrMonth newWeekOrMonth = null;
        int resultCount = 0;
        if(optionalNewWeekOrMonth.isPresent()) {
            newWeekOrMonth = optionalNewWeekOrMonth.get();
            if(month > newWeekOrMonth.getCurrent() || month < newWeekOrMonth.getCurrent()){
                newWeekOrMonth.setCurrent(month);
                saveNewWeekOrMonth(newWeekOrMonth);
                resultCount = updateCounterFrequencyForMonthlySchedules(month);
            }
        }
        else {
            newWeekOrMonth = NewWeekOrMonth.builder().current(month).last(month-1).name("M").build();
            saveNewWeekOrMonth(newWeekOrMonth);
        }
        return resultCount;
    }

    private int updateCounterFrequencyForMonthlySchedules(Integer freqCounterValue){
        return scheduleService.updateMonthlyCustomScheduleTable(freqCounterValue);
    }
}
