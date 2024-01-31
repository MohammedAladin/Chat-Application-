package org.Client.Controllers;

import Model.DTO.NotificationDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.Client.ClientEntities.Chat;
import org.Client.Models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class NotificationListController implements Initializable {
    @FXML
    public ListView<NotificationDTO> noti_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        noti_listview.setCellFactory(new Callback<ListView<NotificationDTO>, ListCell<NotificationDTO>>() {
            @Override
            public ListCell<NotificationDTO> call(ListView<NotificationDTO> chatListView) {
                return new NotificationCellFactory();
            }
        });
        noti_listview.getItems().addAll(Model.getInstance().getNotifications());

    }
}
