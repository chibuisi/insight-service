package com.chibuisi.dailyinsightservice.schedules.service;

import com.chibuisi.dailyinsightservice.schedules.model.*;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleDay;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleStatus;
import com.chibuisi.dailyinsightservice.schedules.model.enums.ScheduleType;
import com.chibuisi.dailyinsightservice.schedules.repository.DailyCustomScheduleRepository;
import com.chibuisi.dailyinsightservice.schedules.repository.DefaultScheduleRepository;
import com.chibuisi.dailyinsightservice.schedules.repository.MonthlyCustomScheduleRepository;
import com.chibuisi.dailyinsightservice.schedules.repository.WeeklyCustomScheduleRepository;
import com.chibuisi.dailyinsightservice.topic.model.SupportedTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private DefaultScheduleRepository defaultScheduleRepository;
    @Autowired
    private DailyCustomScheduleRepository dailyCustomScheduleRepository;
    @Autowired
    private WeeklyCustomScheduleRepository weeklyCustomScheduleRepository;
    @Autowired
    private MonthlyCustomScheduleRepository monthlyCustomScheduleRepository;

    public Schedule saveSchedule(ScheduleDTO scheduleDTO){
        SupportedTopics topic = SupportedTopics.of(scheduleDTO.getTopic());
        Schedule schedule = getScheduleInstance(scheduleDTO);
        if(schedule instanceof DailyCustomSchedule){
            DailyCustomSchedule existing = dailyCustomScheduleRepository
                    .findDailyCustomScheduleByUserIdAndTopic(scheduleDTO.getUserId(),
                            topic);
            if(existing != null)
                return null;
            DailyCustomSchedule dailyCustomSchedule = (DailyCustomSchedule) schedule;
            dailyCustomSchedule.setStatus(ScheduleStatus.ACTIVE);
            return dailyCustomScheduleRepository.save(dailyCustomSchedule);
        }
        else if(schedule instanceof WeeklyCustomSchedule){
            List<ScheduleDay> scheduleDays = ScheduleDay.of(scheduleDTO.getScheduleDays());
            List<WeeklyCustomSchedule> existing = weeklyCustomScheduleRepository
                    .findAllWeeklyCustomSchedulesByUserIdAndTopicAndScheduleDayIn(
                            scheduleDTO.getUserId(), topic, scheduleDays);
            List<ScheduleDay> existingScheduleDays = existing.stream()
                    .map(e -> ScheduleDay.of(e.getScheduleDay().getValue())).collect(Collectors.toList());
            scheduleDays.removeIf(scheduleDay -> existingScheduleDays.contains(scheduleDay));
            List<WeeklyCustomSchedule> weeklyCustomSchedules = new ArrayList<>();
            scheduleDays.forEach(scheduleDay -> {
                WeeklyCustomSchedule newInstance = ScheduleDTO.createWeeklyCustomSchedule(scheduleDTO);
                newInstance.setScheduleDay(scheduleDay);
                newInstance.setStatus(ScheduleStatus.ACTIVE);
                weeklyCustomSchedules.add(newInstance);
            });
            weeklyCustomScheduleRepository.saveAll(weeklyCustomSchedules);
            schedule.setStatus(ScheduleStatus.ACTIVE);
            return schedule;
        }
        else if(schedule instanceof MonthlyCustomSchedule){
//            MonthlyCustomSchedule existing = monthlyCustomScheduleRepository
//                    .findMonthlyCustomScheduleByUserIdAndTopic(scheduleDTO.getUserId(),
//                            topic);
//            if(existing != null)
//                return null;
            MonthlyCustomSchedule monthlyCustomSchedule = (MonthlyCustomSchedule) schedule;
            monthlyCustomSchedule.setStatus(ScheduleStatus.ACTIVE);
            return monthlyCustomScheduleRepository.save(monthlyCustomSchedule);
        }
        else {
//            DefaultSchedule existing = defaultScheduleRepository
//                    .findDefaultScheduleByUserIdAndTopic(scheduleDTO.getUserId(), topic);
//            if(existing != null)
//                return null;
            DefaultSchedule defaultSchedule = (DefaultSchedule) schedule;
            defaultSchedule.setStatus(ScheduleStatus.ACTIVE);
            return defaultScheduleRepository.save(defaultSchedule);
        }
    }
    public List<Schedule> getScheduleByUserIdAndTopic(Long userId, String topic){
        SupportedTopics foundTopic = SupportedTopics.of(topic);
        List<Schedule> schedule = null;
        schedule.add(defaultScheduleRepository
                .findDefaultScheduleByUserIdAndTopic(userId,foundTopic));
        if(schedule.size() != 0)
            return schedule;
        schedule.add(dailyCustomScheduleRepository
                .findDailyCustomScheduleByUserIdAndTopic(userId, foundTopic));
        if(schedule.size() != 0)
            return schedule;
        schedule.addAll(weeklyCustomScheduleRepository
                .findAllWeeklyCustomSchedulesByUserIdAndTopic(userId, foundTopic));
        if(schedule.size() != 0)
            return schedule;
        schedule.addAll(monthlyCustomScheduleRepository
                .findMonthlyCustomScheduleByUserIdAndTopic(userId, foundTopic));
        return schedule;
    }

    public List<? extends Schedule> pauseUnpauseSchedule(Long userId, String topic, String scheduleType){
        SupportedTopics foundTopic = SupportedTopics.of(topic);
        Schedule schedule = getScheduleInstanceFromUserIdAndTopic(userId, topic, scheduleType);
        if(schedule instanceof DailyCustomSchedule){
            DailyCustomSchedule existing = dailyCustomScheduleRepository
                    .findDailyCustomScheduleByUserIdAndTopic(userId,
                            foundTopic);
            if(existing == null)
                return null;
            if(existing.getStatus().equals(ScheduleStatus.ACTIVE))
                existing.setStatus(ScheduleStatus.INACTIVE);
            else
                existing.setStatus(ScheduleStatus.ACTIVE);
            return Collections.singletonList(dailyCustomScheduleRepository.save(existing));
        }
        else if(schedule instanceof WeeklyCustomSchedule){
            List<WeeklyCustomSchedule> existing = weeklyCustomScheduleRepository
                    .findAllWeeklyCustomSchedulesByUserIdAndTopic(userId,
                            foundTopic);
            if(existing == null)
                return null;
            existing.forEach(e -> {
                if(e.getStatus().equals(ScheduleStatus.ACTIVE))
                    e.setStatus(ScheduleStatus.INACTIVE);
                else
                    e.setStatus(ScheduleStatus.ACTIVE);
            });

            return weeklyCustomScheduleRepository.saveAll(existing);
        }
        else if(schedule instanceof MonthlyCustomSchedule){
            List<MonthlyCustomSchedule> existing = monthlyCustomScheduleRepository
                    .findMonthlyCustomScheduleByUserIdAndTopic(userId,
                            foundTopic);
            if(existing == null)
                return null;
            existing.forEach(e -> {
                if(e.getStatus().equals(ScheduleStatus.ACTIVE))
                    e.setStatus(ScheduleStatus.INACTIVE);
                else
                    e.setStatus(ScheduleStatus.ACTIVE);
            });
            return monthlyCustomScheduleRepository.saveAll(existing);
        }
        else {
            DefaultSchedule existing = defaultScheduleRepository
                    .findDefaultScheduleByUserIdAndTopic(userId, foundTopic);
            if(existing == null)
                return null;
            if(existing.getStatus().equals(ScheduleStatus.ACTIVE))
                existing.setStatus(ScheduleStatus.INACTIVE);
            else
                existing.setStatus(ScheduleStatus.ACTIVE);
            return Collections.singletonList(defaultScheduleRepository.save(existing));
        }
    }

    public void deleteSchedule(Long userId, String topic, String scheduleType){
        SupportedTopics foundTopic = SupportedTopics.of(topic);
        Schedule schedule = getScheduleInstanceFromUserIdAndTopic(userId, topic, scheduleType);
        if(schedule instanceof DailyCustomSchedule){
            DailyCustomSchedule existing = dailyCustomScheduleRepository
                    .findDailyCustomScheduleByUserIdAndTopic(userId,
                            foundTopic);
            if(existing == null)
                return;
            dailyCustomScheduleRepository.delete(existing);
        }
        else if(schedule instanceof WeeklyCustomSchedule){
            List<WeeklyCustomSchedule> existing = weeklyCustomScheduleRepository
                    .findAllWeeklyCustomSchedulesByUserIdAndTopic(userId,
                            foundTopic);
            if(existing == null)
                return;
            existing.forEach(e -> {
                weeklyCustomScheduleRepository.delete(e);
            });
        }
        else if(schedule instanceof MonthlyCustomSchedule){
            List<MonthlyCustomSchedule> existing = monthlyCustomScheduleRepository
                    .findMonthlyCustomScheduleByUserIdAndTopic(userId,
                            foundTopic);
            if(existing == null)
                return;
            existing.forEach(e -> {
                monthlyCustomScheduleRepository.delete(e);
            });
        }
        else {
            DefaultSchedule existing = defaultScheduleRepository
                    .findDefaultScheduleByUserIdAndTopic(userId, foundTopic);
            if(existing == null)
                return;
            defaultScheduleRepository.delete(existing);
        }
    }

    @Transactional
    public void deleteAllScheduleForUser(Long userId, String topic){
        SupportedTopics foundTopic = SupportedTopics.of(topic);
        defaultScheduleRepository.deleteAllByUserIdAndTopic(userId,foundTopic);
        dailyCustomScheduleRepository.deleteAllByUserIdAndTopic(userId,foundTopic);
        weeklyCustomScheduleRepository.deleteAllByUserIdAndTopic(userId,foundTopic);
        monthlyCustomScheduleRepository.deleteAllByUserIdAndTopic(userId,foundTopic);
    }

    public Map<String, List<? extends Schedule>> getUserAllSchedule(Long userId){
        Map<String, List<? extends Schedule>> userSchedules = new HashMap<>();
        userSchedules.put("default",  getUserDefaultSchedules(userId));
        userSchedules.put("daily",  getUserDailyCustomSchedules(userId));
        userSchedules.put("weekly",  getUserWeeklyCustomSchedules(userId));
        userSchedules.put("monthly",  getUserMonthlyCustomSchedules(userId));

        return userSchedules;
    }
    public List<DefaultSchedule> getUserDefaultSchedules(Long userId){
        return defaultScheduleRepository.findDefaultSchedulesByUserId(userId);
    }
    public List<DailyCustomSchedule> getUserDailyCustomSchedules(Long userId){
        return dailyCustomScheduleRepository.findDailyCustomSchedulesByUserId(userId);
    }
    public List<WeeklyCustomSchedule> getUserWeeklyCustomSchedules(Long userId){
        return weeklyCustomScheduleRepository.findWeeklyCustomSchedulesByUserId(userId);
    }
    public List<MonthlyCustomSchedule> getUserMonthlyCustomSchedules(Long userId){
        return monthlyCustomScheduleRepository.findMonthlyCustomSchedulesByUserId(userId);
    }
    public List<DefaultSchedule> getUserDefaultSchedulesByTopic(Long userId, SupportedTopics topic){
        return defaultScheduleRepository.findDefaultSchedulesByUserIdAndTopic(userId, topic);
    }
    public List<DailyCustomSchedule> getUserDailyCustomSchedulesByTopic(Long userId, SupportedTopics topic){
        return dailyCustomScheduleRepository.findDailySchedulesByUserIdAndTopic(userId, topic);
    }
    public List<WeeklyCustomSchedule> getUserWeeklyCustomSchedulesByTopic(Long userId, SupportedTopics topic){
        return weeklyCustomScheduleRepository.findWeeklySchedulesByUserIdAndTopic(userId, topic);
    }
    public List<MonthlyCustomSchedule> getUserMonthlyCustomSchedulesByTopic(Long userId, SupportedTopics topic){
        return monthlyCustomScheduleRepository.findMonthlySchedulesByUserIdAndTopic(userId, topic);
    }
    public Map<String, List<? extends Schedule>> getUserAllScheduleByTopic(Long userId, SupportedTopics topic){
        Map<String, List<? extends Schedule>> userSchedules = new HashMap<>();
        userSchedules.put("default", getUserDefaultSchedulesByTopic(userId, topic));
        userSchedules.put("daily", getUserDailyCustomSchedulesByTopic(userId, topic));
        userSchedules.put("weekly", getUserWeeklyCustomSchedulesByTopic(userId, topic));
        userSchedules.put("monthly", getUserMonthlyCustomSchedulesByTopic(userId, topic));
        return userSchedules;
    }
    public List<DefaultSchedule> getTopicDefaultSchedules(SupportedTopics topic){
        return defaultScheduleRepository.findDefaultCustomSchedulesByTopic(topic);
    }
    public List<DailyCustomSchedule> getTopicDailyCustomSchedules(SupportedTopics topic){
        return dailyCustomScheduleRepository.findDailyCustomSchedulesByTopic(topic);
    }
    public List<WeeklyCustomSchedule> getTopicWeeklyCustomSchedules(SupportedTopics topic){
        return weeklyCustomScheduleRepository.findWeeklyCustomSchedulesByTopic(topic);
    }
    public List<MonthlyCustomSchedule> getTopicMonthlyCustomSchedules(SupportedTopics topic){
        return monthlyCustomScheduleRepository.findMonthlyCustomSchedulesByTopic(topic);
    }
    public Map<String, List<? extends Schedule>> getTopicAllSchedule(SupportedTopics topic){
        Map<String, List<? extends Schedule>> userSchedules = new HashMap<>();
        userSchedules.put("default", getTopicDefaultSchedules(topic));
        userSchedules.put("daily", getTopicDailyCustomSchedules(topic));
        userSchedules.put("weekly", getTopicWeeklyCustomSchedules(topic));
        userSchedules.put("monthly", getTopicMonthlyCustomSchedules(topic));
        return userSchedules;
    }

    private Schedule getScheduleInstanceFromUserIdAndTopic(
            Long userId, String topic, String scheduleType){
        ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                .userId(userId).topic(topic).scheduleType(scheduleType).build();
        return getScheduleInstance(scheduleDTO);
    }

    private Schedule getScheduleInstance(ScheduleDTO scheduleDTO){
        String schedType = scheduleDTO.getScheduleType();
        if(schedType == null || schedType.length() == 0)
            scheduleDTO.setScheduleType(ScheduleType.DEFAULT.getValue());
        ScheduleType scheduleType = ScheduleType.of(scheduleDTO.getScheduleType());
        SupportedTopics topic = SupportedTopics.of(scheduleDTO.getTopic());
        switch (scheduleType){
            case DAILY:{
                DailyCustomSchedule dailyCustomSchedule =
                        ScheduleDTO.createDailyCustomSchedule(scheduleDTO);
                return dailyCustomSchedule;
            }
            case WEEKLY:{
                WeeklyCustomSchedule weeklyCustomSchedule =
                        ScheduleDTO.createWeeklyCustomSchedule(scheduleDTO);
                return weeklyCustomSchedule;
            }
            case MONTHLY:{
                MonthlyCustomSchedule monthlyCustomSchedule =
                        ScheduleDTO.createMonthlyCustomSchedule(scheduleDTO);
                return monthlyCustomSchedule;
            }
            default:{
                DefaultSchedule defaultSchedule =
                        ScheduleDTO.createDefaultSchedule(scheduleDTO);
                return defaultSchedule;
            }
        }
    }
}
