package org.Client.Controllers;

import Interfaces.CallBacks.Server.CallBackServicesServer;
import Model.DTO.BlockedContactDTO;
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
import org.Client.Service.ImageServices;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class UserCardController implements Initializable {
    public Button add_btn;
    private boolean isFriend;
    private boolean isBlocked;
    public Button blockButton;
    public ImageView userImage;
    public Label name_label;
    String name;
    public Button unblockButton;


    public AnchorPane parentPane;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isFriend() {
        return isFriend;
    }

    String phoneNumber;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    byte[] image;
    Image defaultImage = new Image(getClass().getResource("/ClientImages/defaultUser.jpg").toString());
    Model model;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = Model.getInstance();
        if (isFriend) {
            add_btn.setVisible(false);
            unblockButton.setVisible(false);
        }
        if(isBlocked){
            blockButton.setVisible(false);
        }
        unblockButton.setOnAction(actionEvent -> unblockContact(phoneNumber));
        blockButton.setOnAction(actionEvent -> blockContact(phoneNumber));
        unblockButton.setOnAction(actionEvent -> unblockContact(phoneNumber));
        name_label.setText(name);
        add_btn.setOnAction(e -> addContact(phoneNumber));
        if (image == null) {
            userImage.setImage(defaultImage);
        } else {
            userImage.setImage(ImageServices.convertToImage(image));
        }
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setFriend(boolean friend) {
        isFriend = friend;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public void addContact(String contact) {
        try {
            RemoteServiceHandler.getInstance().getCallbacks().addContact(Model.getInstance().getClientId(), contact);
            System.out.println("Contact added");
            ((VBox) parentPane.getParent()).getChildren().remove(parentPane);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    private void blockContact (String phoneNumber){
        System.out.println("before calling the server");
        try {
            CallBackServicesServer server = model.getCallBackServicesServer();
            BlockedContactDTO blockedContactDTO = new BlockedContactDTO(model.getClientId(), phoneNumber);
            server.blockContact(blockedContactDTO);
            ((VBox) parentPane.getParent()).getChildren().remove(parentPane);

            System.out.println("after calling the server");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void unblockContact (String phoneNumber){
        BlockedContactDTO blockedContactDTO = new BlockedContactDTO(model.getClientId(), phoneNumber);
        blockButton.setVisible(true);
        unblockButton.setVisible(false);
        add_btn.setVisible(true);

        try {
            RemoteServiceHandler.getInstance().getBlockedContactsService().unblock(blockedContactDTO);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
