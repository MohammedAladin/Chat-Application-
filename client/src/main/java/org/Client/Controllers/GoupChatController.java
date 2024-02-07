package org.Client.Controllers;

import Model.DTO.MessageDTO;
import Model.DTO.ParticipantDto;
import Model.DTO.Style;
import javafx.application.Platform;
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

    @FXML
    private Button styleBtn;

    private Style style;

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public TextField getTextFieldID() {
        return textFieldID;
    }

    public void setTextFieldID(TextField textFieldID) {
        this.textFieldID = textFieldID;
    }

    public List<ParticipantDto> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantDto> participants) {
        this.participants = participants;
    }

    private List<ParticipantDto> participants = new ArrayList<>();

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
        if (image == null || image.length == 0) {
            circleID.setFill(new ImagePattern(ImageServices.getDefaultGroupImage()));
        } else circleID.setFill(new ImagePattern(ImageServices.convertToImage(image)));
        textFieldID.setOnAction(event -> sendMessage());
        send_btn.setOnAction(event -> sendMessage());
        StringBuilder chatParticipants = new StringBuilder();
        for (ParticipantDto participant : participants) {
            chatParticipants.append(participant.getParticipantName()).append(", ");
        }
        chatParticipants.delete(chatParticipants.length() - 2, chatParticipants.length());
        bio.setText(chatParticipants.toString());

        textFieldID.textProperty().addListener((observable, oldValue, newValue) -> applyStyle());

        messageListView.setCellFactory(new Callback<ListView<MessageDTO>, ListCell<MessageDTO>>() {
            @Override
            public ListCell<MessageDTO> call(ListView<MessageDTO> messageListView) {
                return new MessageCellFactory();
            }
        });
        messageListView.setItems(Model.getInstance().getPrivateChats().get(chatID));
        messageListView.scrollTo(Model.getInstance().getPrivateChats().get(chatID).size() - 1);
        messageListView.refresh();
        styleBtn.setOnAction(actionEvent -> {
            Model.getInstance().getViewFactory().showStylePopup(styleBtn);
        });
    }

    public void sendMessage() {
        String message = textFieldID.getText();
        if (!message.isEmpty()) {
            textFieldID.clear();
            MessageDTO messageDTO = new MessageDTO(chatID, message, 0, Model.getInstance().getClientId(), style,Timestamp.valueOf(java.time.LocalDateTime.now()));
            try {
                Model.getInstance().getCallBackServicesServer().sendGroupMessage(messageDTO, participants);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        messageListView.setItems(Model.getInstance().getPrivateChats().get(chatID));
    }

    private void applyStyle() {
        Style tempStyle = Model.getInstance().getStyle();
        String fontColor = tempStyle.getColor();
        String backgroundColor = tempStyle.getBackgroundColor();
        System.out.println(tempStyle.getColor() + " " + tempStyle.getBackgroundColor());
        String css = String.format("-fx-font-size: %d; -fx-font-style: %s; -fx-font-weight: %s; -fx-text-fill: %s; -fx-background-color: %s;",
                tempStyle.getFontSize(),
                tempStyle.getFontStyle()[0],
                tempStyle.getFontStyle()[1],
                fontColor,
                backgroundColor
        );

        // Set the CSS string as the style of the textFieldID
        Platform.runLater(() -> {
            textFieldID.setStyle(css);

        });
    }
}
