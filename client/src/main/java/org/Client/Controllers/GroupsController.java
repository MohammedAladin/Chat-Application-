package org.Client.Controllers;

import Model.DTO.ContactDto;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
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
        groupListProperty().bind(Model.getInstance().groupListProperty());


        groupsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ChatDto chat = groupsListView.getSelectionModel().getSelectedItem();
                System.out.println("Selected Item: " + newValue.getChatName());
            }
        });


    }
}
