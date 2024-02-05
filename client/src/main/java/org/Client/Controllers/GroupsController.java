package org.Client.Controllers;

import Model.DTO.ContactDto;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

import Model.DTO.ChatDto;
import javafx.util.Callback;
import org.Client.Models.Model;

public class GroupsController implements Initializable {

    public ListView<ChatDto> groupsListView;

    public ObservableList<ChatDto> getGroupList() {
        return groupList;
    }

    public ListProperty<ChatDto> groupListProperty() {
        return new SimpleListProperty<>(getGroupList());
    }

    public void setGroupList(ObservableList<ChatDto> groupList) {
        this.groupList = groupList;
    }

    ObservableList<ChatDto> groupList = Model.getInstance().getGroupList();
    ChatDto chat;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupsListView.setItems(Model.getInstance().getGroupList());

        groupsListView.setCellFactory(new Callback<ListView<ChatDto>, ListCell<ChatDto>>() {
            @Override
            public ListCell<ChatDto> call(ListView<ChatDto> chatListView) {
                return new GroupCellFactory();
            }
        });

        Model.getInstance().getGroupList().addListener((javafx.collections.ListChangeListener.Change<? extends ChatDto> c) -> {
            // Refresh the ListView whenever the ObservableList changes
            Platform.runLater(() ->
                    groupsListView.setItems(Model.getInstance().getGroupList()));
            System.out.println("Group list changed");
            groupsListView.refresh();
        });


        groupsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                chat = groupsListView.getSelectionModel().getSelectedItem();
                getChat(chat);
            }
        });


    }

    private void getMessages(ChatDto chat) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Model.getInstance().getCallBackServicesServer().getPrivateChatMessages(chat.getChatID(), Model.getInstance().getCallBackServicesClient());
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
                    Model.getInstance().getViewFactory().showGroupChatArea(chat);
                });
            }
        });

        // Start the task in a new thread
        new Thread(task).start();
    }

    private void getChat(ChatDto chat) {
        if (Model.getInstance().getPrivateChats().containsKey(chat.getChatID())) {
            System.out.println("Chat exists");
            Model.getInstance().getViewFactory().showGroupChatArea(chat);
        } else {
            System.out.println("Chat does not exist");
            getMessages(chat);


        }
    }
}
