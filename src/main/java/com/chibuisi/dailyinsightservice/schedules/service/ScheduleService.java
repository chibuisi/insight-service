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
import com.chibuisi.dailyinsightservice.topic.model.UserTopicItemOffset;
import com.chibuisi.dailyinsightservice.user.model.User;
import com.chibuisi.dailyinsightservice.user.service.UserService;
import com.chibuisi.dailyinsightservice.usersubscription.model.Subscription;
import com.chibuisi.dailyinsightservice.usersubscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
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
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private UserService userService;
    private String timezone = "MST";

    @Transactional
    public Schedule saveSchedule(ScheduleDTO scheduleDTO){
        User user = userService.getUserById(scheduleDTO.getUserId());
        if(user == null)
            return DefaultSchedule.builder().build();
        SupportedTopics topic = SupportedTopics.of(scheduleDTO.getTopic());
        Subscription subscription =
                subscriptionService.getSubscriptionInfo(user.getEmail(), topic.getName());
        if(subscription == null)
            return DefaultSchedule.builder().build();
        scheduleDTO.setTime(getTimeInMST(scheduleDTO.getTime(), scheduleDTO.getTimezone()));
        Schedule schedule = getScheduleInstance(scheduleDTO);
        if(schedule instanceof DailyCustomSchedule){
            synchronizeAllScheduleTable(scheduleDTO);
            DailyCustomSchedule existing = dailyCustomScheduleRepository
                    .findDailyCustomScheduleByUserIdAndTopic(scheduleDTO.getUserId(),
                            topic);
            if(existing != null){
                existing.setTime(scheduleDTO.getTime());
                existing.setFrequency(scheduleDTO.getFrequency());
                existing.setFrequencyCounter(scheduleDTO.getFrequency());
                existing = dailyCustomScheduleRepository.save(existing);
                createUserTopicItemOffset(user, topic);
                return existing;
            }
            DailyCustomSchedule dailyCustomSchedule = (DailyCustomSchedule) schedule;
            dailyCustomSchedule.setStatus(ScheduleStatus.ACTIVE);
            dailyCustomSchedule = dailyCustomScheduleRepository.save(dailyCustomSchedule);
            createUserTopicItemOffset(user, topic);
            return dailyCustomSchedule;
        }
        else if(schedule instanceof WeeklyCustomSchedule){
            synchronizeAllScheduleTable(scheduleDTO);
            ScheduleDay scheduleDay = ScheduleDay.of(scheduleDTO.getScheduleDay());
            WeeklyCustomSchedule existing = weeklyCustomScheduleRepository
                    .findWeeklyCustomScheduleByUserIdAndTopicAndScheduleDay(
                            scheduleDTO.getUserId(), topic, scheduleDay);
            if(existing != null) {
                existing.setTime(scheduleDTO.getTime());
                existing.setFrequency(scheduleDTO.getFrequency());
                existing.setFrequencyCounter(scheduleDTO.getFrequency());
                existing.setScheduleDay(scheduleDay);
                existing = weeklyCustomScheduleRepository.save(existing);
                createUserTopicItemOffset(user, topic);
                return existing;
            }
            WeeklyCustomSchedule newInstance = (WeeklyCustomSchedule) schedule;
            newInstance.setScheduleDay(scheduleDay);
            newInstance.setStatus(ScheduleStatus.ACTIVE);
            newInstance = weeklyCustomScheduleRepository.save(newInstance);
            createUserTopicItemOffset(user, topic);
            return newInstance;
        }
        else if(schedule instanceof MonthlyCustomSchedule){
            synchronizeAllScheduleTable(scheduleDTO);
            Integer day = ScheduleDTO.getValidMonthDay(scheduleDTO);
            MonthlyCustomSchedule existing = monthlyCustomScheduleRepository
                    .findMonthlyCustomScheduleByUserIdAndTopicAndDay(scheduleDTO.getUserId(),
                            topic, day);
            if(existing != null) {
                existing.setTime(scheduleDTO.getTime());
                existing.setFrequency(scheduleDTO.getFrequency());
                existing.setFrequencyCounter(scheduleDTO.getFrequency());
                existing.setDay(day);
                existing = monthlyCustomScheduleRepository.save(existing);
            }
            MonthlyCustomSchedule monthlyCustomSchedule = (MonthlyCustomSchedule) schedule;
            monthlyCustomSchedule.setDay(day);
            monthlyCustomSchedule.setStatus(ScheduleStatus.ACTIVE);
            return monthlyCustomScheduleRepository.save(monthlyCustomSchedule);
        }
        else {
            DefaultSchedule existing = defaultScheduleRepository
                    .findDefaultScheduleByUserIdAndTopic(scheduleDTO.getUserId(), topic);
            if(existing == null) {
                DefaultSchedule defaultSchedule = (DefaultSchedule) schedule;
                defaultSchedule.setStatus(ScheduleStatus.ACTIVE);
                existing = defaultScheduleRepository.save(defaultSchedule);
                createUserTopicItemOffset(user, topic);
                return existing;
            }
            return existing;
        }
    }
    public void saveDailySchedules(List<DailyCustomSchedule> dailyCustomSchedules){
        dailyCustomScheduleRepository.saveAll(dailyCustomSchedules);
    }
    public void saveDefaultSchedules(List<DefaultSchedule> defaultSchedules){
        defaultScheduleRepository.saveAll(defaultSchedules);
    }
    public void saveWeeklySchedules(List<WeeklyCustomSchedule> weeklyCustomSchedules){
        weeklyCustomScheduleRepository.saveAll(weeklyCustomSchedules);
    }
    public void saveMonthlySchedules(List<MonthlyCustomSchedule> monthlyCustomSchedules){
        monthlyCustomScheduleRepository.saveAll(monthlyCustomSchedules);
    }
    public List<Schedule> getScheduleByUserIdAndTopic(Long userId, String topic){
        SupportedTopics foundTopic = SupportedTopics.of(topic);
        List<Schedule> schedules = new ArrayList<>();
        DefaultSchedule defaultSchedule = defaultScheduleRepository
                .findDefaultScheduleByUserIdAndTopic(userId,foundTopic);
        if(defaultSchedule != null)
            schedules.add(defaultSchedule);
        if(schedules.size() != 0)
            return schedules;
        DailyCustomSchedule dailyCustomSchedule = dailyCustomScheduleRepository
                .findDailyCustomScheduleByUserIdAndTopic(userId, foundTopic);
        if(dailyCustomSchedule != null)
            schedules.add(dailyCustomSchedule);
        if(schedules.size() != 0)
            return schedules;
        List<WeeklyCustomSchedule> weeklyCustomSchedules = weeklyCustomScheduleRepository
                .findAllWeeklyCustomSchedulesByUserIdAndTopic(userId, foundTopic);
        if(weeklyCustomSchedules != null && weeklyCustomSchedules.size() > 0)
            schedules.addAll(weeklyCustomSchedules);
        if(schedules.size() != 0)
            return schedules;
        List<MonthlyCustomSchedule> monthlyCustomSchedules = monthlyCustomScheduleRepository
                .findMonthlyCustomScheduleByUserIdAndTopic(userId, foundTopic);
        if(monthlyCustomSchedules != null && monthlyCustomSchedules.size() > 0)
            schedules.addAll(monthlyCustomSchedules);
        return schedules;
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

    private Integer getTimeInMST(Integer time, String timeZone){
        ZoneId validZoneId = null;
        if(timeZone == null)
            throw new IllegalArgumentException("Timezone field has invalid value");
        if(timeZone.length() == 3)
            validZoneId = ZoneId.of(timeZone.toUpperCase(), ZoneId.SHORT_IDS);
        else validZoneId = ZoneId.of(timeZone);
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate.getYear(), localDate.getMonth(),
                localDate.getDayOfMonth(), time, 0,0);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime,validZoneId);
        ZonedDateTime mstTime = zonedDateTime.withZoneSameInstant(ZoneId.of("America/Phoenix"));
        return mstTime.getHour();
    }

    @Transactional
    public void synchronizeAllScheduleTable(ScheduleDTO scheduleDTO){
        ScheduleType scheduleType = ScheduleType.of(scheduleDTO.getScheduleType());
        Long userId = scheduleDTO.getUserId();
        SupportedTopics topic = SupportedTopics.of(scheduleDTO.getTopic());
        defaultScheduleRepository.deleteAllByUserIdAndTopic(userId, topic);
        List<ScheduleType> scheduleTypes = new ArrayList<>(
                Arrays.asList(ScheduleType.DAILY,ScheduleType.MONTHLY, ScheduleType.WEEKLY));
        scheduleTypes.removeIf(e -> e.equals(scheduleType));
        scheduleTypes.forEach(e -> {
            if(e.equals(ScheduleType.DAILY)){
                dailyCustomScheduleRepository.deleteAllByUserIdAndTopic(userId, topic);
            }
            else if(e.equals(ScheduleType.WEEKLY)){
                weeklyCustomScheduleRepository.deleteAllByUserIdAndTopic(userId, topic);
            }
            else if(e.equals(ScheduleType.MONTHLY)){
                monthlyCustomScheduleRepository.deleteAllByUserIdAndTopic(userId, topic);
            }
        });
    }

    public List<DailyCustomSchedule> getActiveDailyCustomSchedules(){
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timezone, ZoneId.SHORT_IDS));
        Integer time = zonedDateTime.getHour();
        return dailyCustomScheduleRepository
                .findDailyCustomSchedulesByStatusAndTimeAndFrequencyCounterEquals(
                        ScheduleStatus.ACTIVE, time, 1);
    }
    public List<WeeklyCustomSchedule> getActiveWeeklyCustomSchedules(){
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timezone, ZoneId.SHORT_IDS));
        Integer time = zonedDateTime.getHour();
        String day = zonedDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        ScheduleDay scheduleDay = ScheduleDay.of(day);
        return weeklyCustomScheduleRepository.
                findWeeklyCustomSchedulesByStatusAndTimeAndFrequencyCounterEqualsAndScheduleDay(
                        ScheduleStatus.ACTIVE, time, 1, scheduleDay);
    }
    public List<MonthlyCustomSchedule> getActiveMonthlyCustomSchedules(){
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timezone, ZoneId.SHORT_IDS));
        Integer time = zonedDateTime.getHour();
        Integer day = zonedDateTime.getDayOfMonth();
        return monthlyCustomScheduleRepository
                .findMonthlyCustomSchedulesByStatusAndTimeAndDayAndFrequencyCounterEquals(
                        ScheduleStatus.ACTIVE, time, day, 1);
    }
    public List<DefaultSchedule> getActiveDefaultSchedules(){
        return defaultScheduleRepository.findDefaultSchedulesByStatus(ScheduleStatus.ACTIVE);
    }

    public Integer updateWeeklyCustomScheduleTable(Integer frequencyCounter){
        Integer sum = 0;
        sum += weeklyCustomScheduleRepository.updateFrequencyCounter(2,2-1);
        sum += weeklyCustomScheduleRepository.updateFrequencyCounter(3,3-1);
        sum += weeklyCustomScheduleRepository.updateFrequencyCounter(4,4-1);
        return sum;
    }

    public Integer updateMonthlyCustomScheduleTable(Integer frequencyCounter){
        Integer sum = 0;
        for (int i = 2; i <= 12; i++){
            sum+=monthlyCustomScheduleRepository.updateFrequencyCounter(i, i-1);
        }
        return sum;
    }

    private void createUserTopicItemOffset(User user, SupportedTopics topic){
        UserTopicItemOffset userTopicItemOffset = UserTopicItemOffset
                .builder().topicItemOffset(1L)
                .userId(user.getId()).topic(topic).build();
        if(user.getUserTopicItemOffsets() == null){
            user.getUserTopicItemOffsets().add(userTopicItemOffset);
        }
        else{
            Optional<UserTopicItemOffset> existingUserTopicItemOffset = user.getUserTopicItemOffsets()
                    .stream()
                    .filter(e -> e.getUserId().equals(user.getId()) && e.getTopic().equals(topic))
                    .findFirst();
            if(!existingUserTopicItemOffset.isPresent()){
                user.getUserTopicItemOffsets().add(userTopicItemOffset);
            }
        }
        userService.updateUser(user);
    }

    /*private List<Schedule> saveWeeklySchedules(){
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
    }*/
}
