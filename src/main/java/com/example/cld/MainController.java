package com.example.cld;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;

import static com.example.cld.Main.dayOfMonth;


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
     private JFXTextArea events_on_selected_day_textArea;

    @FXML
    private JFXTextArea events_on_today_textArea;

    @FXML
    private ImageView inner_pane_image1;

    @FXML
    private JFXButton profile_button;

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
    private Label events_on_day_number_label;

    @FXML
    private Label today_pane_day_number_label;

    @FXML
    private Label events_on_day_name_label;

    public static Scheduler scheduler;
    private static MainController instance;

    public void initialize() {

        // Initialize scheduler with current date
        scheduler = new Scheduler(dayOfMonth);

        // Set current date and day name
        today_pane_day_number_label.setText(String.valueOf(dayOfMonth));
        today_pane_day_name_label.setText(DateNameMain.getDayAbbreviation(dayOfMonth));

        events_on_day_number_label.setText(String.valueOf(dayOfMonth+1));
        events_on_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayOfMonth+1));

        events_on_today_textArea.setText(scheduler.displayEvents(dayOfMonth));
        events_on_selected_day_textArea.setText(scheduler.displayEvents(dayOfMonth+1));

        setupDateButtonActions();

//        btm_addEvent.setOnAction(event -> {
//            try {
//                switchToAddEventDetails(event);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
    }

    public MainController() {
        instance = this;
    }

    public static MainController getInstance() {
        return instance;
    }

     @FXML
    public void switchToAddEventDetails(Event event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/AddEvent.fxml")));

        EventAction(event, root);
     }

    public void switchToSetDayOff(Event event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/SetDayOff.fxml")));

        EventAction(event, root);
    }

    public void switchToDeleteEvent(Event event) throws IOException { // switch to add the driver details scene
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/DeleteEvent.fxml")));

        EventAction(event, root);
    }

    public void switchToShiftEvent(Event event) throws IOException { // switch to add the driver details scene
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/ShiftEvent.fxml")));

        EventAction(event, root);
    }

    public void switchToMainMenu(Event event) throws IOException { // switch to add the driver details scene
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/Main.fxml")));

        EventAction(event, root);
    }

    private void EventAction(Event event, Parent root) {
        if (event instanceof ActionEvent) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } else if (event instanceof MouseEvent) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
   private void handleDateButtonPressed(ActionEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        int selectedDate = Integer.parseInt(button.getText());

        // Update the selected date label and day name label
        events_on_day_number_label.setText(String.valueOf(selectedDate));
        events_on_day_name_label.setText(DateNameMain.getDayAbbreviationAb(selectedDate));

        // Display events for the selected date
        handleDisplayEvents(selectedDate);
    }

    private void handleDisplayEvents(int date) {
        events_on_selected_day_textArea.setText(scheduler.displayEvents(date));
    }

    public void setupDateButtonActions() {
        // Assign handleDateButtonPressed to each date button
        for (int i = 1; i <= 31; i++) {
            try {
                Field field = getClass().getDeclaredField("btm_date" + i);
                field.setAccessible(true); // Make private fields accessible
                JFXButton button = (JFXButton) field.get(this);
                if (button != null) {
                    button.setOnAction(this::handleDateButtonPressed);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }



}
