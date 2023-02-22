package com.chibuisi.dailyinsightservice.schedules.controller;

import com.chibuisi.dailyinsightservice.schedules.model.ScheduleDTO;
import com.chibuisi.dailyinsightservice.schedules.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity saveSchedule(@RequestBody ScheduleDTO scheduleDTO){
        return new ResponseEntity(scheduleService.saveSchedule(scheduleDTO), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity removeSchedule(@RequestParam Long userId,
                                         @RequestParam String topic,
                                         @RequestParam String scheduleType){
        scheduleService.deleteSchedule(userId, topic, scheduleType);
        return new ResponseEntity("Removed", HttpStatus.OK);
    }
}
