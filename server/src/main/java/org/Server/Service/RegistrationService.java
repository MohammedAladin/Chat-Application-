package org.Server.Service;

import Interfaces.UserServices;
import Model.Entities.User;
import javafx.scene.control.Alert;

public class RegistrationService {

    private static UserServices userService;
    private static  RegistrationService registrationService;
    private RegistrationService(){}
    public static RegistrationService getInstance(UserServices userService){
        if(registrationService == null){
            registrationService = new RegistrationService();
            RegistrationService.userService = userService;
        }
        return registrationService;
    }
    public void registerUser(String phoneNumber, String displayName, String email, String password) {
        try {
            validateUserInput(phoneNumber, displayName, email, password);

            User newUser = new User(phoneNumber, displayName, email, password, "Female", "Canada",
                    new java.util.Date(), "A short bio", "Online", "Available");

            if (userService.existsById(newUser.getPhoneNumber())) {
                showAlert("User Already Exists", Alert.AlertType.WARNING);
            } else {
                userService.registerUser(newUser);
                showAlert("User Added Successfully", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            showAlert("UserServices Failed", Alert.AlertType.ERROR);
        }
    }

    private void validateUserInput(String phoneNumber, String displayName, String email, String password)
            throws IllegalArgumentException {
        if (phoneNumber.isEmpty() || displayName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Please fill in all fields");
        }

        if (!isEmailValid(email) || !isPhoneNumberValid(phoneNumber)) {
            throw new IllegalArgumentException("Please enter a valid phone number or email");
        }
    }

    private void showAlert(String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("User UserServices");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean isEmailValid(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches("[0-9]+");
    }
}

