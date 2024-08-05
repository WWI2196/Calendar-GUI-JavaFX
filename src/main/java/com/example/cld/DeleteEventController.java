
package com.example.cld;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static com.example.cld.Main.dayOfMonth;

public class DeleteEventController {
     @FXML
    private Label Error_date_label;

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
    private JFXButton btm_viewMonth;

    @FXML
    private JFXButton btm_viewWeek;

    @FXML
    private JFXButton confirm_btm_deleteEvent;

    @FXML
    private TextField enter_date_txt_field;

    @FXML
    private Label enter_day_name_label;

    @FXML
    private Label enter_day_number_label;

    @FXML
    private TextField enter_event_name_txt_field;

    @FXML
    private Label error_name_label;

    @FXML
    private TextArea events_on_enter_day_textArea;

    @FXML
    private Label today_day_name_label;

    @FXML
    private Label today_day_number_label;


    private final MainController mainController = MainController.getInstance();

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
    public void switchToViewWeek(Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToViewWeek(event);
    }
    @FXML
    public void switchToViewMonth(Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToViewMonth(event);
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
        confirm_btm_deleteEvent.setOnAction(this::handle);

        if(dayOfMonth != 31) {
            enter_date_txt_field.setPromptText(dayOfMonth + " - 31");
        }else {
            enter_date_txt_field.setText(String.valueOf(dayOfMonth));
        }

        today_day_number_label.setText(String.valueOf(dayOfMonth));
        today_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayOfMonth));

    }

    private void handle(Event event) {
        try {
            if (enter_date_txt_field.getText().isEmpty()) {
                throw new IllegalArgumentException("Enter a date to select.");
            }

            int dayToDelete = Integer.parseInt(enter_date_txt_field.getText());
            boolean deleteRepeats = false;
            String title = enter_event_name_txt_field.getText().trim();

            // Validate the entered date
            if (dayToDelete < dayOfMonth || dayToDelete > 31) {
                Error_date_label.setVisible(true);
                throw new IllegalArgumentException(dayOfMonth == 31 ? "31st is the last day of the month." : "Enter a valid date between " + dayOfMonth + " and 31.");
            }

            if (title.isEmpty()) {
                throw new IllegalArgumentException("Enter the name of the event.");
            }


            if (!mainController.getScheduler().isEventNameExists(dayToDelete, title)) {
                throw new IllegalArgumentException("Event not found.");
            }

            // Check if the event is repeating
            String repeatType = mainController.getScheduler().getEventRepeatType(dayToDelete, title);


            if (!repeatType.equals("none")) {
                Window owner = confirm_btm_deleteEvent.getScene().getWindow();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Event");
                alert.setHeaderText("Confirmation");
                alert.setContentText("The selected event is a " + repeatType + " event. Do you want to delete all occurrences?");
                alert.initOwner(owner);

                Image alertImage = new Image(Objects.requireNonNull(MainController.AlertHelper.class.getResourceAsStream("/com/example/cld/Icons/event_repeat.png")));
                ImageView alertImageView = new ImageView(alertImage);
                alertImageView.setFitWidth(40);
                alertImageView.setFitHeight(40);
                alert.setGraphic(alertImageView);

                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                Image windowIcon = new Image(Objects.requireNonNull(MainController.AlertHelper.class.getResourceAsStream("/com/example/cld/Icons/deleteEvent.png")));
                stage.getIcons().clear(); // Clear existing icons
                stage.getIcons().add(windowIcon);

                ButtonType yesButton = ButtonType.YES;
                ButtonType noButton = ButtonType.NO;
                alert.getButtonTypes().setAll(yesButton, noButton);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == yesButton) {
                    deleteRepeats = true;
                }
            }

            Error_date_label.setVisible(false);

            enter_day_number_label.setText(String.valueOf(dayToDelete));
            enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayToDelete));
            events_on_enter_day_textArea.setText(mainController.getScheduler().displayEvents(dayToDelete));

            mainController.getScheduler().deleteEvent(dayToDelete, title, deleteRepeats, repeatType);

            successPopup();
            clearInputFields();
            events_on_enter_day_textArea.setText(mainController.getScheduler().displayEvents(dayToDelete));

        } catch (NumberFormatException e) {
            showPopup("Invalid date format.");
        } catch (IllegalArgumentException e) {
            showPopup(e.getMessage());
        } catch (Exception e) {
            showPopup("Unexpected error: " + e.getMessage());
        }
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
            Error_date_label.setVisible(false);
            enter_date_txt_field.setStyle("-fx-text-fill: black;");
            return;
        }

        try {
            int day = Integer.parseInt(input);
            if (day < dayOfMonth || day > 31) {
                Error_date_label.setVisible(true);
                enter_date_txt_field.setStyle("-fx-text-fill: red;");
                Error_date_label.setText(dayOfMonth == 31? "31st is the last day of the month.":"Enter a valid date between " + dayOfMonth + " and 31.");
            } else {
                Error_date_label.setVisible(false);
                enter_day_number_label.setText(String.valueOf(day));
                enter_date_txt_field.setStyle("-fx-text-fill: black;");
                enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(day));
                events_on_enter_day_textArea.setText(mainController.getScheduler().displayEvents(day));
            }
        } catch (NumberFormatException e) {
            Error_date_label.setVisible(true);
            Error_date_label.setText("Enter a valid number.");
            enter_date_txt_field.setStyle("-fx-text-fill: red;");
        }
        checkName();
    }

    public void checkName() {
        String input = enter_event_name_txt_field.getText().toUpperCase().trim();
        enter_event_name_txt_field.setStyle("-fx-text-fill: red;");

        if (input.isEmpty()) {
            error_name_label.setVisible(false);
            enter_event_name_txt_field.setStyle("-fx-text-fill: black;");
            return;
        }

        try {
            if (!enter_date_txt_field.getText().isEmpty()) {
                int day = Integer.parseInt(enter_date_txt_field.getText());
                mainController.getScheduler().checkEventNameExists(day, input);
                error_name_label.setVisible(false);
            } else {
                error_name_label.setText(null);
            }
        } catch (NumberFormatException e) {
            error_name_label.setVisible(true);
            error_name_label.setText("Enter a valid date.");
        } catch (IllegalArgumentException e) {
            error_name_label.setVisible(true);
            error_name_label.setText("Event captured.");
            enter_event_name_txt_field.setStyle("-fx-text-fill: green;");
        }
    }
}
