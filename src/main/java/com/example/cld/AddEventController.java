package com.example.cld;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.event.Event;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;

import java.io.IOException;
import java.util.Objects;

import static com.example.cld.Main.dayOfMonth;

public class AddEventController {

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
    private JFXButton check_button;

    @FXML
    private JFXButton confirm_btm_addEvent;

    @FXML
    private CheckBox daily_check_box;

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
    private CheckBox one_time_check_box;

    @FXML
    private HBox root;

    @FXML
    private Label today_day_name_label;

    @FXML
    private Label today_day_number_label;

    @FXML
    private Pane today_pane;

    @FXML
    private CheckBox weekly_check_box;


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

    public void initialize() {
        today_day_number_label.setText(String.valueOf(dayOfMonth));
        today_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayOfMonth));


    }

}
