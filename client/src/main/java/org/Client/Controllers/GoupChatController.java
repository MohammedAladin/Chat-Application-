package org.Client.Controllers;

import Model.DTO.MessageDTO;
import Model.DTO.ParticipantDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import org.Client.Models.Model;
import org.Client.Service.ImageServices;

import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GoupChatController implements Initializable {
    @FXML
    public TextField textFieldID;
    @FXML
    private Circle circleID;
    @FXML
    private Label nameID;
    @FXML
    private Label bio;

    public ListView<MessageDTO> getMessageListView() {
        return messageListView;
    }

    @FXML
    private ListView<MessageDTO> messageListView;
    @FXML
    private Button send_btn;
    @FXML
    private Button attachemnt_btn;

    public List<ParticipantDto> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantDto> participants) {
        this.participants = participants;
    }

    private List<ParticipantDto> participants=new ArrayList<>();

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChatID() {
        return chatID;
    }

    public void setChatID(Integer chatID) {
        this.chatID = chatID;
    }

    byte[] image;
    String name;
    private Integer chatID;
    private ObservableList<MessageDTO> messages = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nameID.setText(name);
        if(image == null|| image.length == 0){
            circleID.setFill(new ImagePattern(ImageServices.getDefaultGroupImage()));
        }
        else circleID.setFill(new ImagePattern(ImageServices.convertToImage(image)));
        textFieldID.setOnAction(event -> sendMessage());
        send_btn.setOnAction(event -> sendMessage());
        StringBuilder chatParticipants = new StringBuilder();
        for (ParticipantDto participant : participants) {
            chatParticipants.append(participant.getParticipantName()).append(", ");
        }
        chatParticipants.delete(chatParticipants.length() - 2, chatParticipants.length());
        bio.setText(chatParticipants.toString());

        messageListView.setCellFactory(new Callback<ListView<MessageDTO>, ListCell<MessageDTO>>() {
            @Override
            public ListCell<MessageDTO> call(ListView<MessageDTO> messageListView) {
                return new MessageCellFactory();
            }
        });
        messageListView.setItems(Model.getInstance().getPrivateChats().get(chatID));
        System.out.println("Group Chat ID: " + chatID);
        messageListView.refresh();
    }

    public void sendMessage() {
        String message = textFieldID.getText();
        if (!message.isEmpty()) {
            textFieldID.clear();
            MessageDTO messageDTO = new MessageDTO(chatID, message, 0, Model.getInstance().getClientId(), Timestamp.valueOf(java.time.LocalDateTime.now()));
            try {
                Model.getInstance().getCallBackServicesServer().sendGroupMessage(messageDTO, participants);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        messageListView.setItems(Model.getInstance().getPrivateChats().get(chatID));
    }


}
