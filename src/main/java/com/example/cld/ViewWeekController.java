package com.example.cld;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.lang.reflect.Field;

import static com.example.cld.Main.dayOfMonth;

public class ViewWeekController {

    @FXML
    private JFXButton btm_week1;

    @FXML
    private JFXButton btm_week2;

    @FXML
    private JFXButton btm_week3;

    @FXML
    private JFXButton btm_week4;

    @FXML
    private JFXButton btm_week5;

    @FXML
    private Label event_view_1_name_label;

    @FXML
    private Label event_view_1_number_label;

    @FXML
    private Label event_view_2_name_label;

    @FXML
    private Label event_view_2_number_label;

    @FXML
    private Label event_view_3_name_label;

    @FXML
    private Label event_view_3_number_label;

    @FXML
    private Label event_view_4_name_label;

    @FXML
    private Label event_view_4_number_label;

    @FXML
    private Label event_view_5_name_label;

    @FXML
    private Label event_view_5_number_label;

    @FXML
    private Label event_view_6_name_label;

    @FXML
    private Label event_view_6_number_label;

    @FXML
    private Label event_view_7_name_label;

    @FXML
    private Label event_view_7_number_label;

    @FXML
    private Label events_on_label;

    @FXML
    private Label events_on_label1;

    @FXML
    private JFXTextArea events_view_1_textArea;

    @FXML
    private JFXTextArea events_view_2_textArea;

    @FXML
    private JFXTextArea events_view_3_textArea;

    @FXML
    private JFXTextArea events_view_4_textArea;

    @FXML
    private JFXTextArea events_view_5_textArea;

    @FXML
    private JFXTextArea events_view_6_textArea;

    @FXML
    private JFXTextArea events_view_7_textArea;

    @FXML
    private Pane today_pane1;

    @FXML
    private Pane today_pane2;

    @FXML
    private Pane today_pane3;

    @FXML
    private Pane today_pane4;

    @FXML
    private Pane today_pane5;

    @FXML
    private Pane today_pane6;

    @FXML
    private Pane today_pane7;

    @FXML
    private Label total_events_number_label;

    private final MainController mainController = MainController.getInstance();

    public void switchToMainMenu(javafx.event.Event event) throws IOException {
        mainController.switchToMainMenu(event);
    }

    public void switchToViewMonth(javafx.event.Event event) throws IOException {
        mainController.switchToViewMonth(event);
    }

   public void initialize() {
       handle(dayOfMonth);
       setupDateButtonActions();
   }

   private void handle(int startIndex) {
       int endIndex;

       if (startIndex <= 6) {
           startIndex = 1; // starting day for the first week
           endIndex = 6; // End day for the first week
       } else if (startIndex >= 28) {
           startIndex = 28; // starting day for the fifth week
           endIndex = 31; // end day for the fifth week
       } else {
           startIndex = ((startIndex) / 7) * 7;
           endIndex = Math.min(startIndex + 6, 31);
       }

       Label[] nameLabels = {event_view_1_name_label, event_view_2_name_label, event_view_3_name_label, event_view_4_name_label, event_view_5_name_label, event_view_6_name_label, event_view_7_name_label};
       Label[] numberLabels = {event_view_1_number_label, event_view_2_number_label, event_view_3_number_label, event_view_4_number_label, event_view_5_number_label, event_view_6_number_label, event_view_7_number_label};
       TextArea[] textAreas = {events_view_1_textArea, events_view_2_textArea, events_view_3_textArea, events_view_4_textArea, events_view_5_textArea, events_view_6_textArea, events_view_7_textArea};
       Pane[] todayPanes = {today_pane1, today_pane2, today_pane3, today_pane4, today_pane5, today_pane6, today_pane7};

       for (int i = 0; i < 7; i++) {
           int currentIndex = startIndex + i;
           boolean isVisible = currentIndex <= endIndex;

           numberLabels[i].setText(isVisible ? String.valueOf(currentIndex) : "");
           nameLabels[i].setText(isVisible ? DateNameMain.getDayAbbreviationAb(currentIndex) : "");
           textAreas[i].setText(isVisible ? MainController.scheduler.displayEvents(currentIndex) : "");

           nameLabels[i].setVisible(isVisible);
           numberLabels[i].setVisible(isVisible);
           textAreas[i].setVisible(isVisible);
           todayPanes[i].setVisible(isVisible);
       }

       total_events_number_label.setText(String.valueOf(MainController.scheduler.countEvents(startIndex, endIndex)));
   }

    private void handleDisplayEvents(ActionEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        int weekNumber = Integer.parseInt(button.getId().replaceAll("\\D+", "")); // get the week number from the button ID
        int startIndex = (weekNumber - 1) * 7 + 1;
        handle(startIndex);
    }

    private void setupDateButtonActions() {
        for (int i = 1; i <= 5; i++) {
            try {
                Field field = getClass().getDeclaredField("btm_week" + i);
                field.setAccessible(true);
                JFXButton button = (JFXButton) field.get(this);
                if (button != null) {
                    button.setOnAction(this::handleDisplayEvents);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}