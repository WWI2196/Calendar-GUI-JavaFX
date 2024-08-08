package com.example.cld;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.Event;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static com.example.cld.Main.dayOfMonth;

public class AddEventController {

    @FXML
    private JFXButton confirm_btm_addEvent;

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
    private JFXTextArea events_on_enter_day_textArea;

    @FXML
    private Label today_day_name_label;

    @FXML
    private Label today_day_number_label;

    @FXML
    private RadioButton one_time_radio_btn;

    @FXML
    private RadioButton daily_radio_btn;

    @FXML
    private RadioButton weekly_radio_btn;

    @FXML
    private Label Error_date;

    @FXML
    private Label error_name_label;

    private final MainController mainController = MainController.getInstance();

    public void switchToMainMenu(Event event) throws IOException {
        mainController.switchToMainMenu(event);
    }
    @FXML
    public void switchToAddEventDetails(Event event) throws IOException {
        mainController.switchToAddEventDetails(event);
    }
    @FXML
    public void switchToSetDayOff(Event event) throws IOException {
        mainController.switchToSetDayOff(event);
    }
    @FXML
    public void switchToDeleteEvent(Event event) throws IOException {
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
        Window owner = confirm_btm_addEvent.getScene().getWindow();
        MainController.AlertHelper.showAlert( // create the alert popup
            Alert.AlertType.INFORMATION,owner,
            "Schedule Event",
            "Success",
            "Event Successfully added",
            "/com/example/cld/Icons/Done_img_1.png",
            "/com/example/cld/Icons/addEvent.png"
        );
    }

    public void initialize() {
        confirm_btm_addEvent.setOnAction(this::handle);

        today_day_number_label.setText(String.valueOf(dayOfMonth));
        today_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayOfMonth));

        if(dayOfMonth != 31) {
            enter_date_txt_field.setPromptText(dayOfMonth + " - 31");
        }else {
            enter_date_txt_field.setText(String.valueOf(dayOfMonth));
        }

        ToggleGroup repeatTypeGroup = new ToggleGroup();
        one_time_radio_btn.setToggleGroup(repeatTypeGroup);
        daily_radio_btn.setToggleGroup(repeatTypeGroup);
        weekly_radio_btn.setToggleGroup(repeatTypeGroup);

        Error_date.setVisible(false);
    }

    private void showPopup(String message) {
        Window owner = confirm_btm_addEvent.getScene().getWindow();

        MainController.AlertHelper.showAlert( // call the alert
            Alert.AlertType.ERROR,owner,
            "Schedule Event",
            "Error",
            message,
            "/com/example/cld/Icons/CrossSign.png",
            "/com/example/cld/Icons/addEvent.png"
        );
    }

    private void clearInputFields() {
        enter_date_txt_field.clear();
        enter_event_name_txt_field.clear();
        enter_start_time_txt_field.clear();
        enter_end_time_txt_field.clear();
        resetCheckBoxesAndRadioButtons();
    }

    private void resetCheckBoxesAndRadioButtons() {
        one_time_radio_btn.setSelected(false);
        daily_radio_btn.setSelected(false);
        weekly_radio_btn.setSelected(false);
    }

    public void checkDate() {
        String input = enter_date_txt_field.getText().trim();
        enter_date_txt_field.setStyle("-fx-text-fill: black;");

        if (input.isEmpty()) {
            Error_date.setVisible(false);
            enter_date_txt_field.setStyle("-fx-text-fill: black;");
            return;
        }

        try {
            int day = Integer.parseInt(input);
            if (day < dayOfMonth || day > 31) {
                Error_date.setVisible(true);
                enter_date_txt_field.setStyle("-fx-text-fill: red;");
                Error_date.setText(dayOfMonth == 31? "31st is the last day of the month.":"Enter a valid date between " + dayOfMonth + " and 31.");
            } else {
                Error_date.setVisible(false);
                enter_day_number_label.setText(String.valueOf(day));
                enter_date_txt_field.setStyle("-fx-text-fill: black;");
                enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(day));
                events_on_enter_day_textArea.setText(mainController.getScheduler().displayEvents(day));
            }
        } catch (NumberFormatException e) {
            Error_date.setVisible(true);
            Error_date.setText("Enter a valid number.");
            enter_date_txt_field.setStyle("-fx-text-fill: red;");
        }

        checkName();
    }

    public void checkName() {
        String input = enter_event_name_txt_field.getText().toUpperCase().trim();
        enter_event_name_txt_field.setStyle("-fx-text-fill: black;");

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
            error_name_label.setText(e.getMessage());
            enter_event_name_txt_field.setStyle("-fx-text-fill: red;");
        }
    }

    private void enterDayDetailsClear() {
        enter_day_number_label.setText(null);
        enter_day_name_label.setText(null);
        events_on_enter_day_textArea.setText(null);
    }


    private void handle(ActionEvent actionEvent) {
        try {
            String enterDateText = enter_date_txt_field.getText();
            String eventName = enter_event_name_txt_field.getText();
            String startTime = enter_start_time_txt_field.getText();
            String endTime = enter_end_time_txt_field.getText();

            validateInput(enterDateText, eventName, startTime, endTime);

            int dayToSchedule = Integer.parseInt(enterDateText);

            validateDate(dayToSchedule);

            if (mainController.getScheduler().days[dayToSchedule - 1].isDayOff()) {
                if (!confirmProceedWithDayOff()) {
                    enterDayDetailsClear();
                    throw new IllegalArgumentException("The selected day is marked as a day off. Cannot schedule.");
                } else {
                    mainController.getScheduler().days[dayToSchedule - 1].setDayOff(false);
                }
            }

            validateRepeatType();

            Error_date.setVisible(false);

            com.example.cld.Event event = getEvent(eventName);

            mainController.getScheduler().scheduleEvent(dayToSchedule, event);

            updateText(dayToSchedule);
            successPopup();
            clearInputFields();
            resetCheckBoxesAndRadioButtons();

        } catch (NumberFormatException e) {
            showPopup("Enter a valid number.");
        } catch (Exception e) {
            showPopup(e.getMessage());
        }
    }

    private void validateInput(String enterDateText, String eventName, String startTime, String endTime) {
        if (enterDateText.isEmpty()) {
            throw new IllegalArgumentException("Enter a date to schedule the event.");
        }
        if (eventName.isEmpty()) {
            throw new IllegalArgumentException("Enter a name for the event.");
        }
        if (startTime.isEmpty()) {
            throw new IllegalArgumentException("Enter a start time for the event.");
        }
        if (endTime.isEmpty()) {
            throw new IllegalArgumentException("Enter an end time for the event.");
        }
    }

    private void validateDate(int dayToSchedule) {
        if (dayToSchedule < dayOfMonth || dayToSchedule > 31) {
            Error_date.setVisible(true);
            throw new IllegalArgumentException(dayOfMonth == 31 ? "31st is the last day of the month." : "Enter a valid date between " + dayOfMonth + " and 31.");
        }
    }

    private boolean confirmProceedWithDayOff() {
        Window owner = confirm_btm_addEvent.getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Schedule Event");
        alert.setHeaderText("Confirmation");
        alert.setContentText("The selected day is marked as a day off. Do you want to proceed?");
        alert.initOwner(owner);

        Image alertImage = new Image(Objects.requireNonNull(MainController.AlertHelper.class.getResourceAsStream("/com/example/cld/Icons/DayOff_1_1.png")));
        ImageView alertImageView = new ImageView(alertImage);
        alertImageView.setFitWidth(40);
        alertImageView.setFitHeight(40);
        alert.setGraphic(alertImageView);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image windowIcon = new Image(Objects.requireNonNull(MainController.AlertHelper.class.getResourceAsStream("/com/example/cld/Icons/addEvent.png")));
        stage.getIcons().clear();
        stage.getIcons().add(windowIcon);

        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == ButtonType.YES;
    }

    private void validateRepeatType() {
        if (!(daily_radio_btn.isSelected() || weekly_radio_btn.isSelected() || one_time_radio_btn.isSelected())) {
            throw new IllegalArgumentException("Select a repeat type.");
        }
    }

    private void updateText(int dayToSchedule) {
        enter_day_number_label.setText(String.valueOf(dayToSchedule));
        enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayToSchedule));
        events_on_enter_day_textArea.setText(mainController.getScheduler().displayEvents(dayToSchedule));
    }

    private com.example.cld.Event getEvent(String title) {
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

        return new com.example.cld.Event(title,startTime,endTime,repeatType);
    }
}