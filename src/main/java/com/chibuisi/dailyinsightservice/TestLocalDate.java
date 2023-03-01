package com.chibuisi.dailyinsightservice;

import java.time.*;
import java.time.temporal.ChronoField;
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
        //System.out.println(localDateTime.getMonthValue());
//        ZonedDateTime dt = localDateTime.atZone(ZoneId.systemDefault());
//        ZonedDateTime dt1 = localDateTime.atZone(ZoneId.of("PST", ZoneId.SHORT_IDS));
//        System.out.println(dt.getHour());
//        System.out.println(dt1.getHour());
        //ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("MST", ZoneId.SHORT_IDS));
        //ZonedDateTime zonedDateTime1 = localDateTime.atZone(ZoneId.of("MST", ZoneId.SHORT_IDS));
        //ZonedDateTime zonedDateTime2 = localDateTime.atZone(ZoneId.of("America/Phoenix"));
        ZonedDateTime zonedDateTime3 = ZonedDateTime.now(ZoneId.of("America/Phoenix"));
        ZonedDateTime zonedDateTime4 = ZonedDateTime.now(ZoneId.of("EST", ZoneId.SHORT_IDS));
        ZonedDateTime zonedDateTime5 = ZonedDateTime.now(ZoneId.of("Africa/Lagos"));
//        System.out.println(zonedDateTime3);
//        System.out.println(zonedDateTime4);
//        System.out.println(zonedDateTime3.getHour());
//        System.out.println(zonedDateTime4.getHour());
//        System.out.println(zonedDateTime4.plusHours(zonedDateTime3.getOffset().get(ChronoField.OFFSET_SECONDS)));
//        System.out.println(Math.abs((zonedDateTime4.getOffset().get(ChronoField.OFFSET_SECONDS) / 3600)));
//        System.out.println(zonedDateTime4.getOffset().compareTo());
        //System.out.println(zonedDateTime5.getOffset());
//        ZonedDateTime z = ZonedDateTime.of(LocalDateTime.now(),ZoneId.of("America/Los_Angeles"));
        ZonedDateTime z1 = ZonedDateTime.of(LocalDateTime.now(),ZoneId.of("MST", ZoneId.SHORT_IDS));
//        System.out.println(z);
//        System.out.println(z1);
        LocalDateTime localNow = LocalDateTime.now();
        LocalDateTime t = LocalDateTime.of(localDate.getYear(), localDate.getMonth(),
                localDate.getDayOfMonth(), 6, 0,0);
        ZonedDateTime t1 = ZonedDateTime.of(t,ZoneId.of("America/New_York"));
        ZonedDateTime t2 = ZonedDateTime.of(t,ZoneId.of("America/Los_Angeles"));
        ZonedDateTime whatINeed = t2.withZoneSameInstant(ZoneId.of("America/Phoenix"));
        System.out.println(t2.getHour());
        System.out.println(whatINeed.getHour());
        //System.out.println(t1.minusHours(z1.getOffset().get(ChronoField.OFFSET_SECONDS)));

        // your local date/time with no timezone information

// setting UTC as the timezone
        ZonedDateTime zonedMSC = localNow.atZone(ZoneId.of("MST", ZoneId.SHORT_IDS));
        //ZonedDateTime zonedPST = localNow.atZone(ZoneId.of("PST", ZoneId.SHORT_IDS));
// converting to IST
        ZonedDateTime zonedPST = zonedMSC.withZoneSameInstant(ZoneId.of("America/New_York"));
        //System.out.println(zonedMSC);
        //System.out.println(zonedPST);
        //System.out.println(zonedMSC.plusHours(zonedPST.getOffset().get(ChronoField.OFFSET_SECONDS)));
//        Integer hoursMst = zonedMSC.getHour();
//        Integer hoursPst = zonedPST.getHour();
//        System.out.println(hoursMst);
//        System.out.println(hoursPst);
//        Integer hour = hoursMst > hoursPst ? hoursMst - hoursPst : hoursPst - hoursMst;
//        System.out.println(hour);
//        System.out.println(z.get(ChronoField.HOUR_OF_DAY));
        //System.out.println(zonedDateTime.getHour());
        //System.out.println(zonedDateTime1.getHour());
        //System.out.println(zonedDateTime2.getHour());
        //System.out.println(zonedDateTime3.getHour());
        //System.out.println(zonedDateTime4.getHour());
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
