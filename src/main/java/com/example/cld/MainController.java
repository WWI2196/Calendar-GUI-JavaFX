package com.example.cld;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController {

    private Stage stage;
    private Scene scene;

    @FXML
    private JFXButton btm_addEvent;

    @FXML
    private JFXButton btm_date1;

    @FXML
    private JFXButton btm_date10;

    @FXML
    private JFXButton btm_date11;

    @FXML
    private JFXButton btm_date12;

    @FXML
    private JFXButton btm_date13;

    @FXML
    private JFXButton btm_date14;

    @FXML
    private JFXButton btm_date15;

    @FXML
    private JFXButton btm_date16;

    @FXML
    private JFXButton btm_date17;

    @FXML
    private JFXButton btm_date18;

    @FXML
    private JFXButton btm_date19;

    @FXML
    private JFXButton btm_date2;

    @FXML
    private JFXButton btm_date20;

    @FXML
    private JFXButton btm_date21;

    @FXML
    private JFXButton btm_date22;

    @FXML
    private JFXButton btm_date23;

    @FXML
    private JFXButton btm_date24;

    @FXML
    private JFXButton btm_date25;

    @FXML
    private JFXButton btm_date26;

    @FXML
    private JFXButton btm_date27;

    @FXML
    private JFXButton btm_date28;

    @FXML
    private JFXButton btm_date29;

    @FXML
    private JFXButton btm_date3;

    @FXML
    private JFXButton btm_date30;

    @FXML
    private JFXButton btm_date31;

    @FXML
    private JFXButton btm_date4;

    @FXML
    private JFXButton btm_date5;

    @FXML
    private JFXButton btm_date6;

    @FXML
    private JFXButton btm_date7;

    @FXML
    private JFXButton btm_date8;

    @FXML
    private JFXButton btm_date9;

    @FXML
    private JFXButton btm_dayOff;

    @FXML
    private JFXButton btm_deleteEvent;

    @FXML
    private JFXButton btm_shiftEvent;

    @FXML
    private JFXButton check_button;

    @FXML
    private TextArea events_on_txt_box;

    @FXML
    private TextArea events_today_txt_box;

    @FXML
    private ImageView inner_pane_image1;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private Pane today_pane;

    @FXML
    private Pane today_pane1;

    @FXML
    private Pane today_pane11;

    @FXML
    private Label today_pane_day_name_label;

    @FXML
    private Label today_pane_day_name_label11;

    @FXML
    private Label today_pane_day_number_label;

    @FXML
    private Label today_pane_day_number_label11;

    public void switchToAddEventDetails(ActionEvent event) throws IOException { // switch to add the driver details scene
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/AddEvent.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void switchToSetDayOff(ActionEvent event) throws IOException { // switch to add the driver details scene
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/SetDayOff.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

}
