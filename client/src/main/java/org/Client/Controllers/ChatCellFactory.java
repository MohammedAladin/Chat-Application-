package org.Client.Controllers;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;


public class ChatCellFactory extends ListCell<Chat> {
    @Override
    protected void updateItem(Chat chat, boolean empty) {
        super.updateItem(chat, empty);
        if (chat == null||empty) {
            setGraphic(null);
            setText(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/contact.fxml"));
            ChatController controller = new ChatController();
            fxmlLoader.setController(controller);
            controller.setImage(chat.getImage());
            controller.setName(chat.getName());
            controller.setStatus(chat.getStatus());
            controller.setMessage(chat.getMessage());

            try {
                setGraphic(fxmlLoader.load());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
