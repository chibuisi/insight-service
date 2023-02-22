package com.chibuisi.dailyinsightservice;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Locale;

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
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
//        System.out.println(yesterday.get(weekFields.weekOfYear()) ==
//                friday.get(weekFields.weekOfYear()));
//        System.out.println(localDate.get(weekFields.weekOfYear()) ==
//                friday.plusDays(4).get(weekFields.weekOfYear()));
        System.out.println(localDate.get(weekFields.weekOfYear()));
        System.out.println(friday.plusDays(4).get(weekFields.weekOfYear()));
    }
}
