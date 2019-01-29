package com.danis.valitov.cloud.client.controller;

import db.ConnectionToDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthController {
    @FXML
    TextField login;
    @FXML
    PasswordField password;
    public void logIn(ActionEvent actionEvent) {
        ConnectionToDB connectionToDB = new ConnectionToDB();

        ConnectionToDB.logIn(login.getText(),password.getText());
    }
}
