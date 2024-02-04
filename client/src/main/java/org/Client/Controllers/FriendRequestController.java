package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.Client.Models.Model;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class FriendRequestController implements Initializable {
    @FXML
    Button accept_btn;
    @FXML
    Button reject_btn;
    @FXML
    Label username;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    int userID;
    private Integer noticationID;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    String Username;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(Username+"");
        accept_btn.setOnAction(actionEvent -> {
            accept();
        });
        reject_btn.setOnAction(actionEvent -> {
            reject();
        });

    }
    public void accept(){
        try {
            Model.getInstance().getCallBackServicesServer().acceptInvitation(Model.getInstance().getClientId(), userID);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void reject(){
        try {
            Model.getInstance().getCallBackServicesServer().rejectInvitation(userID,Model.getInstance().getClientId() );
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    public void setNotificationId(Integer notificationID) {
        this.noticationID = notificationID;
    }
}
