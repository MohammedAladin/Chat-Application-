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
import java.util.ResourceBundle;

public class AddContactController implements Initializable {
    public TextField searchField;
    public Text error;
    public VBox vbox;
    public Button find_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.setOnAction(e->search());
        find_btn.setOnAction(e->search());
    }
    public void search(){
        if (validate()){
            //hide the error message
            error.setVisible(false);
            //search for the contact
            ContactDto user= null;
            String phoneNumber=searchField.getText();
            try {
                user = Model.getInstance().getCallBackServicesServer().searchForContact(searchField.getText());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            if(user == null){
                error.setText("User not found, consider inviting them to the platform");
                error.setVisible(true);
                return;
            }
            //show the contact
            AnchorPane contactCard=Model.getInstance().getViewFactory().showUserCard(user,phoneNumber);
            vbox.getChildren().add(contactCard);
        }
        else {
            //show error
            error.setText("Invalid phone number");
            error.setVisible(true);
        }
    }

    private boolean validate(){
        if (searchField.getText().isEmpty()){
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
