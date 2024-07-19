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

public class DeleteEventController {

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
    private JFXButton confirm_btm_deleteEvent;

    @FXML
    private Pane date_picker;

    @FXML
    private JFXButton enter_button;

    @FXML
    private TextField enter_date_txt_field;

    @FXML
    private Label enter_day_name_label;

    @FXML
    private Label enter_day_number_label;

    @FXML
    private TextField enter_event_name_txt_field;

    @FXML
    private Pane enter_today_pane;

    @FXML
    private Label events_on_enter_day_label;

    @FXML
    private ImageView inner_pane_image1;

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

    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException { // switch to add the driver details scene
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/Main.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

}
