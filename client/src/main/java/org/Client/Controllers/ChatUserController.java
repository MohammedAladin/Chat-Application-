package org.Client.Controllers;

import Model.DTO.AttachmentDto;
import Model.DTO.ContactDto;
import Model.DTO.MessageDTO;
import javafx.animation.PauseTransition;
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
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import org.Client.Models.Model;
import org.Client.Service.ImageServices;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ChatUserController implements Initializable {
    @FXML
    public TextField textFieldID;
    @FXML
    private Circle circleID;
    @FXML
    private Label nameID;
    @FXML
    private Label bio;
    @FXML
    private ListView<MessageDTO> messageListView;
    @FXML
    private Button send_btn;
    @FXML
    private Button attachemnt_btn;
    @FXML
    private Button styleBtn;
    byte[] image;
    String name;

    public String getBioString() {
        return bioString;
    }

    public void setBioString(String bioString) {
        this.bioString = bioString;
    }

    String bioString;
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
        bio.setText(bioString);
        if (image == null) {
            circleID.setFill(new ImagePattern(ImageServices.getDefaultImage()));
        } else circleID.setFill(new ImagePattern(ImageServices.convertToImage(image)));
        //statusID.setText();
        textFieldID.setOnAction(actionEvent -> {
            send_btn.fire();
        });
        messageListView.setItems(Model.getInstance().getPrivateChats().get(chatID));


        messageListView.setCellFactory(new Callback<ListView<MessageDTO>, ListCell<MessageDTO>>() {
            @Override
            public ListCell<MessageDTO> call(ListView<MessageDTO> chatListView) {
                return new MessageCellFactory();
            }
        });
        send_btn.setOnAction(e -> sendMessage());
        messageListView.refresh();
        messageListView.refresh();
        styleBtn.setOnAction(actionEvent -> {
            Model.getInstance().getViewFactory().showStylePopup(styleBtn);
        });
        attachemnt_btn.setOnAction(e->handleFileAttachement());
    }

    private void handleFileAttachement() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file to send");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
                AttachmentDto message = new AttachmentDto(chatID, Model.getInstance().getClientId(), file, file.getName());
               new Thread(()->{
                   try {
                       Model.getInstance().getCallBackServicesServer().sendAttachment(message);
                   } catch (RemoteException e) {
                       throw new RuntimeException(e);
                   }
               }).start();
                messageListView.setItems(Model.getInstance().getPrivateChats().get(chatID));

        }
    }

    private void sendMessage() {
        if (textFieldID.getText().isEmpty()) return;

        try {

            MessageDTO message = new MessageDTO(chatID, textFieldID.getText(), 0, Model.getInstance().getClientId(), Timestamp.valueOf(java.time.LocalDateTime.now()));

            Model.getInstance().getCallBackServicesServer().sendMessage(message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        messageListView.setItems(Model.getInstance().getPrivateChats().get(chatID));
        textFieldID.clear();
    }
}
