package com.example.cld;

import java.util.Objects;

public class Event {
    public String title;
    public Time startTime;
    public Time endTime;
    public String repeatType;

    public Event(String title, Time startTime, Time endTime, String repeatType) {

        if (title == null || startTime == null || endTime == null || repeatType == null) {
            throw new IllegalArgumentException("Fill all the details. Inputs can not be empty.");
        }

        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repeatType = repeatType;

        if (endTime.isLessThan(startTime)) {
            throw new IllegalArgumentException("End time is less than start time");
        }
    }

    protected boolean overlaps(Event comparisonEvent) {
        return startTime.isLessThan(comparisonEvent.endTime) && endTime.isGreaterThan(comparisonEvent.startTime);
    }

    @Override
    public String toString() {
        return title + " from " + startTime.toString() + " to " + endTime.toString() + " (" + repeatType + ")";
    }

    protected String formatDataToString() {
        return startTime.toString()+"-"+title+"("+(Objects.equals(repeatType, "none") ? "n": Objects.equals(repeatType, "daily") ? "d":"w")+")";
    }

    protected String formatEventDataToString() {
        return title + "|" + startTime.toString() + "|" + endTime.toString() + "|" + repeatType;
    }

    protected void extractEventData(String eventString) {
        String[] parts = eventString.split("\\|");
        this.title = parts[0];
        this.startTime = new Time();
        this.startTime.fromString(parts[1]);
        this.endTime = new Time();
        this.endTime.fromString(parts[2]);
        this.repeatType = parts[3];
    }

    protected String getTitle() {
        return title;
    }

    protected String getRepeatType() {
        return repeatType;
    }
}
