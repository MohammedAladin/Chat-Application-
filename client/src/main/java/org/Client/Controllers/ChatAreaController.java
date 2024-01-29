package org.Client.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.util.Callback;
import org.Client.Models.Model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChatAreaController implements Initializable {
    @FXML
    private ListView<Chat> chatList;

    @FXML
    private TextField searchBar;

    private Image image= new Image(getClass().getResource("/ClientImages/cat.jpg").toString());
    private Image image2= new Image(getClass().getResource("/ClientImages/cat2.jpg").toString());
    private ObservableList<Chat> chats= FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatList.setCellFactory(new Callback<ListView<Chat>, ListCell<Chat>>() {
            @Override
            public ListCell<Chat> call(ListView<Chat> chatListView) {
                return new ChatCellFactory();
            }
        });
        searchBar.setPromptText("Search");
        List<Chat> chatslist = new ArrayList<>();
        chatslist.add(new Chat("Hello",image,"Ahmed","online","Hello"));
        chatslist.add(new Chat("Hello",image2,"Nada","online","Hello"));
        chatslist.add(new Chat("what's up",image,"Ali","offline","Hello"));
        chats.addAll(chatslist);
        chatList.setItems(chats);

        chatList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Chat chat = chatList.getSelectionModel().getSelectedItem();
                Model.getInstance().getViewFactory().setSelectedChat(chat);
                System.out.println("Selected Item: " + newValue.getName());
                Model.getInstance().getViewFactory().showChatArea();
                System.out.println("function called ");
            }
        });





    }
}
