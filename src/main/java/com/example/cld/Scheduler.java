package com.example.cld;

import java.io.*;

class Scheduler {
    final Day[] days;
    private final int currentDay;

    public Scheduler(int currentDay) {
        this.currentDay = currentDay;
        days = new Day[31];
        initializeDays();
        try {
            loadEventsFromTxt();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: " + e.getMessage());
        }
    }

    private void initializeDays() {
        String[] daysOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

        for (int i = 0; i < 31; ++i) {
            days[i] = new Day(i + 1, daysOfWeek[i % 7]);
        }
    }

    private void saveEventsToTxt() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/main/resources/com/example/cld/TextFiles/EventFile.txt"))) {
            for (int i = 0; i < 31; ++i) {
                writer.print(days[i].formatDayDataToString());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error saving events");
        }
    }

    private void loadEventsFromTxt() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/cld/TextFiles/EventFile.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) continue;
                int date = Integer.parseInt(line.split("\\|")[0]);
                days[date - 1].extractDayData(line);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error loading events");
        }
    }

    public void scheduleEvent(int date, Event event) {
        try {
            if (date < currentDay || date > 31) {
                throw new IllegalArgumentException("Invalid date");
            }

            if (days[date - 1].isDayOff()) {
                days[date - 1].setDayOff(false);
                throw new IllegalArgumentException("The selected day is marked as a day off");
            }

            Event newEvent = new Event(event.title, event.startTime, event.endTime, event.repeatType);

            if (event.repeatType.equals("daily")) {
                for (int i = date - 1; i < 31; i++) {
                    days[i].addEvent(newEvent);
                }
            } else if (event.repeatType.equals("weekly")) {
                for (int i = date - 1; i < 31; i += 7) {
                    days[i].addEvent(newEvent);
                }
            } else {
                days[date - 1].addEvent(newEvent);
            }

            saveEventsToTxt();
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage() != null ? e.getMessage() : "Error scheduling event.Check the inputs again.");
        }
    }

    public void deleteEvent(int date, String title, boolean deleteRepeats) {
        try {
            if (date < currentDay || date > 31) {
                throw new IllegalArgumentException("Invalid date");
            }

            if (deleteRepeats) {
                for (int i = date - 1; i < 31; ++i) {
                    days[i].deleteEvent(title);
                }
            } else {
                days[date - 1].deleteEvent(title);
            }

            saveEventsToTxt();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting event");
        }
    }

    public void shiftEvent(int date, String title, int newDate) {
        try {
            if (date < currentDay || date > 31 || newDate < currentDay || newDate > 31) {
                throw new IllegalArgumentException("Invalid date");
            }

            days[date - 1].shiftEvent(title, newDate, days);
            saveEventsToTxt();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error shifting event");
        }
    }

    public void markDayOff(int date) {
        try {
            if (date < currentDay || date > 31) {
                throw new IllegalArgumentException("Invalid date");
            }

            days[date - 1].clearEvents();
            days[date - 1].setDayOff(true);
            saveEventsToTxt();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error marking day off");
        }
    }

    public void removeDayOff(int date) {
        try {
            if (date < currentDay || date > 31) {
                throw new IllegalArgumentException("Invalid date");
            }

            days[date - 1].setDayOff(false);
            saveEventsToTxt();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error removing day off");
        }
    }

    public String displayEvents(int date) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("Invalid date");
        }
        if( !days[date - 1].toString().isEmpty()) {
            return days[date - 1].toString();
        }else{
            return "No events";
        }
    }

    public int countWeekEvents(int startDay){
        if (startDay < 1 || startDay > 31) {
            throw new IllegalArgumentException("Invalid start day");
        }

        int startIndex = ((startDay - 1) / 7) * 7;
        int endIndex = Math.min(startIndex + 7, 31);
        int totalEvents = 0;

        for (int i = startIndex; i < endIndex; ++i) {
            if (!days[i].toString().isEmpty()) {
                totalEvents+= days[i].getEventCount();
            }
        }

        return totalEvents;
    }

    public String viewWeekSchedule(int startDay) {
        if (startDay < 1 || startDay > 31) {
            throw new IllegalArgumentException("Invalid start day");
        }

        int startIndex = ((startDay - 1) / 7) * 7;
        int endIndex = Math.min(startIndex + 7, 31);

        for (int i = startIndex; i < endIndex; ++i) {
            String output = days[i].toString();
            if (!output.isEmpty()) {
                return output;
            }else{
                return "No events";
            }
        }
        return null;
    }

    public void displayAllEvents() {
        for (int i = 0; i < 31; ++i) {
            if (!days[i].toString().isEmpty()) {
                System.out.println(days[i].toString());
            }
        }
    }

    public void countEvents() {
        int count = 0;
        for (int i = 0; i < 31; ++i) {
            count += days[i].getEventCount();
        }
        System.out.println("Total number of events: " + count);
    }

    public boolean isEventRepeating(int date, String title) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("Invalid date");
        }
        Day day = days[date - 1];
        for (Event event : day.getEvents()) {
            if (event.getTitle().equals(title) && !event.getRepeatType().equals("none")) {
                return true;
            }
        }
        return false;
    }

    public void checkEventNameExists(int date, String title) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("Invalid date");
        }
        Day day = days[date - 1];
        for (Event event : day.getEvents()) {
            if (event.getTitle().toUpperCase().equals(title)) {
                throw new IllegalArgumentException("Event name already exists");
            }
        }
    }

    public void checkDayOff(int date) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("Invalid date");
        }
        if (days[date - 1].isDayOff()) {
            throw new IllegalArgumentException("The selected day is marked as a day off");
        }
    }
}

