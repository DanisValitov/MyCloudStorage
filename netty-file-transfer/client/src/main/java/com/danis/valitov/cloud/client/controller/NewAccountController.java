package com.danis.valitov.cloud.client.controller;

import com.danis.valitov.cloud.client.Network;
import com.danis.valitov.cloud.common.Request;
import db.ConnectionToDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class NewAccountController {

@FXML
TextField login;
    @FXML
    PasswordField password;
    @FXML
    PasswordField password2;
@FXML
Label passwordLabel;

@FXML
Label loginLabel;

    public void create(ActionEvent actionEvent) {


        if(password.getText().equals(password2.getText())){
            passwordLabel.setText(" ");
            if(!ConnectionToDB.isUserExist(login.getText())){
                ConnectionToDB.incertNewUser(login.getText(),password.getText());
                loginLabel.setText(" ");
                loginLabel.setText("Account was created!");
                Network.request(Request.NEW_DIRRECTORY,login.getText());
            }else loginLabel.setText("login is already exist");
        } else passwordLabel.setText("not equal");


    }
}
