package com.danis.valitov.cloud.client.controller;

import com.danis.valitov.cloud.client.Network;
import com.danis.valitov.cloud.common.Request;
import db.ConnectionToDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthController {
    @FXML
    TextField login;
    @FXML
    PasswordField password;
    @FXML
    Label status;
    public void logIn(ActionEvent actionEvent) {
        ConnectionToDB connectionToDB = new ConnectionToDB();

      if(ConnectionToDB.logIn(login.getText(),password.getText())) {
          status.setText("Success!!");
MainController.user = login.getText();
          Network.request(Request.REFRESH,login.getText());
      } else status.setText("Invalid login/password");
    }
}
