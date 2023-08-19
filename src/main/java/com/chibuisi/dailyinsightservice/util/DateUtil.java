package com.chibuisi.dailyinsightservice.util;

import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final String DATE_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    public static DateTimeFormatter getDateFormat(){
        return DateTimeFormatter.ofPattern(DATE_FORMAT_STRING);
    }
}
