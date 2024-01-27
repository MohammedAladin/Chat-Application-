package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.Client.Models.Model;
import java.net.URL;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Online.setOnMouseClicked(e->{
            currentStatus.getChildrenUnmodifiable().get(0).setStyle("-fx-fill: #00ff00");
            Model.getInstance().getViewFactory().closePopUp();
        });
        Busy.setOnMouseClicked(e->{
            currentStatus.getChildrenUnmodifiable().get(0).setStyle("-fx-fill: #ff0000");
            Model.getInstance().getViewFactory().closePopUp();
        });
        Away.setOnMouseClicked(e->{
            currentStatus.getChildrenUnmodifiable().get(0).setStyle("-fx-fill: #ffff00");
            Model.getInstance().getViewFactory().closePopUp();
        });
        Offline.setOnMouseClicked(e->{
            currentStatus.getChildrenUnmodifiable().get(0).setStyle("-fx-fill: #696a6b");
            Model.getInstance().getViewFactory().closePopUp();
        });
    }
}
