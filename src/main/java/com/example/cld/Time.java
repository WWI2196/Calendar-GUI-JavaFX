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

    public boolean isLessThan(Time comparisonTime) {
        return (hour < comparisonTime.hour) || (hour == comparisonTime.hour && minute < comparisonTime.minute);
    }

    public boolean isGreaterThan(Time comparisonTime) {
        return (hour > comparisonTime.hour) || (hour == comparisonTime.hour && minute > comparisonTime.minute);
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }

    public void fromString(String timeString) {
        String[] parts = timeString.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        if (hour < 0 || hour >= 24 || minute < 0 || minute >= 60) {
            throw new IllegalArgumentException("Invalid time format");
        }

        this.hour = hour;
        this.minute = minute;
    }
}
