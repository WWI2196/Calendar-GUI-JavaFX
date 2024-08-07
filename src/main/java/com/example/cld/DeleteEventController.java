
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

    private void handle(ActionEvent actionEvent) {
        try {
            String enterDateText = enter_date_txt_field.getText();
            String eventName = enter_event_name_txt_field.getText();

            validateInput(enterDateText, eventName);

            int dayToDelete = Integer.parseInt(enterDateText);

            validateDate(dayToDelete);

            if (!mainController.getScheduler().isEventNameExists(dayToDelete, eventName)) {
                throw new IllegalArgumentException("Event not found.");
            }

            boolean deleteRepeats = confirmDeleteRepeats(dayToDelete, eventName);

            Error_date_label.setVisible(false);

            mainController.getScheduler().deleteEvent(dayToDelete, eventName, deleteRepeats, mainController.getScheduler().getEventRepeatType(dayToDelete, eventName));
            updateText(dayToDelete);

            successPopup();
            clearInputFields();
            events_on_enter_day_textArea.setText(mainController.getScheduler().displayEvents(dayToDelete));
        } catch (NumberFormatException e) {
            showPopup("Invalid date format.");
        } catch (Exception e) {
            showPopup(e.getMessage());
        }
    }

    private void validateInput(String enterDateText, String eventName) {
        if (enterDateText.isEmpty()) {
            throw new IllegalArgumentException("Enter a date to select.");
        }
        if (eventName.trim().isEmpty()) {
            throw new IllegalArgumentException("Enter the name of the event.");
        }
    }

    private void validateDate(int dayToDelete) {
        if (dayToDelete < dayOfMonth || dayToDelete > 31) {
            Error_date_label.setVisible(true);
            throw new IllegalArgumentException(dayOfMonth == 31 ? "31st is the last day of the month." : "Enter a valid date between " + dayOfMonth + " and 31.");
        }
    }

    private boolean confirmDeleteRepeats(int dayToDelete, String eventName) {
        String repeatType = mainController.getScheduler().getEventRepeatType(dayToDelete, eventName);
        if (repeatType.equals("none")) {
            return false;
        }

        Alert alert = createDeleteConfirmationAlert(repeatType);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }

    private Alert createDeleteConfirmationAlert(String repeatType) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Event");
        alert.setHeaderText("Confirmation");
        alert.setContentText("The selected event is a " + repeatType + " event. Do you want to delete all occurrences?");

        Image alertImage = new Image(Objects.requireNonNull(MainController.AlertHelper.class.getResourceAsStream("/com/example/cld/Icons/event_repeat.png")));
        ImageView alertImageView = new ImageView(alertImage);
        alertImageView.setFitWidth(40);
        alertImageView.setFitHeight(40);
        alert.setGraphic(alertImageView);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image windowIcon = new Image(Objects.requireNonNull(MainController.AlertHelper.class.getResourceAsStream("/com/example/cld/Icons/deleteEvent.png")));
        stage.getIcons().clear();
        stage.getIcons().add(windowIcon);

        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        return alert;
    }

    private void updateText(int dayToDelete) {
        enter_day_number_label.setText(String.valueOf(dayToDelete));
        enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayToDelete));
        events_on_enter_day_textArea.setText(mainController.getScheduler().displayEvents(dayToDelete));
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