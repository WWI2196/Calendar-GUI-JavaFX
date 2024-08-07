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

    protected void addEvent(Event event) {
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

    protected void deleteEvent(String title) {
        boolean eventFound = false;
        for (int i = 0; i < events.size(); ++i) {
            if (events.get(i).title.equalsIgnoreCase(title)) {
                events.remove(i);
                eventFound = true;
                break;
            }
        }
        if (!eventFound) {
            throw new IllegalArgumentException("Event not found.");
        }
    }

    protected boolean eventsOverlap(Event event) {
        for (Event e : events) {
            if (event.overlaps(e)) {
                return true;
            }
        }
        return false;
    }

    protected void shiftEvent(String title, int newDate, Day[] days) {
        Event eventToShift = null;
        for (Event event : events) {
            if (event.title.equalsIgnoreCase(title)) {
                eventToShift = event;
                break;
            }
        }

        if (eventToShift != null) {
            deleteEvent(title);
            eventToShift.repeatType = "none";
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
            sb.append("  ").append(event.toString()).append("\n-------------------------------------------------\n");
        }

        return sb.toString();
    }

    protected String formatDayDataToString() {
        StringBuilder sb = new StringBuilder();
        if (isDayOff) {
            sb.append(date).append("|off|\n");
        }
        for (Event event : events) {
            sb.append(date).append("|").append(event.formatEventDataToString()).append("\n");
        }
        return sb.toString();
    }

    protected void extractDayData(String dayStr) {
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


    protected boolean isDayOff() {
        return isDayOff;
    }

    protected void setDayOff(boolean isDayOff) {
        this.isDayOff = isDayOff;
    }

    protected int getEventCount() {
        return events.size();
    }

    /**public boolean toStringPrint() {
        return isDayOff;
    }*/

    protected List<Event> getEvents() {
        return events;
    }


    protected Event getEvent(String title) {
        for (Event event : events) {
            if (event.title.equals(title)) {
                return event;
            }
        }
        return null;
    }

    public String toStringCustom() {
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
            sb.append("  ").append(event.formatDataToString()).append("\n");
        }

        return sb.toString();
    }
}
