package com.example.cld;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ViewWeekController {

    @FXML
    private JFXButton btm_back;

    @FXML
    private JFXButton btm_date1;

    @FXML
    private JFXButton btm_date2;

    @FXML
    private JFXButton btm_date3;

    @FXML
    private JFXButton btm_date4;

    @FXML
    private JFXButton btm_date5;

    @FXML
    private JFXButton btm_toMonthView;

    @FXML
    private Label event_view_1_name_label;

    @FXML
    private Label event_view_1_number_label;

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
    private HBox root;

    @FXML
    private Pane today_pane;

    @FXML
    private Pane today_pane11;

    @FXML
    private Pane today_pane111;

    @FXML
    private Pane today_pane1111;

    @FXML
    private Pane today_pane112;

    @FXML
    private Pane today_pane1121;

    @FXML
    private Pane today_pane1122;

    @FXML
    private Pane today_pane1123;

    @FXML
    private Label total_events_number_label;

    private MainController mainController = MainController.getInstance();

    public void switchToMainMenu(javafx.event.Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToMainMenu(event);
    }

    public void switchToViewMonth(javafx.event.Event event) throws IOException { // switch to add the driver details scene
        mainController.switchToViewMonth(event);
    }



}
