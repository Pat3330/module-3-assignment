package com.example.module3assignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("game-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 400, 250);
        stage.setScene(scene);
        stage.setTitle("24 Game");

        stage.show();
    }
}
