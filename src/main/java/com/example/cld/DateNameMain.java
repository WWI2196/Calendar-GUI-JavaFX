package com.example.cld;

public class DateNameMain {
    private static final String[] DAYS_OF_WEEK = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
    private static final String[] DAYS_OF_WEEK_AB = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };

    protected static String getDayAbbreviation(int dayNumber) {
        int dayNameIndex = (dayNumber - 1) % 7;
        return DAYS_OF_WEEK[dayNameIndex];
    }

    protected static String getDayAbbreviationAb(int dayNumber) {
        int dayNameIndex = (dayNumber - 1) % 7;
        return DAYS_OF_WEEK_AB[dayNameIndex];
    }
}