package org.Client.Controllers;

import Model.DTO.ChatDto;
import Model.DTO.MessageDTO;
import Model.DTO.ParticipantDto;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import org.Client.Models.Model;

import java.util.List;

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
            else {
                if (isAGroupchat(message.getChatID())) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientFxml/Message_received_group.fxml"));
                    MessageReceivedGroupController messageReceivedGroupController = new MessageReceivedGroupController();
                    messageReceivedGroupController.setMessage(message.getContent());
                    messageReceivedGroupController.setTimestamp(message.getTimestamp());
                    byte[] image = getParticipantImage(message.getChatID(), message.getSenderID());
                    messageReceivedGroupController.setImage(image);
                    String name = getParticipantName(message.getChatID(), message.getSenderID());
                    messageReceivedGroupController.setSendername(name);
                    loader.setController(messageReceivedGroupController);
                    try {
                        VBox vbox = loader.load();
                        vbox.setAlignment(Pos.BASELINE_LEFT);
                        setGraphic(vbox);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
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

    private byte[] getParticipantImage(Integer ChatID, Integer senderID) {
        byte  [] image = null;
        for(ChatDto chatDto : Model.getInstance().getGroupList()){
            if(chatDto.getChatID().equals(ChatID)){
                List<ParticipantDto> list =chatDto.getParticipants();
                for(ParticipantDto participantDto : list){
                    if(participantDto.getParticipantID().equals(senderID)){
                        System.out.printf("Image found for %s\n",participantDto.getParticipantName());
                        image=participantDto.getImage();
                    }
                }
            }
        }
        return image;
    }

    private String getParticipantName(Integer ChatID, Integer senderID) {
        String name = null;
        for(ChatDto chatDto : Model.getInstance().getGroupList()){
            if(chatDto.getChatID().equals(ChatID)){
                List<ParticipantDto> list =chatDto.getParticipants();
                for(ParticipantDto participantDto : list){
                    if(participantDto.getParticipantID().equals(senderID)){
                        System.out.printf("Name found for %s\n",participantDto.getParticipantName());
                        name=participantDto.getParticipantName();
                    }
                }
            }
        }
        return name;
    }

    private boolean isAGroupchat(Integer chatID) {
        for (ChatDto contact : Model.getInstance().getGroupList()) {
            if (contact.getChatID().equals(chatID)) {
                return true;
            }
        }
        return false;
    }
}
