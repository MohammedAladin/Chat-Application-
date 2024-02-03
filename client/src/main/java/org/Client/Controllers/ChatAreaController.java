package org.Client.Controllers;

import Model.DTO.ChatDto;
import Model.DTO.ContactDto;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.util.Callback;
import org.Client.ClientEntities.Chat;
import org.Client.Models.Model;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChatAreaController implements Initializable {
    @FXML
    private ListView<ContactDto> chatList;

    //@FXML
   // private TextField searchBar;

    private Image image = new Image(getClass().getResource("/ClientImages/cat.jpg").toString());
    private Image image2 = new Image(getClass().getResource("/ClientImages/cat2.jpg").toString());
    private ObservableList<ChatDto> chats = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatList.setCellFactory(new Callback<ListView<ContactDto>, ListCell<ContactDto>>() {
            @Override
            public ListCell<ContactDto> call(ListView<ContactDto> chatListView) {
                return new ChatCellFactory();
            }
        });
        //searchBar.setPromptText("Search");
        Model.getInstance().getContacts().addListener((javafx.collections.ListChangeListener.Change<? extends ContactDto> c) -> {
            // Refresh the ListView whenever the ObservableList changes
            Platform.runLater(() -> chatList.refresh());
        });

        chatList.setItems(Model.getInstance().getContacts());

        chatList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                getChat(newValue.getChatId());
                ContactDto chat = chatList.getSelectionModel().getSelectedItem();
                System.out.println("Selected Item: " + newValue.getContactName());
                System.out.println("Chat ID " + newValue.getChatId());
                Model.getInstance().getViewFactory().setSelectedChat(chat);
                Model.getInstance().getViewFactory().showChatArea(newValue);
                System.out.println("function called ");
            }
        });


    }

    private void getChat(Integer chatId) {
        if(Model.getInstance().getPrivateChats().containsKey(chatId)){
            System.out.println("Chat exists");
        }else{
            System.out.println("Chat does not exist");
            try {
                Model.getInstance().getCallBackServicesServer().getPrivateChatMessages(chatId,Model.getInstance().getCallBackServicesClient());
               // System.out.println(Model.getInstance().getPrivateChats().get(11)+"this is the chat");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

