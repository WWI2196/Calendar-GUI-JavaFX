# JavaFX Calendar Application

This is a Graphical User Interface (GUI) based calendar application for the month of July in the year 2024, built using Java and JavaFX. The application allows users to view, schedule, shift, and delete events. It also features month and week views of the calendar. 

## Installation

1. **Clone the Repository**

2. **Build the Project**
    Use your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse) to open the project and build it. Make sure you have JavaFX set up correctly in your IDE.

## Usage

1. **Run the Application**
    Run the `Main` class located in `src/main/java/com/example/cld/Main.java` to start the application. The welcome screen will display the calendar for July 2024.

2. **Viewing a Day's Schedule**
    Click on any date to view the events scheduled for that day.

3. **Adding an Event**
    Click the "Add Event" button and fill out the form to schedule a new event.

4. **Switching Calendar Views**
    Use the "Month" and "Week" buttons to toggle between month and week views.

5. **Viewing Event Details**
    Click on an event in the schedule to view its details in a new window.

6. **Setting a Day Off**
    Select a day to set as a day off.

7. **Editing, Shifting or Deleting an Event**
    Select an event and use the "Edit" or "Delete" buttons to modify or remove the event.

## Project Structure

- **src**
  - **main**
    - **java**
      - **com.example.cld**
        - `AddEventController.java`
        - `DateNameMain.java`
        - `Day.java`
        - `DeleteEventController.java`
        - `Event.java`
        - `Main.java`
        - `MainController.java`
        - `Scheduler.java`
        - `SetDayOffController.java`
        - `ShiftEventController.java`
        - `Time.java`
        - `ViewMonthController.java`
        - `ViewWeekController.java`
    - **resources**
      - **com.example.cld**
        - **FXML**
          - `AddEvent.fxml`
          - `DeleteEvent.fxml`
          - `Main.fxml`
          - `SetDayOff.fxml`
          - `ShiftEvent.fxml`
          - `ViewMonth.fxml`
          - `ViewWeek.fxml`
        - **Icons**: Contains icon files.
        - **Styles**: Contains CSS stylesheets.
        - **TextFiles**
          - `EventFile.txt`
      - `style.css`
      - `style_add_event.css`
      - `style_date_btm.css`
      - `style_delete_event.css`
      - `style_shift_event.css`

## Requirements

- Java 8 or higher
- JavaFX 11 or higher
- An IDE with JavaFX support
