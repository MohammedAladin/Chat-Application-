// AuthController.java
package org.Client.Controllers;

import Model.DTO.UserLoginDTO;
import Model.DTO.UserRegistrationDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AuthController implements Initializable {
    @FXML
    public Label registerLabel;
    @FXML
    public Button signingButton;
    @FXML
    public PasswordField passwordLoginField;
    @FXML
    public TextField phoneLoginField;
    @FXML
    public Pane loginPage;
    @FXML
    public TextField nameField;
    @FXML
    public TextField phoneNumberField;
    @FXML
    public TextField emailField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField confirmPasswordField;
    @FXML
    public DatePicker dateOfBirthPicker;
    @FXML
    public RadioButton maleRadioButton;
    @FXML
    public ComboBox<String> countryComboBox;
    @FXML
    public Button registerButton;

    private RemoteServiceHandler remoteServiceHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        remoteServiceHandler = RemoteServiceHandler.getInstance();
        signingButton.setOnAction((e) -> handleSignIn());
        registerButton.setOnAction((e) -> handleRegistration());
        registerLabel.setOnMouseClicked(e -> loginPage.toFront());
    }

    private void handleSignIn() {
        try {
            validateUserInputLogin();

            String phoneNumber = phoneLoginField.getText();
            String password = passwordLoginField.getText();
            UserLoginDTO userLogin = new UserLoginDTO(phoneNumber, password);

            int loginResult = remoteServiceHandler.getRemoteLoginService().loginUser(userLogin);
            handleLoginResult(loginResult);

        }catch (IllegalArgumentException e) {
            remoteServiceHandler.showAlert(e.getMessage(), Alert.AlertType.ERROR);
        } catch (RemoteException e) {
            handleException("Error during login", e);
        } finally {
            clearLoginFields();
        }
    }

    private void handleRegistration() {
        try {
            validateUserInput();

            String phoneNumber = phoneNumberField.getText();
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            Date dateOfBirth = Date.valueOf(dateOfBirthPicker.getValue());
            String gender = maleRadioButton.isSelected() ? "Male" : "Female";
            String country = countryComboBox.getValue();

            UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(
                    phoneNumber, name, email, password, gender, country, dateOfBirth
            );

            int registrationResult = remoteServiceHandler.getRegistrationService().registerUser(userRegistrationDTO);
            handleRegistrationResult(registrationResult);

        } catch (IllegalArgumentException e) {
            remoteServiceHandler.showAlert(e.getMessage(), Alert.AlertType.ERROR);
        } catch (RemoteException | SQLException e) {
            handleException("Error during registration", e);
        } finally {
            clearRegistrationFields();
        }
    }

    private void handleLoginResult(int loginResult) {
        if (loginResult == 0) {
            remoteServiceHandler.showAlert("Login Successful", Alert.AlertType.INFORMATION);
        } else if (loginResult == 1) {
            remoteServiceHandler.showAlert("Invalid Phone Number or Password", Alert.AlertType.WARNING);
        } else {
            remoteServiceHandler.showAlert("User Services Failed", Alert.AlertType.ERROR);
        }
    }

    private void handleRegistrationResult(int registrationResult) {
        if (registrationResult == 0) {
            remoteServiceHandler.showAlert("User is Already Existed", Alert.AlertType.INFORMATION);
        } else if (registrationResult == 1) {
            remoteServiceHandler.showAlert("Sign Up Successfully", Alert.AlertType.INFORMATION);
        }
    }

    private void handleException(String message, Exception exception) {
        remoteServiceHandler.showAlert(message + ": " + exception.getMessage(), Alert.AlertType.ERROR);
    }

    private void validateUserInput() {
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
    private void validateUserInputLogin() {
        if (phoneLoginField.getText().isEmpty() || passwordLoginField.getText().isEmpty()) {
            throw new IllegalArgumentException("Please enter both phone number and password");
        }
        if (!isPhoneNumberValid(phoneLoginField.getText())) {
            throw new IllegalArgumentException("Please enter a valid phone number");
        }
    }
    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches("[0-9]+");
    }

    private void clearLoginFields() {
        phoneLoginField.clear();
        passwordLoginField.clear();
    }

    private void clearRegistrationFields() {
        nameField.clear();
        phoneNumberField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        dateOfBirthPicker.setValue(null);
    }
}
