package org.Client.Controllers;

import Model.DTO.ContactDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.Client.Models.Model;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class UserCardController implements Initializable {
    public Button add_btn;
    public ImageView userImage;
    public Label name_label;
    String name;

    @FXML
    public AnchorPane parentPane;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    String phoneNumber;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    Image image;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        name_label.setText(name);
        add_btn.setOnAction(e->addContact(phoneNumber));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addContact(String contact){
        try {
            RemoteServiceHandler.getInstance().getCallbacks().addContact(Model.getInstance().getClientId(),contact);
            System.out.println("Contact added");
            ((VBox)parentPane.getParent()).getChildren().remove(parentPane);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
