package com.chibuisi.dailyinsightservice.schedules.service;

import com.chibuisi.dailyinsightservice.schedules.model.ReadySchedule;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ReadyScheduleStatus;
import com.chibuisi.dailyinsightservice.schedules.repository.ReadyScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReadyScheduleService {
    @Autowired
    private ReadyScheduleRepository readyScheduleRepository;

    public Optional<ReadySchedule> getReadyScheduleById(Long id){
        return readyScheduleRepository.findById(id);
    }

    public List<ReadySchedule> getReadySchedules(){
        return readyScheduleRepository.getReadySchedulesByStatus(ReadyScheduleStatus.UNSENT);
    }

    public List<ReadySchedule> getReadySchedulesBySentStatus(){
        return readyScheduleRepository.getReadySchedulesByStatus(ReadyScheduleStatus.SENT);
    }

    public ReadySchedule saveReadySchedule(ReadySchedule readySchedule){
        return readyScheduleRepository.save(readySchedule);
    }

    public void saveReadySchedules(List<ReadySchedule> readySchedules){
        readyScheduleRepository.saveAll(readySchedules);
    }

    public List<ReadySchedule> getReadySchedulesById(List<Long> ids){
        return readyScheduleRepository.findAllById(ids);
    }

    public void cleanUpAllTableData(){
        readyScheduleRepository.deleteAll();
    }

}
