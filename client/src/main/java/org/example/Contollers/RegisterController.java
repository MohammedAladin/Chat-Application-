package org.example.Contollers;

import Interfaces.Registration;
import Model.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public TextField phone_field;
    public TextField name_field;
    public TextField email_field;
    public PasswordField password_field;
    public Button register_button;
    private Registration userService;
// Added password field




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            try {
                Registry registry = LocateRegistry.getRegistry("localhost", 1100);
                userService = (Registration) registry.lookup("RegistrationService");
            } catch (RemoteException | NotBoundException e) {
                throw new RuntimeException(e);
            }

            register_button.setOnAction(e->handleRegistration());

        }


    private void handleRegistration() {
        String phoneNumber = phone_field.getText();
        String displayName = name_field.getText();
        String email = email_field.getText();
        String password = password_field.getText(); // Retrieve password

        if (phoneNumber.isEmpty() || displayName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("User Registration", "Please fill in all fields", Alert.AlertType.ERROR);
            return;
        }

        if (!isEmailValid(email) || !isPhoneNumberValid(phoneNumber)) {
            showAlert("User Registration", "Please enter a valid phone number or email", Alert.AlertType.ERROR);
            return;
        }

        User newUser = new User(phoneNumber, displayName, email, password, "Female", "Canada", new java.util.Date(), "A short bio", "Online", "Available");

        try {
            if (userService.existsById(newUser.getPhoneNumber())) {
                showAlert("User Registration", "User Already Exists", Alert.AlertType.WARNING);
            } else {
                userService.registerUser(newUser);
                showAlert("User Registration", "User Added Successfully", Alert.AlertType.INFORMATION);
                clearFields();
            }
        } catch (SQLException | RemoteException e) {
            System.out.println(e.getMessage());
            showAlert("User Registration", "Registration Failed", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        phone_field.clear();
        name_field.clear();
        email_field.clear();
    }

    private boolean isEmailValid(String email) {
        // Implement email validation logic (e.g., regex or library)
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // Implement phone number validation logic (e.g., regex)
        return phoneNumber.matches("[0-9]+");
    }

    }

