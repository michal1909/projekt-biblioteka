package com.example.biblioteka;

import connect.ConnectionDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        String css=this.getClass().getResource("/styles/style.css").toExternalForm();;
        stage.setResizable(false);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

        Image icon = new Image(getClass().getResourceAsStream("/com/example/projektfx/images/adminIcons/icons8_Library_Building_50.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Biblioteka");

    }

    public static void main(String[] args) {
        launch();
    }
}