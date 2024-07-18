package com.example.cld;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
        Scene scene = new Scene(root, 1221, 784);

        File file = new File("src/main/resources/com/example/cld/Icons/appLogo.png");
        Image icon = new Image(file.toURI().toString());

//        File file01 = new File("src/image02.jpg");
//        Image image01 = new Image(file01.toURI().toString());
//        ImageView imageView01 = new ImageView(image01);
//        imageView01.setFitHeight(750);
//        imageView01.setFitWidth(400);
//        imageView01.setLayoutX(0);
//        imageView01.setLayoutY(0);
//
//        if (root instanceof Pane) {
//            ((Pane) root).getChildren().add(imageView01);
//        }

        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(icon);
        primaryStage.show();



    }
}