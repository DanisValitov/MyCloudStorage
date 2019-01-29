package com.danis.valitov.cloud.client.controller;

import db.ConnectionToDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class NewAccountController {
@FXML
TextField login;
    @FXML
    PasswordField password;



    public void create(ActionEvent actionEvent) {
        ConnectionToDB connectionToDB = new ConnectionToDB();

        ConnectionToDB.incertNewUser(login.getText(),password.getText());
    }
}
