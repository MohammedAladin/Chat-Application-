package org.Client.Controllers;

import Model.DTO.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.Client.Models.Model;
import org.Client.Service.ImageServices;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
    StyleController styleController;
    Style style;
    public List<ParticipantDto> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantDto> participants) {
        this.participants = participants;
    }

    private List<ParticipantDto> participants = new ArrayList<>();

    public StyleController getStyleController() {
        return styleController;
    }

    public void setStyleController(StyleController styleController) {
        this.styleController = styleController;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getBioString() {
        return bioString;
    }

    public void setBioString(String bioString) {
        this.bioString = bioString;
    }

    public TextField getTextFieldID() {
        return textFieldID;
    }

    public void setTextFieldID(TextField textFieldID) {
        this.textFieldID = textFieldID;
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
        if (image == null||image.length==0){
            if(isChatInGroupList(chatID)){
                circleID.setFill(new ImagePattern(ImageServices.getDefaultGroupImage()));
            }
           else circleID.setFill(new ImagePattern(ImageServices.getDefaultImage()));
        }
        else circleID.setFill(new ImagePattern(ImageServices.convertToImage(image)));

        textFieldID.setOnAction(actionEvent -> {
            send_btn.fire();
        });
        messageListView.setItems(Model.getInstance().getPrivateChats().get(chatID));
        messageListView.scrollTo(Model.getInstance().getPrivateChats().get(chatID).size() - 1);
        textFieldID.textProperty().addListener((observable, oldValue, newValue) -> applyStyle());


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

            MessageDTO message = new MessageDTO(chatID, textFieldID.getText(), 0, Model.getInstance().getClientId(), style,Timestamp.valueOf(java.time.LocalDateTime.now()));

            Model.getInstance().getCallBackServicesServer().sendMessage(message);
        } catch (RemoteException e) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setContentText("Sorry, we couldn't send your message. Please check your connection and try again later.");
              alert.show();
        }
        messageListView.setItems(Model.getInstance().getPrivateChats().get(chatID));
        textFieldID.clear();
    }
    private void applyStyle() {
        Style tempStyle = Model.getInstance().getStyle();
        String fontColor = tempStyle.getColor();
        String backgroundColor = tempStyle.getBackgroundColor();
        System.out.println(tempStyle.getColor() + " "+tempStyle.getBackgroundColor());
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

    public boolean isChatInGroupList(int chatID) {
        ObservableList<ChatDto> groupList = Model.getInstance().getGroupList();
        for (ChatDto chat : groupList) {
            if (chat.getChatID() == chatID) {
                return true;
            }
        }
        return false;
    }

}
