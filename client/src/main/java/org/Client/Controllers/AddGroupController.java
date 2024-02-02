package org.Client.Controllers;

import Model.DTO.ContactDto;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.Client.Models.Model;
import org.Client.Service.ImageServices;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddGroupController implements Initializable
{
    byte[] grpImage;
    @FXML
    public ImageView userImage;
    @FXML
    public HBox imageViewID;
    @javafx.fxml.FXML
    private TextField textFieldID;
    @javafx.fxml.FXML
    private ListView <ContactDto>contactsLV;
    @javafx.fxml.FXML
    private Button createBtn;

    ArrayList<Integer> selected = new ArrayList<>();
@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    contactsLV.getItems().addAll(Model.getInstance().getContacts());

    contactsLV.setCellFactory(CheckBoxListCell.forListView(new Callback<ContactDto, ObservableValue<Boolean>>() {
        @Override
        public ObservableValue<Boolean> call(ContactDto contact) {
            BooleanProperty observable = new SimpleBooleanProperty();
            observable.addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    System.out.println("Check box for "+contact.getContactName()+" changed from "+wasSelected+" to "+isNowSelected);
                    selected.add(contact.getContactID());
                    System.out.println(selected.size());
                }
                if (wasSelected){
                    System.out.println("Check box for "+contact.getContactName()+" changed from "+wasSelected+" to "+isNowSelected);
                    selected.remove(contact);
                    System.out.println(selected.size());
                }
            });
            return observable;
        }
    }, new StringConverter<ContactDto>() {
        @Override
        public String toString(ContactDto object) {
            return object.getContactName();
        }

        @Override
        public ContactDto fromString(String string) {
            return null; // not used
        }
    }));

    createBtn.setOnAction(event -> {
        System.out.println(textFieldID.getText() + " " +selected.size());
        int adminID = Model.getInstance().getClientId();
        String grpName = textFieldID.getText();
        selected.add(adminID);
        try {
            Model.getInstance().getCallBackServicesServer().createGroupChat(adminID,grpName , selected , grpImage);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    });
    imageViewID.setOnMouseClicked(mouseEvent -> {
        changePic();
    });
}
    private void changePic() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a profile picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        File image = fileChooser.showOpenDialog(imageViewID.getScene().getWindow());
        try {
            grpImage = ImageServices.convertToByte(ImageIO.read(image));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userImage.setImage(ImageServices.convertToImage(grpImage));

    }
}