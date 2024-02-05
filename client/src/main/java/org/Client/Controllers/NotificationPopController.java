package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class NotificationPopController implements Initializable {
    @FXML
    private Label noti_msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        noti_msg.setText(msg);
    }
}
