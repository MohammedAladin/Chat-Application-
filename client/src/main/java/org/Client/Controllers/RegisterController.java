package org.Client.Controllers;
import Interfaces.RemoteRegistrationService;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
            registrationService = (RemoteRegistrationService) registry.lookup("RegistrationService");

        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
        registerButton.setOnAction(e -> handleRegistration());
    }

    private void handleRegistration() {
        try {
            validateUserInput();
            int reg = registrationService.registerUser(
                    phoneNumberField.getText(),
                    emailField.getText(),
                    nameField.getText(),
                    passwordField.getText(),
                    confirmPasswordField.getText(),
                    Date.valueOf(dateOfBirthPicker.getValue()),
                    maleRadioButton.isSelected() ? "Male" : "Female",
                    countryComboBox.getValue());

            if (reg == 0) {
                showAlert("User Already Exists", Alert.AlertType.WARNING);
            } else if (reg == 1) {
                showAlert("User Added Successfully", Alert.AlertType.INFORMATION);
            } else if (reg == 2) {
                showAlert("Any specific message...", Alert.AlertType.INFORMATION);
            }

        } catch (RemoteException | IllegalArgumentException e) {
            showAlert(e.getMessage(), Alert.AlertType.ERROR);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        nameField.clear();
        phoneNumberField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }
    private void validateUserInput() throws IllegalArgumentException {
        if (nameField.getText().isEmpty() || phoneNumberField.getText().isEmpty() ||
                emailField.getText().isEmpty() || passwordField.getText().isEmpty() ||
                confirmPasswordField.getText().isEmpty() || dateOfBirthPicker.getValue() == null ||
                countryComboBox.getValue() == null || (maleRadioButton.isSelected() && countryComboBox.getValue().isEmpty())) {
            throw new IllegalArgumentException("Please fill in all fields");
        }
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            throw new IllegalArgumentException("Password and Confirm Password do not match");
        }
        if (!isPhoneNumberValid(phoneNumberField.getText())) {
            throw new IllegalArgumentException("Please enter a valid phone number");
        }
        if (dateOfBirthPicker.getValue().isAfter(Date.valueOf(LocalDate.now()).toLocalDate())) {
            throw new IllegalArgumentException("Date of birth must be in the past");
        }
    }
    private void showAlert(String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("User Services");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches("[0-9]+");
    }

}
