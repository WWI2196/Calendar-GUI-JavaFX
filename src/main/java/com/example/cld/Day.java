package com.example.cld;

import java.util.ArrayList;
import java.util.List;

class Day {
    private int date;
    private final String dayOfWeek;
    private final List<Event> events;
    private boolean isDayOff;

    public Day(int date, String dayOfWeek) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.events = new ArrayList<>();
        this.isDayOff = false;
    }

    private void sortEvents() {
        for (int i = 0; i < events.size() - 1; ++i) {
            for (int j = 0; j < events.size() - i - 1; ++j) {
                if (events.get(j).startTime.isGreaterThan(events.get(j + 1).startTime)) {
                    Event temp = events.get(j);
                    events.set(j, events.get(j + 1));
                    events.set(j + 1, temp);
                }
            }
        }
    }

    public void addEvent(Event event) {
        if (isDayOff) {
            throw new IllegalArgumentException("Cannot add events on a day off");
        }
        for (Event e : events) {
            if (event.overlaps(e)) {
                throw new IllegalArgumentException("Event overlap detected");
            }
        }
        events.add(event);
        sortEvents();
    }

    public void deleteEvent(String title) {
        boolean eventFound = false;
        for (int i = 0; i < events.size(); ++i) {
            if (events.get(i).title.equals(title)) {
                events.remove(i);
                eventFound = true;
                break;
            }
        }
        if (!eventFound) {
            throw new IllegalArgumentException("Event not found");
        }
    }

    public void shiftEvent(String title, int newDate, Day[] days) {
        Event eventToShift = null;

        // Find the event to shift
        for (Event event : events) {
            if (event.title.equals(title)) {
                eventToShift = event;
                break;
            }
        }
        // If the event is found, check for overlapping events and shift it
        if (eventToShift != null) {
            // Check for overlapping events on the new date
            for (Event newDateEvent : days[newDate - 1].getEvents()) {
                if (eventToShift.overlaps(newDateEvent)) {
                    throw new IllegalArgumentException("Event overlap detected");
                }
            }
            // If no overlap, delete the event from the current day and add it to the new date
            deleteEvent(title);
            days[newDate - 1].addEvent(eventToShift);
        } else {
            throw new IllegalArgumentException("Event not found");
        }
    }


    public void clearEvents() {
        events.clear();
    }

    @Override
    public String toString() {
        if (events.isEmpty() && !isDayOff) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
//        sb.append("\n").append(date).append(" July 2024 (").append(dayOfWeek).append(")");

        if (isDayOff) {
            sb.append(" (Day Off)");
        }
        sb.append("\n");

        for (Event event : events) {
            sb.append("  ").append(event.toString()).append("\n");
        }

        return sb.toString();
    }

    public String formatDayDataToString() {
        StringBuilder sb = new StringBuilder();
        if (isDayOff) {
            sb.append(date).append("|off|\n");
        }
        for (Event event : events) {
            sb.append(date).append("|").append(event.formatEventDataToString()).append("\n");
        }
        return sb.toString();
    }

    public void extractDayData(String dayStr) {
        String[] lines = dayStr.split("\n");
        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\|");
            this.date = Integer.parseInt(parts[0]);
            if (parts[1].equals("off")) {
                this.isDayOff = true;
                clearEvents();
            } else {
                Event event = new Event(parts[1], new Time(), new Time(), parts[4]);
                event.extractEventData(line.substring(line.indexOf('|') + 1));
                this.isDayOff = false;
                addEvent(event);
            }
        }
    }

     public boolean isDayOff() {
        return isDayOff;
    }

    public void setDayOff(boolean isDayOff) {
        this.isDayOff = isDayOff;
    }

    public int getEventCount() {
        return events.size();
    }

    /**public boolean toStringPrint() {
        return isDayOff;
    }*/

    public List<Event> getEvents() {
        return events;
    }
}