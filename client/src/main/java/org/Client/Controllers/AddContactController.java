package org.Client.Controllers;

import Model.DTO.ContactDto;
import javafx.fxml.Initializable;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.setOnAction(e -> search());
        find_btn.setOnAction(e -> search());

    }

    public void search() {
        if (validate()) {
            //hide the error message
            error.setVisible(false);
            //search for the contact
            ContactDto user = null;
            String phoneNumber = searchField.getText();
            try {
                user = Model.getInstance().getCallBackServicesServer().searchForContact(searchField.getText());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            if (user == null) {
                error.setText("User not found, consider inviting them to the platform");
                error.setVisible(true);
                return;
            } else {
                if (user.getContactID().equals(Model.getInstance().getClientId())) {
                    error.setText("You can't add yourself");
                    error.setVisible(true);
                    return;
                }
                List<ContactDto> contctList = Model.getInstance().getContacts();
                for (ContactDto contactDto : contctList) {
                    if (contactDto.getContactID().equals(user.getContactID())) {
                        error.setText("User already in your contact list");
                        error.setVisible(true);
                        isFriend = true;
                        return;
                    }
                }

            }

            //show the contact
            AnchorPane contactCard = Model.getInstance().getViewFactory().showUserCard(user, phoneNumber, isFriend);
            vbox.getChildren().add(contactCard);
        } else {
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
