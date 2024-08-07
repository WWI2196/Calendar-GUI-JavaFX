package com.example.cld;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;


import java.io.IOException;
import java.lang.reflect.Field;

public class ViewMonthController {

     @FXML
    private JFXTextArea events_day1;

    @FXML
    private JFXTextArea events_day10;

    @FXML
    private JFXTextArea events_day11;

    @FXML
    private JFXTextArea events_day12;

    @FXML
    private JFXTextArea events_day13;

    @FXML
    private JFXTextArea events_day14;

    @FXML
    private JFXTextArea events_day15;

    @FXML
    private JFXTextArea events_day16;

    @FXML
    private JFXTextArea events_day17;

    @FXML
    private JFXTextArea events_day18;

    @FXML
    private JFXTextArea events_day19;

    @FXML
    private JFXTextArea events_day2;

    @FXML
    private JFXTextArea events_day20;

    @FXML
    private JFXTextArea events_day21;

    @FXML
    private JFXTextArea events_day22;

    @FXML
    private JFXTextArea events_day23;

    @FXML
    private JFXTextArea events_day24;

    @FXML
    private JFXTextArea events_day25;

    @FXML
    private JFXTextArea events_day26;

    @FXML
    private JFXTextArea events_day27;

    @FXML
    private JFXTextArea events_day28;

    @FXML
    private JFXTextArea events_day29;

    @FXML
    private JFXTextArea events_day3;

    @FXML
    private JFXTextArea events_day30;

    @FXML
    private JFXTextArea events_day31;

    @FXML
    private JFXTextArea events_day4;

    @FXML
    private JFXTextArea events_day5;

    @FXML
    private JFXTextArea events_day6;

    @FXML
    private JFXTextArea events_day7;

    @FXML
    private JFXTextArea events_day8;

    @FXML
    private JFXTextArea events_day9;

     @FXML
    private Label total_events_number_label;


    private final MainController mainController = MainController.getInstance();

    public void switchToMainMenu(Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToMainMenu(event);
    }
     public void switchToViewWeek(Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToViewWeek(event);
    }

    private void showPopup(String message) {
        // Create the alert
        MainController.AlertHelper.showAlert(
            Alert.AlertType.ERROR, null,
            "Schedule Event",
            "Error",
            message,
            "/com/example/cld/Icons/CrossSign.png",
            "/com/example/cld/Icons/addEvent.png"
        );
    }

    public void initialize() {
        setupDateAction();
        updateTotalEvents();
    }

    private void updateTotalEvents() {
        total_events_number_label.setText(String.valueOf(mainController.getScheduler().countEvents(1,31)));
    }

    private void setupDateAction() {
        try {
            for (int i = 1; i <= 31; i++) {
                String textFieldName = "events_day" + i;

                // Use reflection to get the field
                Field field = this.getClass().getDeclaredField(textFieldName);
                field.setAccessible(true);

                TextArea textArea = (TextArea) field.get(this);

                // Call displayEventsForMonths() method with the textArea (assuming displayEventsForMonths accepts a TextArea)
                textArea.setText(mainController.getScheduler().displayEventsForMonths(i));
            }
        } catch (Exception e) {
            showPopup(e.getMessage());
        }
    }

}