package com.example.cld;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Optional;

import static com.example.cld.Main.dayOfMonth;

public class SetDayOffController {

    @FXML
    private Label Error_date;

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
  
    private JFXButton confirm_btm_DeleteEvent;

    @FXML
    private JFXButton btm_viewWeek;

    @FXML
    private JFXButton confirm_btm_DeleteEvent;

    @FXML
    private TextField enter_date_txt_field;

    @FXML
    private Label enter_day_name_label;

    @FXML
    private Label enter_day_number_label;

    @FXML

    private Pane enter_today_pane;

    @FXML

    private JFXTextArea events_on_enter_day_day_off_textArea;

    private Label events_on_enter_day_label;

    @FXML
    private Label Error_date;

    @FXML
    private ImageView inner_pane_image1;


    @FXML
    private Label today_day_name_label;

    @FXML
    private Label today_day_number_label;

    @FXML
    private Pane today_pane;

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
    public void switchToViewWeek(Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToViewWeek(event);
    }
    @FXML
    public void switchToViewMonth(Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToViewMonth(event);
    }

    @FXML

    private void Error() {
        Window owner = confirm_btm_DeleteEvent.getScene().getWindow();
        // Create the alert
        MainController.AlertHelper.showAlert(
                Alert.AlertType.INFORMATION,owner,
                "Set Dayoff",
                "Error",
                "There is a problem with entered values; check whether the entered values are in correct format",
                "/com/example/cld/Icons/CrossSign.png",
                "/com/example/cld/Icons/addEvent.png"
        );
    }

    @FXML

    private void Error() {
        Window owner = confirm_btm_DeleteEvent.getScene().getWindow();
        // Create the alert
        MainController.AlertHelper.showAlert(
                Alert.AlertType.INFORMATION,owner,
                "Set Dayoff",
                "Error",
                "There is a problem with entered values; check whether the entered values are in correct format",
                "/com/example/cld/Icons/CrossSign.png",
                "/com/example/cld/Icons/addEvent.png"
        );
    }

    @FXML

    private void successPopup() {
        Window owner = confirm_btm_DeleteEvent.getScene().getWindow();
        // Create the alert
        MainController.AlertHelper.showAlert(
                Alert.AlertType.INFORMATION,owner,

                "Set Day Off",

                "Set Dayoff",

                "Success",
                "Day successfully set as Day Off",
                "/com/example/cld/Icons/Done_img_1.png",
                "/com/example/cld/Icons/dayOff.png"
        );
    }

    public void initialize() {

        confirm_btm_DeleteEvent.setOnAction(this::handleDayOff);

        confirm_btm_DeleteEvent.setOnAction(event_ -> {
            try {
                int dayToSetDayoff = Integer.parseInt(enter_date_txt_field.getText());

                // Validate the entered date
                if (dayToSetDayoff < dayOfMonth || dayToSetDayoff> 31) {
                    Error_date.setVisible(true);
                    throw new IllegalArgumentException(dayOfMonth == 31? "31st is the last day of the month.":"Enter a valid date between " + dayOfMonth + " and 31.");
                }

                if (mainController.getScheduler().days[dayToSetDayoff - 1].isDayOff()) {
                    Window owner = confirm_btm_DeleteEvent.getScene().getWindow();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                    alert.setTitle("Set Day Off");
                    alert.setHeaderText("Confirmation");
                    alert.setContentText("The selected day is already marked as a day off. Do you want to keep it as a day off?");

                    alert.setTitle("Set Dayoff");
                    alert.setHeaderText("Confirmation");
                    alert.setContentText("The selected day is already marked as a dayoff. Do you want to Keep as it is or remove Dayoff");

                    alert.initOwner(owner);

                    // Load and set the alert's display icon
                    Image alertImage = new Image(MainController.AlertHelper.class.getResourceAsStream("/com/example/cld/Icons/DayOff_1_1.png"));
                    ImageView alertImageView = new ImageView(alertImage);
                    alertImageView.setFitWidth(40); // Set desired width
                    alertImageView.setFitHeight(40); // Set desired height
                    alert.setGraphic(alertImageView);


                    ButtonType keepButton =new ButtonType("KEEP");
                    ButtonType removeButton =new ButtonType("REMOVE");
                    alert.getButtonTypes().setAll(keepButton, removeButton);

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == keepButton) {
                        mainController.getScheduler().days[dayToSetDayoff - 1].setDayOff(true);
                        //events_on_enter_day_label.setText(mainController.getScheduler().displayEvents(dayToSchedule));
                    } else {
                        mainController.getScheduler().days[dayToSetDayoff - 1].setDayOff(false);
                        //events_on_enter_day_label.setText(mainController.getScheduler().displayEvents(dayToSetDayoff));
                        //throw new IllegalArgumentException("The selected day is marked as a day off. Can not schedule.");
                    }
                }

                Error_date.setVisible(false); // Hide the error label if the date is valid


                enter_day_number_label.setText(String.valueOf(dayToSetDayoff));
                enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayToSetDayoff));
                events_on_enter_day_label.setText(mainController.getScheduler().displayEvents(dayToSetDayoff));

                mainController.getScheduler().markDayOff(dayToSetDayoff);

                successPopup();
                clearInputFields();

                events_on_enter_day_label.setText(mainController.getScheduler().displayEvents(dayToSetDayoff));

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
        Window owner = confirm_btm_DeleteEvent.getScene().getWindow();
        // Create the alert
        MainController.AlertHelper.showAlert(
                Alert.AlertType.ERROR,owner,
                "Set Dayoff",
                "Error",
                message,
                "/com/example/cld/Icons/CrossSign.png",
                "/com/example/cld/Icons/dayOff.png"
        );
    }

    private void clearInputFields() {
        enter_date_txt_field.clear();
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

        //checkName();
    }
    /*
    public void checkName() {
        String input = enter_event_name_txt_field.getText().toUpperCase().trim();

        if (input.isEmpty()) {
            error_name_label.setVisible(false);
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
        }
    }
    */

                    ButtonType keepButton =new ButtonType("Keep");
                    ButtonType removeButton =new ButtonType("Remove");
                    alert.getButtonTypes().setAll(keepButton, removeButton);

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == keepButton) {
                        successPopup();
                        return;
                    } else {
                        mainController.getScheduler().days[dayToSetDayoff - 1].setDayOff(false);
                    }
                }

                Error_date.setVisible(false); // Hide the error label if the date is valid


                enter_day_number_label.setText(String.valueOf(dayToSetDayoff));
                enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayToSetDayoff));
                events_on_enter_day_day_off_textArea.setText(mainController.getScheduler().displayEvents(dayToSetDayoff));

                mainController.getScheduler().markDayOff(dayToSetDayoff);

                successPopup();
                clearInputFields();

                events_on_enter_day_day_off_textArea.setText(mainController.getScheduler().displayEvents(dayToSetDayoff));

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
        Window owner = confirm_btm_DeleteEvent.getScene().getWindow();
        // Create the alert
        MainController.AlertHelper.showAlert(
                Alert.AlertType.ERROR,owner,
                "Set Day Off",
                "Error",
                message,
                "/com/example/cld/Icons/CrossSign.png",
                "/com/example/cld/Icons/dayOff.png"
        );
    }

    private void dayOffRemovedSuccess(String message, String title) {
        Window owner = confirm_btm_DeleteEvent.getScene().getWindow();
        // Create the alert
        MainController.AlertHelper.showAlert(
                Alert.AlertType.INFORMATION,owner,
                "Set Day Off",
                title,
                message,
                "/com/example/cld/Icons/dayOff_removed.png",
                "/com/example/cld/Icons/dayOff.png"
        );
    }

    private void clearInputFields() {
        enter_date_txt_field.clear();
    }

    public void checkDate() {
        String input = enter_date_txt_field.getText().trim();

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
                events_on_enter_day_day_off_textArea.setText(mainController.getScheduler().displayEvents(day));
            }
        } catch (NumberFormatException e) {
            Error_date.setVisible(true);
            enter_date_txt_field.setStyle("-fx-text-fill: red;");
            Error_date.setText("Enter a valid number.");
        }
        //checkName();
    }

    private void handleDayOff(Event event) {
        try {
            if (enter_date_txt_field.getText().isEmpty()) {
                throw new IllegalArgumentException("Enter a date to set day off.");
            }

            int dayToSetDayoff = Integer.parseInt(enter_date_txt_field.getText());

            // Validate the entered date
            if (dayToSetDayoff < dayOfMonth || dayToSetDayoff > 31) {
                Error_date.setVisible(true);
                throw new IllegalArgumentException(dayOfMonth == 31 ? "31st is the last day of the month." : "Enter a valid date between " + dayOfMonth + " and 31.");
            }

            if (mainController.getScheduler().days[dayToSetDayoff - 1].isDayOff()) {
                Window owner = confirm_btm_DeleteEvent.getScene().getWindow();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Set Day Off");
                alert.setHeaderText("Confirmation");
                alert.setContentText("The selected day is already marked as a day off. Do you want to keep it as a day off?");
                alert.initOwner(owner);

                // Load and set the alert's display icon
                Image alertImage = new Image(MainController.AlertHelper.class.getResourceAsStream("/com/example/cld/Icons/DayOff_1_1.png"));
                ImageView alertImageView = new ImageView(alertImage);
                alertImageView.setFitWidth(40); // Set desired width
                alertImageView.setFitHeight(40); // Set desired height
                alert.setGraphic(alertImageView);

                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                Image windowIcon = new Image(MainController.AlertHelper.class.getResourceAsStream("/com/example/cld/Icons/dayOff.png"));
                stage.getIcons().clear(); // Clear existing icons
                stage.getIcons().add(windowIcon);

                ButtonType keepButton = new ButtonType("Keep");
                ButtonType removeButton = new ButtonType("Remove");
                alert.getButtonTypes().setAll(keepButton, removeButton);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == keepButton) {
                    successPopup();
                    return;
                } else {
                    mainController.getScheduler().removeDayOff(dayToSetDayoff);
                    dayOffRemovedSuccess("Day off removed successfully.", "Success");
                    clearInputFields();
                    events_on_enter_day_day_off_textArea.setText(mainController.getScheduler().displayEvents(dayToSetDayoff));
                    return;
                }
            }

            Error_date.setVisible(false); // Hide the error label if the date is valid


            enter_day_number_label.setText(String.valueOf(dayToSetDayoff));
            enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayToSetDayoff));
            events_on_enter_day_day_off_textArea.setText(mainController.getScheduler().displayEvents(dayToSetDayoff));

            mainController.getScheduler().markDayOff(dayToSetDayoff);

            successPopup();
            clearInputFields();

            events_on_enter_day_day_off_textArea.setText(mainController.getScheduler().displayEvents(dayToSetDayoff));

        } catch (NumberFormatException e) {
            showPopup("Enter a valid number.");
        } catch (IllegalArgumentException e) {
            showPopup(e.getMessage());
        } catch (Exception e) {
            showPopup(e.getMessage());
        }
    }
}