package com.example.cld;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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

public class ShiftEventController {

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
    private JFXButton btm_shiftEvent1;

    @FXML
    private JFXButton btm_shiftEvent11;

    @FXML
    private JFXButton confirm_btm_shiftEvent;

    @FXML
    private TextField enter_date_txt_field;

    @FXML
    private Label enter_day_name_label;

    @FXML
    private Label enter_day_number_label;

    @FXML
    private TextField enter_event_name_txt_field;

    @FXML
    private TextField enter_new_date_txt_field;

    @FXML
    private Label error_enter_date_label;

    @FXML
    private Label error_name_label;

    @FXML
    private Label error_shift_date_label;

    @FXML
    private JFXTextArea events_on_enter_day_textArea;

    @FXML
    private JFXTextArea events_on_shift_day_textArea;

    @FXML
    private Label shift_day_name_label;

    @FXML
    private Label shift_day_number_label;

    @FXML
    private Label today_day_name_label;

    @FXML
    private Label today_day_number_label;

    private final MainController mainController = MainController.getInstance();

    public void switchToMainMenu(javafx.event.Event event) throws IOException {
        mainController.switchToMainMenu(event);
    }
    @FXML
    public void switchToAddEventDetails(javafx.event.Event event) throws IOException {
        mainController.switchToAddEventDetails(event);
    }
    @FXML
    public void switchToSetDayOff(javafx.event.Event event) throws IOException {
        mainController.switchToSetDayOff(event);
    }
    @FXML
    public void switchToDeleteEvent(javafx.event.Event event) throws IOException {
        mainController.switchToDeleteEvent(event);
    }
    @FXML
    public void switchToShiftEvent(Event event) throws IOException {
        mainController.switchToShiftEvent(event);
    }
    @FXML
    public void switchToViewWeek(Event event) throws IOException {
        mainController.switchToViewWeek(event);
    }
    @FXML
    public void switchToViewMonth(Event event) throws IOException {
        mainController.switchToViewMonth(event);
    }

    @FXML
    private void successPopup() {
        Window owner = confirm_btm_shiftEvent.getScene().getWindow();
        MainController.AlertHelper.showAlert(
                Alert.AlertType.INFORMATION,owner,
                "Shift Event",
                "Success",
                "Event Successfully shifted",
                "/com/example/cld/Icons/Done_img_1.png",
                "/com/example/cld/Icons/shiftEvent.png"
        );
    }

    public void initialize() {
        confirm_btm_shiftEvent.setOnAction(this::handle);

        today_day_number_label.setText(String.valueOf(dayOfMonth));
        today_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayOfMonth));

        if(dayOfMonth != 31) {
            enter_date_txt_field.setPromptText(dayOfMonth + " - 31");
            enter_new_date_txt_field.setPromptText(dayOfMonth + " - 31");
        }else {
            enter_date_txt_field.setText(String.valueOf(dayOfMonth));
            enter_new_date_txt_field.setText(String.valueOf(dayOfMonth));
        }

        error_enter_date_label.setVisible(false);
        error_shift_date_label.setVisible(false);
    }

    private void handle(ActionEvent event) {
        try {
            String enterDateText = enter_date_txt_field.getText();
            String enterNewDateText = enter_new_date_txt_field.getText();
            String eventName = enter_event_name_txt_field.getText();

            validateInput(enterDateText, enterNewDateText, eventName);

            int eventBeforeShift = Integer.parseInt(enterDateText);
            int eventAfterShift = Integer.parseInt(enterNewDateText);

            validateDates(eventBeforeShift, eventAfterShift);

            if (!mainController.getScheduler().isEventNameExists(eventBeforeShift, eventName)) {
                throw new IllegalArgumentException("Event not found.");
            }

            if (mainController.getScheduler().days[eventAfterShift - 1].isDayOff()) {
                if (!confirmDayOffShift()) {
                    throw new IllegalArgumentException("The selected day is marked as a day off. Cannot shift.");
                }
                mainController.getScheduler().days[eventAfterShift - 1].setDayOff(false);
            }

            mainController.getScheduler().shiftEvent(eventBeforeShift, eventName, eventAfterShift);
            updateText(eventBeforeShift, eventAfterShift);
            successPopup();
            clearInputFields();

        } catch (NumberFormatException e) {
            showPopup("Enter a valid number.");
        } catch (Exception e) {
            showPopup(e.getMessage());
        }
    }

    private void validateInput(String enterDateText, String enterNewDateText, String eventName) {
        if (enterDateText.isEmpty()) {
            throw new IllegalArgumentException("Enter a date to set day off.");
        }
        if (enterNewDateText.isEmpty()) {
            throw new IllegalArgumentException("Enter a date to shift the event.");
        }
        if (eventName.isEmpty()) {
            throw new IllegalArgumentException("Enter a name for the event.");
        }
    }

    private void validateDates(int eventBeforeShift, int eventAfterShift) {
        if (eventBeforeShift < dayOfMonth || eventBeforeShift > 31) {
            error_enter_date_label.setVisible(true);
            throw new IllegalArgumentException(dayOfMonth == 31 ? "31st is the last day of the month." : "Enter a valid date between " + dayOfMonth + " and 31.");
        }
        if (eventAfterShift < dayOfMonth || eventAfterShift > 31) {
            error_shift_date_label.setVisible(true);
            throw new IllegalArgumentException(dayOfMonth == 31 ? "31st is the last day of the month." : "Enter a valid date between " + dayOfMonth + " and 31.");
        }
        error_enter_date_label.setVisible(false);
        error_shift_date_label.setVisible(false);
    }

    private boolean confirmDayOffShift() {
        Window owner = confirm_btm_shiftEvent.getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Shift Event");
        alert.setHeaderText("Confirmation");
        alert.setContentText("The selected day is marked as a day off. Do you want to proceed?");
        alert.initOwner(owner);

        Image alertImage = new Image(Objects.requireNonNull(MainController.AlertHelper.class.getResourceAsStream("/com/example/cld/Icons/DayOff_1_1.png")));
        ImageView alertImageView = new ImageView(alertImage);
        alertImageView.setFitWidth(40);
        alertImageView.setFitHeight(40);
        alert.setGraphic(alertImageView);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image windowIcon = new Image(Objects.requireNonNull(MainController.AlertHelper.class.getResourceAsStream("/com/example/cld/Icons/shiftEvent.png")));
        stage.getIcons().clear();
        stage.getIcons().add(windowIcon);

        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == ButtonType.YES;
    }

    private void updateText(int eventBeforeShift, int eventAfterShift) {
        enter_day_number_label.setText(String.valueOf(eventBeforeShift));
        enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(eventBeforeShift));
        events_on_enter_day_textArea.setText(mainController.getScheduler().displayEvents(eventBeforeShift));

        shift_day_number_label.setText(String.valueOf(eventAfterShift));
        shift_day_name_label.setText(DateNameMain.getDayAbbreviationAb(eventAfterShift));
        events_on_shift_day_textArea.setText(mainController.getScheduler().displayEvents(eventAfterShift));
    }


    private void showPopup(String message) {
        Window owner = confirm_btm_shiftEvent.getScene().getWindow();
        MainController.AlertHelper.showAlert(
                Alert.AlertType.ERROR,owner,
                "Shift Event",
                "Error",
                message,
                "/com/example/cld/Icons/CrossSign.png",
                "/com/example/cld/Icons/shiftEvent.png"
        );
    }

    private void clearInputFields() {

        enter_date_txt_field.clear();
        enter_event_name_txt_field.clear();
        error_name_label.setVisible(false);
        enter_new_date_txt_field.clear();
    }

    public void checkDate() {
        dateValidation(enter_date_txt_field.getText().trim(),
                error_enter_date_label,
                enter_day_number_label,
                enter_day_name_label,
                events_on_enter_day_textArea,
                enter_date_txt_field);
    }

    public void checkDate_1() {
        dateValidation(enter_new_date_txt_field.getText().trim(),
                             error_shift_date_label,
                             shift_day_number_label,
                             shift_day_name_label,
                             events_on_shift_day_textArea,
                             enter_new_date_txt_field);
    }

    private void dateValidation(String input,
                                      Label errorLabel,
                                      Label dayNumberLabel,
                                      Label dayNameLabel,
                                      TextArea eventsTextArea,
                                TextField enter_date_txt_field) {
        if (input.isEmpty()) {
            errorLabel.setVisible(false);
            enter_date_txt_field.setStyle("-fx-text-fill: black;");
            return;
        }

        try {
            int day = Integer.parseInt(input);
            if (day < dayOfMonth || day > 31) {
                errorLabel.setVisible(true);
                enter_date_txt_field.setStyle("-fx-text-fill: red;");
                errorLabel.setText(dayOfMonth == 31 ? "31st is the last day of the month." : "Enter a valid date between " + dayOfMonth + " and 31.");
            } else {
                errorLabel.setVisible(false);
                dayNumberLabel.setText(String.valueOf(day));
                enter_date_txt_field.setStyle("-fx-text-fill: black;");
                dayNameLabel.setText(DateNameMain.getDayAbbreviationAb(day));
                eventsTextArea.setText(mainController.getScheduler().displayEvents(day));
            }
        } catch (NumberFormatException e) {
            errorLabel.setVisible(true);
            enter_date_txt_field.setStyle("-fx-text-fill: red;");
            errorLabel.setText("Enter a valid number.");
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
            enter_event_name_txt_field.setStyle("-fx-text-fill: green;");
        }
    }

}