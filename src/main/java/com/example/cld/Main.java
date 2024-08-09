package com.example.cld;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

import java.time.LocalDate;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public static LocalDate currentDate; // static variable for current date
    public static int dayOfMonth; // static variable for day of month

    @Override
    public void start(Stage primaryStage) throws Exception {

        currentDate = LocalDate.now(); // get current date
        dayOfMonth = currentDate.getDayOfMonth();

//       dayOfMonth = 31;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/cld/FXML/Main.fxml")));
        Scene scene = new Scene(root, 1221, 784);

        File file = new File("src/main/resources/com/example/cld/Icons/appLogo.png");
        Image icon = new Image(file.toURI().toString());

        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(icon);
        primaryStage.show();

    }
}