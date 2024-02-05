package org.Client.Controllers;

import Model.DTO.ChatDto;
import Model.DTO.ContactDto;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
import java.rmi.NotBoundException;
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
    ContactDto chat;

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

        chatList.setOnMouseClicked(event -> {
            chat = chatList.getSelectionModel().getSelectedItem();
            if (chat != null) {
                getChat(chat);
                System.out.println("Selected Item: " + chat.getContactName());
                System.out.println("Chat ID " + chat.getChatId());
                Model.getInstance().getViewFactory().setSelectedChat(chat);
                System.out.println("function called ");
            }
        });


    }

    private void getChat(ContactDto chat) {
        if (Model.getInstance().getPrivateChats().containsKey(chat.getChatId())) {
            System.out.println("Chat exists");
            Model.getInstance().getViewFactory().showChatArea(chat);
        } else {
            System.out.println("Chat does not exist");
                getMessages(chat.getChatId());


        }
    }

    private void getMessages(Integer chatId) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Model.getInstance().getCallBackServicesServer().getPrivateChatMessages(chatId, Model.getInstance().getCallBackServicesClient());
                return null;
            }
        };

        // Handle any exceptions that occurred in the task
        task.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Throwable ex = newValue;
                System.out.println("Exception occurred in task: " + ex);
            }
        });

        // Update the UI after the task has completed
        task.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == javafx.concurrent.Worker.State.SUCCEEDED) {
                Platform.runLater(() -> {
                    Model.getInstance().getViewFactory().showChatArea(chat);
                });
            }
        });

        // Start the task in a new thread
        new Thread(task).start();
    }
}



