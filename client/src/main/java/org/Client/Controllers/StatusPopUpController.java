package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.Client.Models.Model;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;


public class StatusPopUpController implements Initializable {

    @FXML
    private Label currentStatus;
    @FXML
    private Label Online;
    @FXML
    private Label Busy;
    @FXML
    private Label Away;
    @FXML
    private Label Offline;

    private String phoneNumber= Model.getInstance().getViewFactory().getPhoneNumber();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Online.setOnMouseClicked(e->{
            currentStatus.getChildrenUnmodifiable().get(0).setStyle("-fx-fill: #00ff00");
            Model.getInstance().getViewFactory().closePopUp();
            try {
                Model.getInstance().getCallBackServicesServer().changeStatus(Model.getInstance().getClientId(),"Online");
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });
        Busy.setOnMouseClicked(e->{
            currentStatus.getChildrenUnmodifiable().get(0).setStyle("-fx-fill: #ff0000; -fx-stroke: transparent");
            Model.getInstance().getViewFactory().closePopUp();
        });
        Away.setOnMouseClicked(e->{
            currentStatus.getChildrenUnmodifiable().get(0).setStyle("-fx-fill: #ffff00; -fx-stroke: transparent");
            Model.getInstance().getViewFactory().closePopUp();
        });
        Offline.setOnMouseClicked(e->{
            currentStatus.getChildrenUnmodifiable().get(0).setStyle("-fx-fill: #696a6b; -fx-stroke: transparent");
            try {
                Model.getInstance().getCallBackServicesServer().changeStatus(Model.getInstance().getClientId(),"Offline");
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
            Model.getInstance().getViewFactory().closePopUp();
        });
    }
}
