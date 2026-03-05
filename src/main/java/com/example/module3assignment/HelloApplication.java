package com.example.module3assignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.stage.StageStyle;
import javafx.scene.text.Text;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Text text = new Text();


        Group root = new Group(text);
        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        stage.setTitle("Hello!");

        stage.show();
    }
}
