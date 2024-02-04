package org.Client.Controllers;

import Model.DTO.ContactDto;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;
import java.util.ArrayList;

public class AddGroupCellFactory extends ListCell<ContactDto> {

    private ArrayList<Integer> selected;
    public AddGroupCellFactory(ArrayList<Integer> selected) {
        this.selected = selected;
    }
    @Override
    protected void updateItem(ContactDto contactDto, boolean b) {
        super.updateItem(contactDto, b);
        FXMLLoader fxmlLoader = null;
        if (contactDto == null || b) {
            setGraphic(null);
            setText(null);
        } else {
            try {
                fxmlLoader = new FXMLLoader(getClass().getResource("/ClientFxml/groupLVItems.fxml"));
                GroupLVItemController controller = new GroupLVItemController(selected);
                fxmlLoader.setController(controller);
                setGraphic(fxmlLoader.load());
                controller.setContactDto(contactDto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}