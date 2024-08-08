# JavaFX Calendar Application

This is a Graphical User Interface (GUI) based calendar application for the month of July in the year 2024, built using Java and JavaFX. The application allows users to view, schedule, shift, set day-offs, and delete events. It also features month and week views of the calendar. 

## Installation

### Method 1: Using a Java IDE

1. **Clone the Repository**
   - Clone this repository to your local machine using `git clone <repository-url>`.

2. **Build the Project**
   - Use your preferred Java IDE (e.g., IntelliJ IDEA) to open the project and build it. 
   - Ensure that JavaFX is set up correctly in your IDE.

### Method 2: Running Directly via Maven

1. **Download and Extract the Zip File**
   - Download the zip file from the repository and extract its contents into a folder.

2. **Download Apache Maven**
   - Download Apache Maven from [here](https://maven.apache.org/download.cgi).

3. **Add New System Variable for Maven**
   - Add a new system variable named `MAVEN_HOME`.
   - Set the variable value to the path of the `<apache-maven-3.9.8>` folder that was downloaded.

4. **Add Maven Bin to Path**
   - Add the `<apache-maven-3.9.8 bin>` folder to your system's `Path`.

5. **Confirm Path Configuration**
   - Click "OK" to save the changes.

6. **Verify Maven Configuration**
   - Open Command Prompt.
   - Run the command: `mvn --version`.
   - If Maven is configured correctly, it should display the Apache Maven version.

7. **Navigate to Project Folder**
   - Open the extracted folder and navigate to the project folder.

8. **Open Command Prompt in Project Folder**
   - Open Command Prompt in the project folder by clicking on the path address, typing `cmd`, and pressing Enter.

9. **Run the Application**
   - In the command prompt, run the command: `mvn javafx:run`.
   - This will start the GUI application.

## Usage

1. **Run the Application**
    - Run the `Main` class located in `src/main/java/com/example/cld/Main.java` to start the application. The welcome screen will display the calendar for July 2024.

2. **Viewing a Day's Schedule**
    - Click on any date to view the events scheduled for that day.

3. **Adding an Event**
    - Click the "Add Event" button and fill out the form to schedule a new event.

4. **Switching Calendar Views**
    - Use the "Month" and "Week" buttons to toggle between month and week views.

5. **Viewing Event Details**
    - Click on an event in the schedule to view its details in a new window.

6. **Setting a Day Off**
    - Select a day to set as a day off.

7. **Editing, Shifting or Deleting an Event**
    - Select an event and use the "Edit" or "Delete" buttons to modify or remove the event.

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

- Java 22 
- JavaFX 22
- An IDE with JavaFX support
