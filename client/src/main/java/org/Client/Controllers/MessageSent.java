package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.Client.Models.Model;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        messageLabelID.maxWidthProperty().bind(Model.getInstance().getViewFactory().getChatUserController().getMessageListView().widthProperty().multiply(0.65));
        messageLabelID.setText(message);
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        time.setText(localDateTime.format(formatter));
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;

    }
}