package org.Client.Controllers;

import Model.DTO.ContactDto;
import Model.DTO.MessageDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.Client.Models.Model;
import org.Client.Service.ImageServices;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatUserController implements Initializable {
    @FXML
    public TextField textFieldID;
    @FXML
    private Circle circleID;
    @FXML
    private Label nameID;
    @FXML
    private Label statusID;
    @FXML
    private ListView<MessageDTO> messageListView;
    @FXML
    private Button send_btn;
    @FXML
    private Button attachemnt_btn;
    byte[] image;
    String name;
    private Integer chatID;
    private ObservableList<MessageDTO> messages = FXCollections.observableArrayList();

    public Integer getChatID() {
        return chatID;
    }

    public void setChatID(Integer chatID) {
        this.chatID = chatID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameID.setText(name);
        if (image == null) {
            circleID.setFill(new ImagePattern(new Image(getClass().getResource("/ClientImages/defaultUser.jpg").toString())));
        } else circleID.setFill(new ImagePattern(ImageServices.convertToImage(image)));
        //statusID.setText();
        textFieldID.setOnAction(actionEvent -> {
            send_btn.fire();
        });


        send_btn.setOnAction(e -> sendMessage());
        messageListView.setItems(Model.getInstance().getPrivateChats().get(chatID));


        messageListView.setCellFactory(new Callback<ListView<MessageDTO>, ListCell<MessageDTO>>() {
            @Override
            public ListCell<MessageDTO> call(ListView<MessageDTO> chatListView) {
                return new MessageCellFactory();
            }
        });
    }

    private void sendMessage() {
        Model.getInstance().getPrivateChats().get(chatID).add(new MessageDTO(chatID, textFieldID.getText(), 0, Model.getInstance().getClientId()));
        System.out.println(Model.getInstance().getPrivateChats().get(chatID).get(0).getContent() + " this is the message");
        messageListView.setItems(Model.getInstance().getPrivateChats().get(chatID));
        textFieldID.clear();
    }
}
