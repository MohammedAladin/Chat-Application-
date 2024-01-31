package org.Client.Controllers;


import Model.DTO.ContactDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import org.Client.ClientEntities.Chat;


public class ChatCellFactory extends ListCell<ContactDto> {
    @Override
    protected void updateItem(ContactDto chat, boolean empty) {
        super.updateItem(chat, empty);
        if (chat == null||empty) {
            setGraphic(null);
            setText(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/contact.fxml"));
            ChatController controller = new ChatController();
            fxmlLoader.setController(controller);
            controller.setImage(chat.getContactImage());
            controller.setName(chat.getContactName());
            controller.setStatus(chat.getStatus());
            controller.setMessage(chat.getBio());


            try {
                setGraphic(fxmlLoader.load());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
