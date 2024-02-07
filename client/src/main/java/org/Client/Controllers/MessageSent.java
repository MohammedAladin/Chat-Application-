package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class MessageSent implements Initializable
{


    @FXML
    private Label messageLabelID;
    private Timestamp timestamp;
    @FXML
    private Label time;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
    public Label getMessageLabelID(){return messageLabelID;}

    public void setMessageLabelID(Label messageLabelID) {
        this.messageLabelID = messageLabelID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLabelID.setText(message);
        time.setText(timestamp.toString());
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;

    }
}