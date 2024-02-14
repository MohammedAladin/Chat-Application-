package org.Client.Controllers;

import Model.DTO.ChatDto;
import Model.DTO.MessageDTO;
import Model.DTO.ParticipantDto;
import Model.DTO.Style;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.Client.Models.Model;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class MessageCellFactory extends ListCell<MessageDTO> {
    @Override
    protected void updateItem(MessageDTO message, boolean empty) {
        super.updateItem(message, empty);
        if (message == null || empty) {
            setGraphic(null);
            setText(null);
        } else {
            if(Objects.equals(message.getSenderID(), Model.getInstance().getClientId())) {
                ContextMenu contextMenu = new ContextMenu();
                MenuItem deleteMenuItem = new MenuItem("Delete");
                deleteMenuItem.setStyle("-fx-text-fill: red;");
                deleteMenuItem.setOnAction(event -> {
                    deleteMessage(message);
                });
                contextMenu.getItems().add(deleteMenuItem);

                // Right-click action handling
                setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        contextMenu.show(this, event.getScreenX(), event.getScreenY());
                    }
                });
            }
            if (message.getIsAttachment() == 1) {
                VBox node = dealWithAttachment(message);
                setGraphic(node);
            } else {
                if (message.getSenderID().equals(Model.getInstance().getClientId())) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientFxml/Message_sent.fxml"));
                    MessageSent messageSent = new MessageSent();
                    messageSent.setMessage(message.getContent());
                    messageSent.setTimestamp(message.getTimestamp());
                    loader.setController(messageSent);
                    try {
                        VBox vbox = loader.load();
                        vbox.setAlignment(Pos.BASELINE_RIGHT);
                        if (message.getStyle() != null) {
                            Label messageLabel = messageSent.getMessageLabelID();
                            messageLabel.setStyle(decodeStyle(message.getStyle()));
                        }
                        setGraphic(vbox);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
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
                            if (message.getStyle() != null) {
                                Label messageLabel = messageReceivedGroupController.getMessageLabel();
                                messageLabel.setStyle(decodeStyle(message.getStyle()));
                            }
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
                            if (message.getStyle() != null) {
                                Label messageLabel = messageReceived.getMessageLabelID();
                                messageLabel.setStyle(decodeStyle(message.getStyle()));
                            }
                            setGraphic(vbox);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void deleteMessage(MessageDTO message) {
        if (message.getChatID() != null) {
            ObservableList<MessageDTO> messages = Model.getInstance().getPrivateChats().get(message.getChatID());
            if (messages != null) {
                messages.remove(message);
                try {
                    Model.getInstance().getCallBackServicesServer().deleteMessage(message);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    private VBox dealWithAttachment(MessageDTO message) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientFxml/attachemnt.fxml"));
        AttachementController controller = new AttachementController();
        controller.setAttachmentID(message.getAttachmentID());
        controller.setMessage(message.getContent());
        controller.setTime(message.getTimestamp());
        System.out.println("this is the attachment id "+message.getAttachmentID());
         if(isAGroupchat(message.getChatID())&&!message.getSenderID().equals(Model.getInstance().getClientId())){
            controller.setImg(getParticipantImage(message.getChatID(), message.getSenderID()));
            controller.setUserName(getParticipantName(message.getChatID(), message.getSenderID()));
        }
        loader.setController(controller);
        VBox vbox;
        try {
            vbox =loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(message.getSenderID().equals(Model.getInstance().getClientId())){
            controller.getLabelContainer().setStyle(" -fx-background-color: #6e00ff;\n" +
                    "    -fx-text-fill: #FFFFFF;\n" +
                    "    -fx-background-radius: 15px;\n" +
                    "    -fx-wrap-text: true;\n" +
                    "    -fx-padding: 10px;" +
                    "   -fx-alignment: center-right;");
            vbox.setAlignment(Pos.BASELINE_RIGHT);
        }

        else {

            controller.getLabelContainer().setStyle("-fx-background-color: #e7e7e7;\n" +
                    "    -fx-text-fill: #303030;\n" +
                    "    -fx-background-radius: 15px;\n" +
                    "    -fx-wrap-text: true;\n" +
                    "    -fx-padding: 10px;" +
                    "   -fx-alignment: center-left;");
            vbox.setAlignment(Pos.BASELINE_LEFT);

        }


        return vbox;
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
    public String decodeStyle(Style style){

            String fontColor = style.getColor();
            String backgroundColor = style.getBackgroundColor();
            System.out.println(style.getColor() + " "+style.getBackgroundColor());
            String css = String.format("-fx-font-size: %d; -fx-font-style: %s; -fx-font-weight: %s; -fx-text-fill: %s; -fx-background-color: %s;",
                    style.getFontSize(),
                    style.getFontStyle()[0],
                    style.getFontStyle()[1],
                    fontColor,
                    backgroundColor
            );

          return css ;

    }
}
