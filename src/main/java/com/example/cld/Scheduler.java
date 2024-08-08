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
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

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

   protected void scheduleEvent(int date, Event event) {
       boolean wasDayOff = false;
       try {
           if (date < currentDay || date > 31) {
               throw new IllegalArgumentException("Invalid date");
           }

           wasDayOff = days[date - 1].isDayOff();// get the original day off status

           if (wasDayOff) { // set day off to false if the event is successfully scheduled
               days[date - 1].setDayOff(false);
           }

           Event newEvent = new Event(event.title, event.startTime, event.endTime, event.repeatType);

           try {
               checkEventNameExists(date, event.title);
           } catch (IllegalArgumentException e) {
               throw new IllegalArgumentException("Event name already exists");
           }

           if (event.repeatType.equals("daily")) {
               for (int i = date - 1; i < 31; ++i) {
                   if (days[i].eventsOverlap(newEvent)) {
                       throw new IllegalArgumentException("Event overlaps detected on " + (i + 1) + ". Cannot schedule.");
                   }
                   if (days[i].isDayOff()) {
                       throw new IllegalArgumentException("Day off detected on " + (i + 1) + ". Cannot schedule.");
                   }
               }
               for (int i = date - 1; i < 31; i++) {
                   days[i].addEvent(newEvent);
               }
           } else if (event.repeatType.equals("weekly")) {
               for (int i = date - 1; i < 31; i += 7) {
                   if (days[i].eventsOverlap(newEvent)) {
                       throw new IllegalArgumentException("Event overlaps detected on " + (i + 1) + ". Cannot schedule.");
                   }
                   if (days[i].isDayOff()) {
                       throw new IllegalArgumentException("Day off detected on " + (i + 1) + ". Cannot schedule.");
                   }
               }
               for (int i = date - 1; i < 31; i += 7) {
                   days[i].addEvent(newEvent);
               }
           } else {
               days[date - 1].addEvent(newEvent);
           }

           saveEventsToTxt();
       } catch (Exception e) {
           if (wasDayOff) { // reset changes if the day off was changed
               days[date - 1].setDayOff(true);
           }
           throw new IllegalArgumentException(e.getMessage() != null ? e.getMessage() : "Error scheduling event. Check the inputs again.");
       }
   }

    protected void deleteEvent(int date, String title, boolean deleteRepeats, String repeatType) {
        try {
            if (date < currentDay || date > 31) {
                throw new IllegalArgumentException("Invalid date");
            }

            if (days[date - 1].isDayOff()) {
                throw new IllegalArgumentException("The selected day is marked as a day off.\nNo events to delete.");
            }

            if (!isEventNameExists(date, title)) {
                throw new IllegalArgumentException("Event not found.");
            }

            if (deleteRepeats) {
                if ("daily".equals(repeatType)) {
                    for (int i = date - 1; i < 31; ++i) {
                        try {
                            days[i].deleteEvent(title);
                        } catch (IllegalArgumentException e) {
                            //  if event not found on this day, continue to next day
                        }
                    }
                } else if ("weekly".equals(repeatType)) {
                    for (int i = date - 1; i < 31; i += 7) {
                        try {
                            days[i].deleteEvent(title);
                        } catch (IllegalArgumentException e) {
                            // if event not found on this day, continue to next day
                        }
                    }
                }
            } else {
                days[date - 1].deleteEvent(title);
            }
            saveEventsToTxt();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting event: " + e.getMessage());
        }
    }

    protected String getEventRepeatType(int date, String title) {
        String lowercaseTitle = title.toLowerCase();

        for (Event event : days[date - 1].getEvents()) {
            if (event.getTitle().toLowerCase().equals(lowercaseTitle)) {
                return event.getRepeatType();
            }
        }
        return "none";
    }

    protected void shiftEvent(int date, String title, int newDate) {
        try {
            if (date < currentDay || date > 31 || newDate < currentDay || newDate > 31) {
                throw new IllegalArgumentException("Invalid date");
            }

            days[date - 1].shiftEvent(title, newDate, days);
            saveEventsToTxt();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error shifting event" + e.getMessage());
        }
    }

    protected void markDayOff(int date) {
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

    protected void removeDayOff(int date) {
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

    protected String displayEvents(int date) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("Invalid date");
        }
        if (!days[date - 1].toString().isEmpty()) {
            return days[date - 1].toString();
        } else {
            return "No events";
        }
    }

    protected int countEvents(int startDay, int endDay) {
        if (startDay < 1 || startDay > 31 || endDay < 1 || endDay > 31) {
            throw new IllegalArgumentException("Invalid day range");
        }

        int totalEvents = 0;

        for (int i = startDay - 1; i < endDay; ++i) {
            if (days[i] != null && !days[i].toString().isEmpty()) {
                totalEvents += days[i].getEventCount();
            }
        }

        return totalEvents;

    }

    protected void checkEventNameExists(int date, String title) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("Invalid date");
        }
        Day day = days[date - 1];
        for (Event event : day.getEvents()) {
            if (event.getTitle().equalsIgnoreCase(title)) {
                throw new IllegalArgumentException("Event name already exists");
            }
        }
    }

    protected boolean isEventNameExists(int date, String title) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("Invalid date");
        }
        Day day = days[date - 1];
        for (Event event : day.getEvents()) {
            if (event.getTitle().equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
    }

    public void validEventCapture(int date, String title) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("Invalid date");
        }
        Day day = days[date - 1];
        for (Event event : day.getEvents()) {
            if (event.getTitle().toUpperCase().equals(title)) {
                throw new IllegalArgumentException("Event Captured");
            }
        }
    }

    public String displayEventsForMonths(int date) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("Invalid date");
        }
        if (!days[date - 1].toString().isEmpty()) {
            return days[date - 1].toStringCustom();
        } else {
            return "No events";
        }
    }
}