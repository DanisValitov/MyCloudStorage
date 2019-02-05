package com.danis.valitov.cloud.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewAccount extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        String fxmlFile = "/newAccount.fxml";

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));

        Parent root = fxmlLoader.load();
        primaryStage.setTitle("New Account");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
