package com.example.cld;

import com.jfoenix.controls.JFXButton;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Optional;

import static com.example.cld.Main.dayOfMonth;

public class DeleteEventController {

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
    private JFXButton confirm_btm_deleteEvent;

    @FXML
    private Pane date_picker;

    @FXML
    private JFXButton enter_button;

    @FXML
    private TextField enter_date_txt_field;

    @FXML
    private Label enter_day_name_label;

    @FXML
    private Label enter_day_number_label;

    @FXML
    private TextField enter_event_name_txt_field;

    @FXML
    private Pane enter_today_pane;

    @FXML
    private Label events_on_enter_day_label;

    @FXML
    private ImageView inner_pane_image1;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private Label today_day_name_label;

    @FXML
    private Label today_day_number_label;

    @FXML
    private Pane today_pane;

    @FXML
    private Label Error_date;

    @FXML
    private Label error_name_label;

    private MainController mainController = MainController.getInstance();

    @FXML
    public void switchToMainMenu(javafx.event.Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToMainMenu(event);
    }
    @FXML
    public void switchToAddEventDetails(javafx.event.Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToAddEventDetails(event);
    }
    @FXML
    public void switchToSetDayOff(javafx.event.Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToSetDayOff(event);
    }
    @FXML
    public void switchToDeleteEvent(javafx.event.Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToDeleteEvent(event);
    }
    @FXML
    public void switchToShiftEvent(Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToShiftEvent(event);
    }


    @FXML
    private void Error() {
        Window owner = confirm_btm_deleteEvent.getScene().getWindow();
        // Create the alert
        MainController.AlertHelper.showAlert(
                Alert.AlertType.INFORMATION,owner,
                "Delete Event",
                "Error",
                "There is a problem with entered values; check whether the entered values are in correct format",
                "/com/example/cld/Icons/CrossSign.png",
                "/com/example/cld/Icons/deleteEvent.png"
        );
    }

    @FXML
    private void successPopup() {
        Window owner = confirm_btm_deleteEvent.getScene().getWindow();
        // Create the alert
        MainController.AlertHelper.showAlert(
                Alert.AlertType.INFORMATION,owner,
                "Delete Event",
                "Success",
                "Event Successfully Deleted",
                "/com/example/cld/Icons/Done_img_1.png",
                "/com/example/cld/Icons/deleteEvent.png"
        );
    }

    public void initialize() {
        confirm_btm_deleteEvent.setOnAction(event_ -> {
            try {
                int dayToDelete = Integer.parseInt(enter_date_txt_field.getText());

                // Validate the entered date
                if (dayToDelete < dayOfMonth || dayToDelete > 31) {
                    Error_date.setVisible(true);
                    throw new IllegalArgumentException(dayOfMonth == 31? "31st is the last day of the month.":"Enter a valid date between " + dayOfMonth + " and 31.");
                }

                /*
                if (mainController.getScheduler().days[dayToDelete - 1].isDayOff()) {
                    Window owner = confirm_btm_deleteEvent.getScene().getWindow();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Schedule Event");
                    alert.setHeaderText("Confirmation");
                    alert.setContentText("The selected day is marked as a day off. Do you want to proceed?");
                    alert.initOwner(owner);

                    // Load and set the alert's display icon
                    Image alertImage = new Image(MainController.AlertHelper.class.getResourceAsStream("/com/example/cld/Icons/DayOff_1_1.png"));
                    ImageView alertImageView = new ImageView(alertImage);
                    alertImageView.setFitWidth(40); // Set desired width
                    alertImageView.setFitHeight(40); // Set desired height
                    alert.setGraphic(alertImageView);

                    ButtonType yesButton = ButtonType.YES;
                    ButtonType noButton = ButtonType.NO;
                    alert.getButtonTypes().setAll(yesButton, noButton);

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == yesButton) {
                        mainController.getScheduler().days[dayToSchedule - 1].setDayOff(false);
                        events_on_enter_day_textArea.setText(mainController.getScheduler().displayEvents(dayToSchedule));
                    } else {
                        throw new IllegalArgumentException("The selected day is marked as a day off. Can not schedule.");
                    }

                */


                if (enter_event_name_txt_field.getText().isEmpty()) {
                    throw new IllegalArgumentException("Enter a name for the event.");
                }

                Error_date.setVisible(false); // Hide the error label if the date is valid

                String title = enter_event_name_txt_field.getText(); // done

                enter_day_number_label.setText(String.valueOf(dayToDelete));
                enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayToDelete));
                events_on_enter_day_label.setText(mainController.getScheduler().displayEvents(dayToDelete));

                /*
                Time startTime = new Time();
                Time endTime = new Time();
                startTime.fromString(enter_start_time_txt_field.getText());
                endTime.fromString(enter_end_time_txt_field.getText());
                */

                //com.example.cld.Event event = new com.example.cld.Event(title,startTime,endTime,repeatType);
                boolean repeatItemDelete = false;

                mainController.getScheduler().deleteEvent(dayToDelete, title, repeatItemDelete);

                successPopup();
                clearInputFields();
                events_on_enter_day_label.setText(mainController.getScheduler().displayEvents(dayToDelete));

            } catch (NumberFormatException e) {
                showPopup(e.getMessage());
            } catch (IllegalArgumentException e) {
                showPopup(e.getMessage());
            } catch (Exception e) {
                showPopup(e.getMessage());
            }
        });

        today_day_number_label.setText(String.valueOf(dayOfMonth));
        today_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayOfMonth));

        if(dayOfMonth != 31) {
            enter_date_txt_field.setPromptText(dayOfMonth + " - 31");
        }else {
            enter_date_txt_field.setText(String.valueOf(dayOfMonth));
        }


        // Hide the Error_date label initially
        Error_date.setVisible(false);
    }

    private void showPopup(String message) {
        Window owner = confirm_btm_deleteEvent.getScene().getWindow();
        // Create the alert
        MainController.AlertHelper.showAlert(
                Alert.AlertType.ERROR,owner,
                "Delete Event",
                "Error",
                message,
                "/com/example/cld/Icons/CrossSign.png",
                "/com/example/cld/Icons/deleteEvent.png"
        );
    }

    private void clearInputFields() {
        enter_date_txt_field.clear();
        enter_event_name_txt_field.clear();
        error_name_label.setVisible(false);
    }

    public void checkDate() {
        String input = enter_date_txt_field.getText().trim();

        if (input.isEmpty()) {
            Error_date.setVisible(false);
            return;
        }

        try {
            int day = Integer.parseInt(input);
            if (day < dayOfMonth || day > 31) {
                Error_date.setVisible(true);
                Error_date.setText(dayOfMonth == 31? "31st is the last day of the month.":"Enter a valid date between " + dayOfMonth + " and 31.");
            } else {
                Error_date.setVisible(false);
                enter_day_number_label.setText(String.valueOf(day));
                enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(day));
                events_on_enter_day_label.setText(mainController.getScheduler().displayEvents(day));
            }
        } catch (NumberFormatException e) {
            Error_date.setVisible(true);
            Error_date.setText("Enter a valid number.");
        }

    }

    public void checkName() {
        String input = enter_event_name_txt_field.getText().toUpperCase().trim();

        if (input.isEmpty()) {
            error_name_label.setVisible(false);
            return;
        }

        try {
            if (!enter_date_txt_field.getText().isEmpty()) {
                int day = Integer.parseInt(enter_date_txt_field.getText());
                mainController.getScheduler().validEventCapture(day, input);
                error_name_label.setVisible(false);
            } else {
                error_name_label.setText(null);
            }
        } catch (NumberFormatException e) {
            error_name_label.setVisible(true);
            error_name_label.setText("Enter a valid date.");
        } catch (IllegalArgumentException e) {
            error_name_label.setVisible(true);
            error_name_label.setText(e.getMessage());
        }
    }



}
