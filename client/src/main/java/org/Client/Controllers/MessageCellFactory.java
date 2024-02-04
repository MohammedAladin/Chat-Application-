package org.Client.Controllers;

import Model.DTO.ContactDto;
import Model.DTO.MessageDTO;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.Client.Models.Model;

public class MessageCellFactory extends ListCell<MessageDTO> {
    @Override
    protected void updateItem(MessageDTO message, boolean empty) {
        super.updateItem(message, empty);
        if (message == null || empty) {
            setGraphic(null);
            setText(null);
        } else {
            if (message.getSenderID().equals(Model.getInstance().getClientId()) ){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientFxml/Message_sent.fxml"));
                MessageSent messageSent = new MessageSent();
                messageSent.setMessage(message.getContent());
                messageSent.setTimestamp(message.getTimestamp());
                loader.setController(messageSent);
                try {
                    VBox vbox = loader.load();
                    vbox.setAlignment(Pos.BASELINE_RIGHT);
                    setGraphic(vbox);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientFxml/Message_received.fxml"));
                MessageReceived messageReceived = new MessageReceived();
                messageReceived.setMessage(message.getContent());
                messageReceived.setTimestamp(message.getTimestamp());
                loader.setController(messageReceived);
                try {
                    VBox vbox = loader.load();
                    vbox.setAlignment(Pos.BASELINE_LEFT);
                    setGraphic(vbox);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
