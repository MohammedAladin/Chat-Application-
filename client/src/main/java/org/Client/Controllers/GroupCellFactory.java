package org.Client.Controllers;
import Model.DTO.ChatDto;
import Model.DTO.ContactDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class GroupCellFactory extends ListCell<ChatDto> {
    @Override
    protected void updateItem(ChatDto chat, boolean empty) {
        super.updateItem(chat, empty);
        FXMLLoader fxmlLoader=null;
        if (chat == null || empty) {
            setGraphic(null);
            setText(null);
        } else {
            fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/group.fxml"));
            GroupCardController controller = new GroupCardController();
            fxmlLoader.setController(controller);
            controller.setChatName(chat.getChatName());
            controller.setChatImage(chat.getChatImage());
            try {
                setGraphic(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }



    }
}
