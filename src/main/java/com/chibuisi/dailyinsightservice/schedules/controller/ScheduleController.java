package com.chibuisi.dailyinsightservice.schedules.controller;

import com.chibuisi.dailyinsightservice.schedules.model.ScheduleDTO;
import com.chibuisi.dailyinsightservice.schedules.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
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

    @GetMapping("/pause")
    public ResponseEntity pauseUnpauseSchedule(@RequestParam Long userId,
                                         @RequestParam String topic,
                                         @RequestParam String scheduleType){

        return new ResponseEntity(scheduleService.pauseUnpauseSchedule(userId, topic, scheduleType),
                HttpStatus.OK);
    }

    //get schedule by userId and topic
    @GetMapping
    public ResponseEntity getScheduleByUserIdAndTopic(@RequestParam Long userId, @RequestParam String topic){
        return new ResponseEntity(scheduleService.getScheduleByUserIdAndTopic(userId, topic), HttpStatus.OK);
    }

    //get user schedule - (all, default, daily, weekly, monthly) types
    @GetMapping("/user")
    public ResponseEntity getAllUserSchedule(@RequestParam Long userId,
                                             @RequestParam String scheduleType){
        if(scheduleType.equalsIgnoreCase("all"))
            return new ResponseEntity(scheduleService.getAllUserSchedule(userId), HttpStatus.OK);
        else if(scheduleType.equalsIgnoreCase("default"))
            return new ResponseEntity(scheduleService.getUserDefaultSchedules(userId), HttpStatus.OK);
        else if(scheduleType.equalsIgnoreCase("daily"))
            return new ResponseEntity(scheduleService.getUserDailyCustomSchedules(userId), HttpStatus.OK);
        else if(scheduleType.equalsIgnoreCase("weekly"))
            return new ResponseEntity(scheduleService.getUserWeeklyCustomSchedules(userId), HttpStatus.OK);
        else if(scheduleType.equalsIgnoreCase("monthly"))
            return new ResponseEntity(scheduleService.getUserMonthlyCustomSchedules(userId), HttpStatus.OK);
        else
            return new ResponseEntity("Invalid Schedule Type", HttpStatus.OK);
    }

}
