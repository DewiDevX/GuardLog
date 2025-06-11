package com.guardlog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlPath = getClass().getResource("/fxml/login.fxml");
        System.out.println("FXML found at: " + fxmlPath);

        if (fxmlPath == null) {
            throw new IllegalStateException("FXML file not found!");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlPath);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("GuardLog");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
 