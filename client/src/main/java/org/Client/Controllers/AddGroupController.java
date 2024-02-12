package org.Client.Controllers;

import Model.DTO.ContactDto;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
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

public class AddGroupController implements Initializable {
    byte[] grpImage;
    @FXML
    public ImageView userImage;
    @FXML
    public HBox imageViewID;
    @javafx.fxml.FXML
    private TextField textFieldID;
    @javafx.fxml.FXML
    private ListView<ContactDto> contactsLV;
    @javafx.fxml.FXML
    private Button createBtn;

    ArrayList<Integer> selected = new ArrayList<>();

    public void addToSelected(int id) {
        selected.add(id);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactsLV.getItems().addAll(Model.getInstance().getContacts());

        contactsLV.setCellFactory(param -> new AddGroupCellFactory(selected));

        createBtn.setOnAction(event -> {

            System.out.println(textFieldID.getText() + " " + selected.size());
            int adminID = Model.getInstance().getClientId();
            String grpName = textFieldID.getText();
            selected.add(adminID);
            try {
                if (grpName.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("The group name is empty!");
                    alert.show();
                } else
                    Model.getInstance().getCallBackServicesServer().createGroupChat(adminID, grpName, selected, grpImage);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            ((Popup) createBtn.getScene().getWindow()).hide();

        });
        imageViewID.setOnMouseClicked(mouseEvent -> {
            changePic();
        });
        System.out.println("gowa el init");
    }

    private void changePic() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a profile picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File image = fileChooser.showOpenDialog(null);
        try {
            System.out.println("ay 5ara");
            grpImage = ImageServices.convertToByte(ImageIO.read(image));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userImage.setImage(ImageServices.convertToImage(grpImage));

    }

}