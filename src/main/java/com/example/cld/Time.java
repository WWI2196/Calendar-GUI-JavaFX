package com.example.cld;

public class Time {
    private int hour;
    private int minute;

    public Time() {
        this(0, 0);
    }

    public Time(int hour, int minute) {
        if (hour < 0 || hour >= 24 || minute < 0 || minute >= 60) {
            throw new IllegalArgumentException("Invalid time format");
        }
        this.hour = hour;
        this.minute = minute;
    }

    protected boolean isLessThan(Time comparisonTime) {
        return (hour < comparisonTime.hour) || (hour == comparisonTime.hour && minute < comparisonTime.minute);
    }

    protected boolean isGreaterThan(Time comparisonTime) {
        return (hour > comparisonTime.hour) || (hour == comparisonTime.hour && minute > comparisonTime.minute);
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }

    protected void fromString(String timeString) {

        String[] parts = timeString.split("\\D+"); // separate the string using any character as the divider
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid time format");
        }

        try {
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);

            if (hour < 0 || hour >= 24 || minute < 0 || minute >= 60) {
                throw new IllegalArgumentException("Invalid time format");
            }

            this.hour = hour;
            this.minute = minute;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid time format", e);
        }
    }
}
