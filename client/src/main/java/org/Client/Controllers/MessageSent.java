package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageSent implements Initializable
{


    @FXML
    private Label messageLabelID;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
    public Label getMessageLabelID(){return messageLabelID;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLabelID.setText(message);
    }
}