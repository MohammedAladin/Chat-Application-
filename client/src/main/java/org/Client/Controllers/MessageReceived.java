package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageReceived implements Initializable
{
    @FXML
    private Label messageLabelID;
    String message;

    public void setMessage(String text){
        message = text;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLabelID.setText(message);
    }
}