package com.example.cld;

import java.io.*;
import java.util.Scanner;

class Scheduler {
    private final Day[] days;
    private final int currentDay;

    public Scheduler(int currentDay) {
        this.currentDay = currentDay;
        days = new Day[31];
        initializeDays();
        try {
            loadEventsFromTxt();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void initializeDays() {
        String[] daysOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

        for (int i = 0; i < 31; ++i) {
            days[i] = new Day(i + 1, daysOfWeek[i % 7]);
        }
    }

    private void saveEventsToTxt() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/main/EventFile.txt"))) {
            for (int i = 0; i < 31; ++i) {
                writer.print(days[i].formatDayDataToString());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error saving events");
        }
    }

    private void loadEventsFromTxt() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/EventFile.txt"))) {
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
                Scanner scanner = new Scanner(System.in);
                System.out.print("The selected day is marked as a day off. Do you want to proceed? (yes/no): ");
                String confirmation = scanner.nextLine();

                if (!confirmation.equalsIgnoreCase("yes")) {
                    return;
                }
                days[date - 1].setDayOff(false);
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
            throw new IllegalArgumentException("Error scheduling event");
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

    public String displayEvents(int date) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("Invalid date");
        }
        return days[date - 1].toString();
    }

    public void viewWeekSchedule(int startDay) {
        if (startDay < 1 || startDay > 31) {
            throw new IllegalArgumentException("Invalid start day");
        }

        int startIndex = ((startDay - 1) / 7) * 7;
        int endIndex = Math.min(startIndex + 7, 31);

        for (int i = startIndex; i < endIndex; ++i) {
            String output = days[i].toString();
            if (!output.isEmpty()) {
                System.out.println(output);
            }
        }
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
}
