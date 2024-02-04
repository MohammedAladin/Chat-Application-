package org.Client.Controllers;

import Model.DTO.ContactDto;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class GroupLVItemController {
    @javafx.fxml.FXML
    private ImageView contactImage;
    @javafx.fxml.FXML
    private Label contactName;
    @javafx.fxml.FXML
    private CheckBox checkBoxID;
    private ContactDto contactDto;
    private ArrayList<Integer> selected;

    public ImageView getContactImage() {
        return contactImage;
    }
    public GroupLVItemController(ArrayList<Integer> selected) {
        this.selected = selected;
    }
    public void setContactImage(ImageView contactImage) {
        this.contactImage = contactImage;
    }

    public Label getContactName() {
        return contactName;
    }

    public void setContactName(Label contactName) {
        this.contactName = contactName;
    }

    public CheckBox getCheckBoxID() {
        return checkBoxID;
    }

    public void setCheckBoxID(CheckBox checkBoxID) {
        this.checkBoxID = checkBoxID;
    }


    public void setContactDto(ContactDto contactDto) {
        this.contactDto = contactDto;
        contactName.setText(contactDto.getContactName());
        checkBoxID.setOnAction(actionEvent -> {
            if (checkBoxID.isSelected()) selected.add(contactDto.getContactID());
        });
    }

}
