package com.example.cld;

public class DateNameMain {
    private static final String[] DAYS_OF_WEEK = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
    private static final String[] DAYS_OF_WEEK_AB = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };

    public static String getDayAbbreviation(int dayNumber) {
        // Calculate the day of the week based on the given day number
        int dayNameIndex = (dayNumber - 1) % 7; // Adjust for zero-based index
        return DAYS_OF_WEEK[dayNameIndex];
    }

    public static String getDayAbbreviationAb(int dayNumber) {
        // Calculate the day of the week based on the given day number
        int dayNameIndex = (dayNumber - 1) % 7; // Adjust for zero-based index
        return DAYS_OF_WEEK_AB[dayNameIndex];
    }
}
