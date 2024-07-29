package com.example.cld;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.Event;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Optional;

import static com.example.cld.Main.dayOfMonth;

public class AddEventController {

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

    @FXML
    private Label error_name_label;

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
        confirm_btm_addEvent.setOnAction(event_ -> {
            try {
                int dayToSchedule = Integer.parseInt(enter_date_txt_field.getText());

                // Validate the entered date
                if (dayToSchedule < dayOfMonth || dayToSchedule > 31) {
                    Error_date.setVisible(true);
                    throw new IllegalArgumentException("Enter a valid date between " + dayOfMonth + " and 31.");
                }

                if (enter_event_name_txt_field.getText().isEmpty()) {
                    throw new IllegalArgumentException("Enter a name for the event.");
                }

                if (enter_start_time_txt_field.getText().isEmpty()) {
                    throw new IllegalArgumentException("Enter a start times for the event.");
                }
                if(enter_end_time_txt_field.getText().isEmpty()) {
                    throw new IllegalArgumentException("Enter an end time for the event.");
                }

                if (!(daily_radio_btn.isSelected() || weekly_radio_btn.isSelected() || one_time_radio_btn.isSelected())) {
                    throw new IllegalArgumentException("Select a repeat type.");
                }

                if (enter_start_time_txt_field.getText().isEmpty() || enter_end_time_txt_field.getText().isEmpty()) {
                    throw new IllegalArgumentException("Enter start and end times for the event.");
                }

                if (mainController.getScheduler().days[dayToSchedule - 1].isDayOff()) {
                    Window owner = confirm_btm_addEvent.getScene().getWindow();

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
                    } else {
                        throw new IllegalArgumentException("The selected day is marked as a day off. Can not schedule.");
                    }
                }

                Error_date.setVisible(false); // Hide the error label if the date is valid

                String title = enter_event_name_txt_field.getText(); // done

                enter_day_number_label.setText(String.valueOf(dayToSchedule));
                enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayToSchedule));
                events_on_enter_day_textArea.setText(mainController.getScheduler().displayEvents(dayToSchedule));

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

                mainController.getScheduler().scheduleEvent(dayToSchedule, event);
                successPopup();
                clearInputFields();
                resetCheckBoxesAndRadioButtons();
                events_on_enter_day_textArea.setText(mainController.getScheduler().displayEvents(dayToSchedule));

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

          // Configure the ToggleGroup for repeatType
        ToggleGroup repeatTypeGroup = new ToggleGroup();
        one_time_radio_btn.setToggleGroup(repeatTypeGroup);
        daily_radio_btn.setToggleGroup(repeatTypeGroup);
        weekly_radio_btn.setToggleGroup(repeatTypeGroup);


        // Hide the Error_date label initially
        Error_date.setVisible(false);
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

    private void clearInputFields() {
        enter_date_txt_field.clear();
        enter_event_name_txt_field.clear();
        enter_start_time_txt_field.clear();
        enter_end_time_txt_field.clear();
        resetCheckBoxesAndRadioButtons();
        //events_on_enter_day_textArea.clear();
    }

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

    public void checkName() {
        String input = enter_event_name_txt_field.getText().trim();
        try{
            if (input.isEmpty()) {
                Error_date.setText("Enter a name for the event.");
            }else if(!input.isEmpty()){ // check for the name already exists on the day
                Error_date.setVisible(false);
                mainController.getScheduler().checkEventNameExists(Integer.parseInt(enter_date_txt_field.getText()), input);

            }else {
                Error_date.setVisible(false);
            }
        }catch (IllegalArgumentException e) {
            error_name_label.setText("Event name already exists");
        }
    }

}
