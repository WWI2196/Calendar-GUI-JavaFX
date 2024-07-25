package com.example.cld;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ShiftEventController {

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
    private JFXButton check_button_new_date;

    @FXML
    private JFXButton confirm_btm_shiftEvent;

    @FXML
    private Pane date_picker;

    @FXML
    private TextField enter_date_txt_field;

    @FXML
    private Label enter_day_name_label;

    @FXML
    private Label enter_day_name_label1;

    @FXML
    private Label enter_day_number_label;

    @FXML
    private Label enter_day_number_label1;

    @FXML
    private TextField enter_event_name_txt_field;

    @FXML
    private TextField enter_new_date_txt_field;

    @FXML
    private Pane enter_today_pane;

    @FXML
    private Pane enter_today_pane1;

    @FXML
    private Label events_on_enter_day_label;

    @FXML
    private Label events_on_enter_day_label1;

    @FXML
    private ImageView inner_pane_image1;

    @FXML
    private ImageView inner_pane_image11;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private Label today_day_name_label;

    @FXML
    private Label today_day_number_label;

    @FXML
    private Pane today_pane;

    private MainController mainController = MainController.getInstance();

    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException { // switch to add the driver details scene
        mainController.switchToMainMenu(event);
    }
}
