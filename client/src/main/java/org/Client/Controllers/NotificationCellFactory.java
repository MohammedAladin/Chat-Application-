package org.Client.Controllers;

import Model.DTO.NotificationDTO;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import org.Client.ClientEntities.Chat;

public class NotificationCellFactory extends ListCell<NotificationDTO> {
    protected void updateItem(NotificationDTO notification, boolean empty) {
        super.updateItem(notification, empty);
        if (notification == null||empty) {
            setGraphic(null);
            setText(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/friendRequest.fxml"));
            FriendRequestController controller = new FriendRequestController();
            fxmlLoader.setController(controller);
            controller.setUsername(notification.getSenderID());
            controller.setNotificationId(notification.getNotificationID());


            try {
                setGraphic(fxmlLoader.load());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
