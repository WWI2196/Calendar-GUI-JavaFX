package com.example.cld;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
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

    @FXML
    private RadioButton one_time_radio_btn;

    @FXML
    private RadioButton daily_radio_btn;

    @FXML
    private RadioButton weekly_radio_btn;

    @FXML
    private Label Error_date;

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
    private void Error(ActionEvent event) {
        // Create the alert
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Message");
        alert.setContentText("There is a problem with entered values; check whether the entered values are in correct format");
        alert.setHeaderText("Error");

        // Set a custom image for the alert
        Image customImage = new Image(getClass().getResourceAsStream("/com/example/cld/Icons/CrossSign.png"));
        ImageView imageView = new ImageView(customImage);
        imageView.setFitWidth(40); // Set desired width
        imageView.setFitHeight(40); // Set desired height
        alert.setGraphic(imageView);

        // Set a custom icon for the application window
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/cld/Icons/Message.png")));

        // Show the alert and wait for user response
        alert.showAndWait();
    }

    @FXML
    private void Success(ActionEvent event) {
        // Create the alert
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Message");
        alert.setContentText("Event Successfully added");
        alert.setHeaderText("Success");

        // Set a custom image for the alert
        Image customImage = new Image(getClass().getResourceAsStream("/com/example/cld/Icons/Done_img_1.png"));
        ImageView imageView = new ImageView(customImage);
        imageView.setFitWidth(40); // Set desired width
        imageView.setFitHeight(40); // Set desired height
        alert.setGraphic(imageView);

        // Set a custom icon for the application window
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/cld/Icons/Message.png")));

        // Show the alert and wait for user response
        alert.showAndWait();
    }

    public void initialize() {
        today_day_number_label.setText(String.valueOf(dayOfMonth));
        today_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayOfMonth));

          // Configure the ToggleGroup for repeatType
        ToggleGroup repeatTypeGroup = new ToggleGroup();
        one_time_radio_btn.setToggleGroup(repeatTypeGroup);
        daily_radio_btn.setToggleGroup(repeatTypeGroup);
        weekly_radio_btn.setToggleGroup(repeatTypeGroup);



        // Add an event handler for the confirm button
        confirm_btm_addEvent.setOnAction(this::onConfirmButtonClick);

        // Hide the Error_date label initially
        Error_date.setVisible(false);
    }

     /**
     * Method to handle the confirm button click event
     */
    private void onConfirmButtonClick(ActionEvent event) {
        // Schedule the event
        scheduleEvent();

        // Clear all input fields
        clearInputFields();

        // Optionally, clear other UI components
        resetCheckBoxesAndRadioButtons();
    }

    protected void scheduleEvent() {
        try {

            int dayToschedule = Integer.parseInt(enter_date_txt_field.getText());

            // Validate the entered date
            if (dayToschedule < dayOfMonth || dayToschedule > 31) {
                Error_date.setVisible(true);
                Error_date.setText("Enter a valid date between " + dayOfMonth + " and 31.");
                return;
            }

            Error_date.setVisible(false); // Hide the error label if the date is valid

            String title = enter_event_name_txt_field.getText(); // done

            scheduler = new Scheduler(dayToschedule);
            enter_day_number_label.setText(String.valueOf(dayToschedule));
            enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayToschedule));
            events_on_enter_day_textArea.setText(scheduler.displayEvents(dayToschedule));

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

            scheduler.scheduleEvent(dayToschedule, event);


        } catch (NumberFormatException e) {
            showErrorPopup("Invalid number format for time fields.");
        } catch (IllegalArgumentException e) {
            showErrorPopup(e.getMessage());
        } catch (Exception e) {
            showErrorPopup("An unexpected error occurred.");
        }
    }

    private void showErrorPopup(String message) {
        // Create the alert
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Message");
        alert.setContentText(message);
        alert.setHeaderText("Error");

        // Set a custom image for the alert
        Image customImage = new Image(getClass().getResourceAsStream("/com/example/cld/Icons/CrossSign.png"));
        ImageView imageView = new ImageView(customImage);
        imageView.setFitWidth(40); // Set desired width
        imageView.setFitHeight(40); // Set desired height
        alert.setGraphic(imageView);

        // Set a custom icon for the application window
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/cld/Icons/Message.png")));

        // Show the alert and wait for user response
        alert.showAndWait();
    }

    /**
     * Clear all input fields
     */
    private void clearInputFields() {
        //enter_date_txt_field.clear();
        enter_event_name_txt_field.clear();
        enter_start_time_txt_field.clear();
        enter_end_time_txt_field.clear();
        //events_on_enter_day_textArea.clear();
    }

    /**
     * Optionally reset checkboxes and radio buttons to default state
     */
    private void resetCheckBoxesAndRadioButtons() {
        daily_check_box.setSelected(false);
        one_time_check_box.setSelected(false);
        weekly_check_box.setSelected(false);

        one_time_radio_btn.setSelected(false);
        daily_radio_btn.setSelected(false);
        weekly_radio_btn.setSelected(false);
    }


}
