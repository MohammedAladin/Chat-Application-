package org.Client.Controllers;

import Interfaces.RemoteRegistrationService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public TextField nameField;
    public TextField phoneNumberField;

    public TextField emailField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public DatePicker dateOfBirthPicker;
    public RadioButton maleRadioButton;
    public ComboBox<String> countryComboBox;
    public Button registerButton;
    RemoteRegistrationService registrationService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            registrationService = (RemoteRegistrationService) registry.lookup("RegistrationServices");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
        registerButton.setOnAction(e -> handleRegistration());
    }

    private void handleRegistration() {
        try {
            int reg = registrationService.registerUser(
                    phoneNumberField.getText(),
                    emailField.getText(),
                    nameField.getText(),
                    passwordField.getText(),
                    confirmPasswordField.getText(),
                    Date.valueOf(dateOfBirthPicker.getValue()),
                    maleRadioButton.isSelected() ? "Male" : "Female",
                    countryComboBox.getValue()

            );

            if(reg==0){
                showAlert("User Already Exists", Alert.AlertType.WARNING);
            }
            else if(reg == 1){
                showAlert("User Added Successfully", Alert.AlertType.INFORMATION);
            }
            else{
                showAlert("UserServices Failed", Alert.AlertType.ERROR);
            }
        } catch (RemoteException | NullPointerException e) {
            showAlert("Please Fill All Fields", Alert.AlertType.ERROR);
        }


        nameField.clear();
        phoneNumberField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    private void showAlert(String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("User Services");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
