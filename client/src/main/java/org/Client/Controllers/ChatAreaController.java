package org.Client.Controllers;

import Model.DTO.ChatDto;
import Model.DTO.ContactDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChatAreaController implements Initializable {
    @FXML
    private ListView<ContactDto> chatList;

    @FXML
    private TextField searchBar;

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
        searchBar.setPromptText("Search");

        chatList.setItems(Model.getInstance().getContacts());

        chatList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ContactDto chat = chatList.getSelectionModel().getSelectedItem();
                Model.getInstance().getViewFactory().setSelectedChat(chat);
                System.out.println("Selected Item: " + newValue.getContactName());
                Model.getInstance().getViewFactory().showChatArea();
                System.out.println("function called ");
            }
        });


    }

    public void getContactList() {
        //get a list of contacts for this user : the contacts are of type user -- aka from the useraccounts table
        //here I'm using a chat object to represent each contact
        //the image should be an array of byte and should be converted here

    }
}
