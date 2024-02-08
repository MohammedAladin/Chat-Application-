package org.Client.Controllers;

import Interfaces.RmiServices.BlockedContactsInterface;
import Model.DTO.BlockedContactDTO;
import Model.DTO.ContactDto;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.Client.Models.Model;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class AddContactController implements Initializable {
    public TextField searchField;
    public Text error;
    public VBox vbox;
    public Button find_btn;
    public boolean isFriend = false;
    public boolean isBlocked = false;
    boolean sentBefore = false;
    private BlockedContactsInterface blockedContactsService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.setOnAction(e -> search());
        find_btn.setOnAction(e -> search());


    }

    public void search() {
        if (validate()) {
            blockedContactsService = RemoteServiceHandler.getInstance().getBlockedContactsService();
            //hide the error message
            error.setVisible(false);
            //search for the contact
            ContactDto user = null;
            String phoneNumber = searchField.getText();
            try {
                user = Model.getInstance().getCallBackServicesServer().searchForContact(searchField.getText());

                if (user == null) {
                    error.setText("User not found, consider inviting them to the platform");
                    error.setVisible(true);
                    return;
                } else {
                    BlockedContactDTO blockedContactDTO = new BlockedContactDTO(Model.getInstance().getClientId(), phoneNumber);
                    if (blockedContactsService.getIdIfUserIsBlocking(blockedContactDTO) != -1) {
                        isBlocked = true;
                    }
                    else{
                        blockedContactDTO = new BlockedContactDTO(user.getContactID(), Model.getInstance().getPhoneNumber());
                        if (blockedContactsService.getIdIfUserIsBlocking(blockedContactDTO) != -1) {
                            error.setText("Sorry, You can't get this user.");
                            error.setVisible(true);
                            return;
                        }
                    }
                    sentBefore = Model.getInstance().getCallBackServicesServer().checkIfSent(user.getContactID(), Model.getInstance().getClientId());
                    if (user.getContactID().equals(Model.getInstance().getClientId())) {

                        error.setText("You can't add yourself");
                        error.setVisible(true);
                        return;
                    }
                    List<ContactDto> contctList = Model.getInstance().getContacts();
                    for (ContactDto contactDto : contctList) {
                        if (contactDto.getContactID().equals(user.getContactID())) {
                            isFriend = true;
                        }
                    }
                }
                //show the contact
                AnchorPane contactCard = Model.getInstance().getViewFactory().showUserCard(user, phoneNumber, isFriend, isBlocked,sentBefore);
                vbox.getChildren().add(contactCard);
                isBlocked= false;
                isFriend = false;
                sentBefore = false;
            }catch (RemoteException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection Error");
                alert.setContentText("Sorry, we couldn't connect to the server. Please check your connection and try again later.");
                alert.show();
                e.printStackTrace();
            }
        }    else{
            //show error
            error.setText("Invalid phone number");
            error.setVisible(true);
        }
    }

    private boolean validate() {
        if (searchField.getText().isEmpty()) {
            return false;
        } else if (searchField.getText().length() < 11) {
            return false;
        } else if (searchField.getText().length() > 11) {
            return false;
        } else if (!searchField.getText().matches("[0-9]+")) {
            return false;
        }
        return true;
    }

}
