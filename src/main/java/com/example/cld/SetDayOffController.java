package com.example.cld;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static com.example.cld.Main.dayOfMonth;

public class SetDayOffController {

    @FXML
    private Label Error_date;

    @FXML
    private JFXButton confirm_btm_DeleteEvent;

    @FXML
    private TextField enter_date_txt_field;

    @FXML
    private Label enter_day_name_label;

    @FXML
    private Label enter_day_number_label;

    @FXML
    private JFXTextArea events_on_enter_day_day_off_textArea;

    @FXML
    private Label today_day_name_label;

    @FXML
    private Label today_day_number_label;

    private final MainController mainController = MainController.getInstance();

    @FXML
    public void switchToMainMenu(javafx.event.Event event) throws IOException {
        mainController.switchToMainMenu(event);
    }

    @FXML
    public void switchToAddEventDetails(javafx.event.Event event) throws IOException {
        mainController.switchToAddEventDetails(event);
    }

    @FXML
    public void switchToSetDayOff(javafx.event.Event event) throws IOException {
        mainController.switchToSetDayOff(event);
    }

    @FXML
    public void switchToDeleteEvent(javafx.event.Event event) throws IOException {
        mainController.switchToDeleteEvent(event);
    }

    @FXML
    public void switchToShiftEvent(Event event) throws IOException {
        mainController.switchToShiftEvent(event);
    }

    @FXML
    public void switchToViewWeek(Event event) throws IOException {
        mainController.switchToViewWeek(event);
    }

    @FXML
    public void switchToViewMonth(Event event) throws IOException {
        mainController.switchToViewMonth(event);
    }

    @FXML
    private void successPopup() {
        Window owner = confirm_btm_DeleteEvent.getScene().getWindow();
        MainController.AlertHelper.showAlert(
                Alert.AlertType.INFORMATION, owner,
                "Set Day Off",
                "Success",
                "Day successfully set as a day off.",
                "/com/example/cld/Icons/Done_img_1.png",
                "/com/example/cld/Icons/dayOff.png"
        );
    }

    public void initialize() {
        confirm_btm_DeleteEvent.setOnAction(this::handleDayOff);

        today_day_number_label.setText(String.valueOf(dayOfMonth));
        today_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayOfMonth));

        if (dayOfMonth != 31) {
            enter_date_txt_field.setPromptText(dayOfMonth + " - 31");
        } else {
            enter_date_txt_field.setText(String.valueOf(dayOfMonth));
        }

        Error_date.setVisible(false);
    }

    private void showPopup(String message) {
        Window owner = confirm_btm_DeleteEvent.getScene().getWindow();
        MainController.AlertHelper.showAlert(
                Alert.AlertType.ERROR, owner,
                "Set Day Off",
                "Error",
                message,
                "/com/example/cld/Icons/CrossSign.png",
                "/com/example/cld/Icons/dayOff.png"
        );
    }

    private void dayOffRemovedSuccess() {
        Window owner = confirm_btm_DeleteEvent.getScene().getWindow();
        MainController.AlertHelper.showAlert(
                Alert.AlertType.INFORMATION, owner,
                "Set Day Off",
                "Success",
                "Day off removed successfully.",
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
                Error_date.setText(dayOfMonth == 31 ? "31st is the last day of the month." : "Enter a valid date between " + dayOfMonth + " and 31.");
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
    }

    private void handleDayOff(ActionEvent event) {
        try {
            String enterDateText = enter_date_txt_field.getText();

            validateInput(enterDateText);

            int dayToSetDayOff = Integer.parseInt(enterDateText);

            validateDate(dayToSetDayOff);

            if (mainController.getScheduler().days[dayToSetDayOff - 1].isDayOff()) {
                if (confirmKeepDayOff()) {
                    successPopup();
                    return;
                } else {
                    mainController.getScheduler().removeDayOff(dayToSetDayOff);
                    dayOffRemovedSuccess();
                    clearInputFields();
                    updateText(dayToSetDayOff);
                    return;
                }
            }

            Error_date.setVisible(false);

            updateText(dayToSetDayOff);

            mainController.getScheduler().markDayOff(dayToSetDayOff);

            successPopup();
            clearInputFields();
            updateText(dayToSetDayOff);

        } catch (NumberFormatException e) {
            showPopup("Enter a valid number.");
        } catch (Exception e) {
            showPopup(e.getMessage());
        }
    }

    private void validateInput(String enterDateText) {
        if (enterDateText.isEmpty()) {
            throw new IllegalArgumentException("Enter a date to set day off.");
        }
    }

    private void validateDate(int dayToSetDayOff) {
        if (dayToSetDayOff < dayOfMonth || dayToSetDayOff > 31) {
            Error_date.setVisible(true);
            throw new IllegalArgumentException(dayOfMonth == 31 ? "31st is the last day of the month." : "Enter a valid date between " + dayOfMonth + " and 31.");
        }
    }

    private boolean confirmKeepDayOff() {
        Window owner = confirm_btm_DeleteEvent.getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Set Day Off");
        alert.setHeaderText("Confirmation");
        alert.setContentText("The selected day is already marked as a day off. Do you want to keep it as a day off?");
        alert.initOwner(owner);

        Image alertImage = new Image(Objects.requireNonNull(MainController.AlertHelper.class.getResourceAsStream("/com/example/cld/Icons/DayOff_1_1.png")));
        ImageView alertImageView = new ImageView(alertImage);
        alertImageView.setFitWidth(40);
        alertImageView.setFitHeight(40);
        alert.setGraphic(alertImageView);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image windowIcon = new Image(Objects.requireNonNull(MainController.AlertHelper.class.getResourceAsStream("/com/example/cld/Icons/dayOff.png")));
        stage.getIcons().clear();
        stage.getIcons().add(windowIcon);

        alert.getButtonTypes().setAll(new ButtonType("Keep"), new ButtonType("Remove"));
        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get().getText().equals("Keep");
    }

    private void updateText(int dayToSetDayOff) {
        enter_day_number_label.setText(String.valueOf(dayToSetDayOff));
        enter_day_name_label.setText(DateNameMain.getDayAbbreviationAb(dayToSetDayOff));
        events_on_enter_day_day_off_textArea.setText(mainController.getScheduler().displayEvents(dayToSetDayOff));
    }
}
