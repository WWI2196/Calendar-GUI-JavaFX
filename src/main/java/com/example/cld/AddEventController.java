package com.example.cld;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.Event;
import javafx.stage.Window;

import java.io.IOException;

import static com.example.cld.Main.dayOfMonth;

public class AddEventController {

    private Stage stage;
    private Scene scene;

    @FXML
    private JFXButton back_to_main_btm;

    @FXML
    private JFXButton btm_addEvent;

    @FXML
    private JFXButton btm_dayOff;

    @FXML
    private JFXButton btm_deleteEvent;

    @FXML
    private JFXButton btm_shiftEvent;

    @FXML
    private JFXButton check_button;

    @FXML
    private JFXButton confirm_btm_addEvent;

    @FXML
    private Pane date_picker;

    @FXML
    private TextField enter_date_txt_field;

    @FXML
    private Label enter_day_name_label;

    @FXML
    private Label enter_day_number_label;

    @FXML
    private TextField enter_end_time_txt_field;

    @FXML
    private TextField enter_event_name_txt_field;

    @FXML
    private TextField enter_start_time_txt_field;

    @FXML
    private Pane enter_today_pane;

    @FXML
    private JFXTextArea events_on_enter_day_textArea;

    @FXML
    private ImageView inner_pane_image1;

    @FXML
    private HBox root;

    @FXML
    private Label today_day_name_label;

    @FXML
    private Label today_day_number_label;

    @FXML
    private Pane today_pane;

    @FXML
    private RadioButton one_time_radio_btn;

    @FXML
    private RadioButton daily_radio_btn;

    @FXML
    private RadioButton weekly_radio_btn;

    @FXML
    private Label Error_date;

    public static Scheduler scheduler;
    private MainController mainController = MainController.getInstance();

    public void switchToMainMenu(Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToMainMenu(event);
    }
    @FXML
    public void switchToAddEventDetails(Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToAddEventDetails(event);
    }
    @FXML
    public void switchToSetDayOff(Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToSetDayOff(event);
    }
    @FXML
    public void switchToDeleteEvent(Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToDeleteEvent(event);
    }
    @FXML
    public void switchToShiftEvent(Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToShiftEvent(event);

    }

    @FXML
    private void Error() {
        Window owner = confirm_btm_addEvent.getScene().getWindow();
        // Create the alert
        MainController.AlertHelper.showAlert(
            Alert.AlertType.INFORMATION,owner,
            "Schedule Event",
            "Error",
            "There is a problem with entered values; check whether the entered values are in correct format",
            "/com/example/cld/Icons/CrossSign.png",
            "/com/example/cld/Icons/addEvent.png"
        );
    }

    @FXML
    private void successPopup() {
        Window owner = confirm_btm_addEvent.getScene().getWindow();
        // Create the alert
        MainController.AlertHelper.showAlert(
            Alert.AlertType.INFORMATION,owner,
            "Schedule Event",
            "Success",
            "Event Successfully added",
            "/com/example/cld/Icons/Done_img_1.png",
            "/com/example/cld/Icons/addEvent.png"
        );
    }

    public void initialize() {

//        confirm_btm_addEvent.setOnAction(event -> {// when the submitted button is clicked
//            try {

//                if (enter_date_txt_field.getText().isEmpty()) { // check if the name field is empty
//                    throw new IllegalArgumentException("Date cannot be empty."); // if the name field is empty, throw exception
//                }
//                if (nameTextField.getText().isEmpty()) { // check if the name field is empty
//                    throw new IllegalArgumentException("Name cannot be empty."); // if the name field is empty, throw exception
//                }
//
//                for (DriverDetails item : dataRepository) { // check if the name already exists
//                    if (nameTextField.getText().toUpperCase().equals(item.getName())) {
//                        throw new IllegalArgumentException("Name already exists.");
//                    }
//                }
//
//                try {
//                    Integer.parseInt(ageField.getText());
//                    if (ageField.getText().isEmpty() || Integer.parseInt(ageField.getText()) < 15
//                            || Integer.parseInt(ageField.getText()) > 99) { // check if the age is between 15 and 99
//                        throw new NumberFormatException();
//                    }
//                } catch (NumberFormatException e) {
//                    throw new IllegalArgumentException("Enter a valid age.");
//                }
//
//                try {
//                    Integer.parseInt(pointsField.getText());
//                } catch (NumberFormatException e) { // check if points is an integer number
//                    throw new IllegalArgumentException("Points must be a integer.");
//                }
//
//                String name = nameTextField.getText().toUpperCase(); // get name from text field
//                int age = Integer.parseInt(ageField.getText()); // get age from text field
//                String team = teamTextField.getText(); // get team from text field
//                String carModel = carTextField.getText(); // get a car model from text field
//                int points = Integer.parseInt(pointsField.getText()); // get points from text field
//
//                dataRepository.add(new DriverDetails(name, age, team, carModel, points)); // add driver details to data repository
//
//                successText.setText("Driver details added successfully");
//                nameTextField.clear();
//                ageField.clear();
//                teamTextField.clear();
//                carTextField.clear();
//                pointsField.clear();
//
//
//                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event01 -> successText.setText(null)));
//                timeline.play();
//
//            } catch (IllegalArgumentException e) { // if the fields are empty or invalid, show an error message
//                Window owner = submitButton.getScene().getWindow(); // get window
//                MainController.AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error",
//                        "Invalid input. "+e.getMessage());
//            }
//        });

        today_day_number_label.setText(String.valueOf(dayOfMonth));
        today_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayOfMonth));

        if(dayOfMonth != 31) {
            enter_date_txt_field.setPromptText(dayOfMonth + " - 31");
        }else {
            enter_date_txt_field.setText(String.valueOf(dayOfMonth));
        }

          // Configure the ToggleGroup for repeatType
        ToggleGroup repeatTypeGroup = new ToggleGroup();
        one_time_radio_btn.setToggleGroup(repeatTypeGroup);
        daily_radio_btn.setToggleGroup(repeatTypeGroup);
        weekly_radio_btn.setToggleGroup(repeatTypeGroup);

        // Add an event handler for the confirm button
        confirm_btm_addEvent.setOnAction(this::onConfirmButtonClick);

        // Hide the Error_date label initially
        Error_date.setVisible(false);
    }

     /**
     * Method to handle the confirm button click event
     */
    private void onConfirmButtonClick(ActionEvent event) {
        // Schedule the event
        scheduleEvent();

        // Clear all input fields
        clearInputFields();

        // Optionally, clear other UI components
        resetCheckBoxesAndRadioButtons();
    }

    protected void scheduleEvent() {
        try {

            int dayToschedule = Integer.parseInt(enter_date_txt_field.getText());

            // Validate the entered date
            if (dayToschedule < dayOfMonth || dayToschedule > 31) {
                Error_date.setVisible(true);
                Error_date.setText("Enter a valid date between " + dayOfMonth + " and 31.");
                return;
            }

            Error_date.setVisible(false); // Hide the error label if the date is valid

            String title = enter_event_name_txt_field.getText(); // done


            enter_day_number_label.setText(String.valueOf(dayToschedule));
            enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayToschedule));
            events_on_enter_day_textArea.setText(mainController.getScheduler().displayEvents(dayToschedule));

            Time startTime = new Time();
            Time endTime = new Time();
            startTime.fromString(enter_start_time_txt_field.getText());
            endTime.fromString(enter_end_time_txt_field.getText());

            String repeatType = "none";
            if (daily_radio_btn.isSelected()) {
                repeatType = "daily";
            } else if (weekly_radio_btn.isSelected()) {
                repeatType = "weekly";
            }

            com.example.cld.Event event = new com.example.cld.Event(title,startTime,endTime,repeatType);

            mainController.getScheduler().scheduleEvent(dayToschedule, event);
            successPopup();
            events_on_enter_day_textArea.setText(mainController.getScheduler().displayEvents(dayToschedule));


        } catch (NumberFormatException e) {
            showPopup("Invalid number format for time fields.");
        } catch (IllegalArgumentException e) {
            showPopup(e.getMessage());
        } catch (Exception e) {
            showPopup("An unexpected error occurred.");
        }
    }

    private void showPopup(String message) {
        Window owner = confirm_btm_addEvent.getScene().getWindow();
        // Create the alert
        MainController.AlertHelper.showAlert(
            Alert.AlertType.ERROR,owner,
            "Schedule Event",
            "Error",
            message,
            "/com/example/cld/Icons/CrossSign.png",
            "/com/example/cld/Icons/addEvent.png"
        );
    }

    /**
     * Clear all input fields
     */
    private void clearInputFields() {
        //enter_date_txt_field.clear();
        enter_event_name_txt_field.clear();
        enter_start_time_txt_field.clear();
        enter_end_time_txt_field.clear();
        resetCheckBoxesAndRadioButtons();
        //events_on_enter_day_textArea.clear();
    }

    /**
     * Optionally reset checkboxes and radio buttons to default state
     */
    private void resetCheckBoxesAndRadioButtons() {

        one_time_radio_btn.setSelected(false);
        daily_radio_btn.setSelected(false);
        weekly_radio_btn.setSelected(false);
    }

    public void checkDate() {
    String input = enter_date_txt_field.getText().trim();

    // Check if the input is empty
    if (input.isEmpty()) {
        Error_date.setVisible(false);
        return;
    }

    try {
        int day = Integer.parseInt(input);
        if (day < dayOfMonth || day > 31) {
            Error_date.setVisible(true);
            Error_date.setText("Enter a date between " + dayOfMonth + " and 31.");
        } else {
            Error_date.setVisible(false);
            enter_day_number_label.setText(String.valueOf(day));
            enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(day));
            events_on_enter_day_textArea.setText(mainController.getScheduler().displayEvents(day));
        }
    } catch (NumberFormatException e) {
        Error_date.setVisible(true);
        Error_date.setText("Enter a valid number.");
    }
}



}
