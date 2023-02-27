package com.chibuisi.dailyinsightservice;

import java.time.*;
import java.time.temporal.WeekFields;
import java.util.*;

public class TestLocalDate {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        DayOfWeek todayDayOfWeek = localDate.getDayOfWeek();
        //System.out.println(todayDayOfWeek.getValue());
        LocalDate yesterday = LocalDate.of(2023,02,19);
        DayOfWeek nextTomorrow = Arrays.stream(DayOfWeek.values())
                .filter(d -> d.name().equalsIgnoreCase("saturday"))
                .findFirst().orElse(DayOfWeek.MONDAY);
        LocalDate friday = yesterday.plusDays(nextTomorrow.getValue());
//        System.out.println(yesterday.equals(friday));
//        System.out.println(yesterday.isBefore(friday));
        //WeekFields weekFields = WeekFields.of(Locale.getDefault());
//        System.out.println(yesterday.get(weekFields.weekOfYear()) ==
//                friday.get(weekFields.weekOfYear()));
//        System.out.println(localDate.get(weekFields.weekOfYear()) ==
//                friday.plusDays(4).get(weekFields.weekOfYear()));
        //System.out.println(localDate.get(weekFields.weekOfYear()));
        //System.out.println(friday.plusDays(4).get(weekFields.weekOfYear()));
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.getMonthValue());
        //ZonedDateTime dt = localDateTime.atZone(ZoneId.systemDefault());
        //ZonedDateTime dt1 = localDateTime.atZone(ZoneId.of("PST"));
        //System.out.println(dt.getHour());
        //System.out.println(dt1.getHour());
        //ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("MST", ZoneId.SHORT_IDS));
        //ZonedDateTime zonedDateTime1 = localDateTime.atZone(ZoneId.of("MST", ZoneId.SHORT_IDS));
        //ZonedDateTime zonedDateTime2 = localDateTime.atZone(ZoneId.of("America/Phoenix"));
        ZonedDateTime zonedDateTime3 = ZonedDateTime.now(ZoneId.of("America/Phoenix"));
        ZonedDateTime zonedDateTime4 = ZonedDateTime.now(ZoneId.of("MST", ZoneId.SHORT_IDS));
        //System.out.println(zonedDateTime.getHour());
        //System.out.println(zonedDateTime1.getHour());
        //System.out.println(zonedDateTime2.getHour());
        System.out.println(zonedDateTime3.getHour());
        System.out.println(zonedDateTime4.getHour());
        //System.out.println(localDateTime.getHour());
        Set<String> timeZones = new HashSet<>(Arrays.asList(TimeZone.getAvailableIDs()));
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        //System.out.println("Extra TimeZone's: " + diff(timeZones, zoneIds));
        //System.out.println("Extra ZoneId's: " + diff(zoneIds, timeZones));
    }
    static Set<String> diff(Set<String> a, Set<String> b) {
        Set<String> diff = new TreeSet<>(a);
        diff.removeAll(b);
        return diff;
    }
}
