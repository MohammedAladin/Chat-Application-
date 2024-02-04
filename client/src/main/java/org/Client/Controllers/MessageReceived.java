package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.Client.Models.Model;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class MessageReceived implements Initializable
{
    @FXML
    private Label messageLabelID;
    String message;
    @FXML
    private Label time;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    private Timestamp timestamp;

    public void setMessage(String text){
        message = text;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLabelID.setText(message);
        time.setText(timestamp.toString());
    }
}