package org.Client.Controllers;
import Model.DTO.UserRegistrationDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import org.Client.Models.Model;

import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
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

    @FXML
    Label alreadyAUser;
    private Map<String, String> countryPhoneRules = new HashMap<>();
    public RemoteServiceHandler remoteServiceHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryPhoneRulesInitialization();

        remoteServiceHandler = RemoteServiceHandler.getInstance();
        registerButton.setOnAction(e -> handleRegistration());
        alreadyAUser.setOnMouseClicked(mouseEvent -> Model.getInstance().getViewFactory().showLoginWindow());

    }
    private void countryPhoneRulesInitialization(){
        countryPhoneRules.put("United States", "^\\d{10}$");
        countryPhoneRules.put("Canada", "^\\(?[\\d]{3}\\)?[\\s-]?[\\d]{3}[\\s-]?[\\d]{4}$");
        countryPhoneRules.put("Egypt", "^01[0125][0-9]{8}$");
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
            Model.getInstance().getViewFactory().showLoginWindow();

            boolean registrationResult = remoteServiceHandler.getRemoteUserService().registerUser(userRegistrationDTO);
            handleRegistrationResult(registrationResult);

        } catch (IllegalArgumentException e) {
            remoteServiceHandler.showAlert(e.getMessage(), Alert.AlertType.ERROR);
        } catch (RemoteException e) {
            handleException(e);
        } finally {
           // clearRegistrationFields();
        }
    }
    private void handleException(Exception exception) {
        remoteServiceHandler.showAlert("Error during registration" + ": " + exception.getMessage(), Alert.AlertType.ERROR);
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
    private boolean isPhoneNumberValid(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        String selectedCountry = countryComboBox.getValue();
        if (selectedCountry == null || selectedCountry.isEmpty()) {
            return false;
        }

        String phoneNumberRegex = countryPhoneRules.get(selectedCountry);
        if (phoneNumberRegex == null) {
            return false;
        }


        return phoneNumber.matches(phoneNumberRegex);
    }
    private void clearRegistrationFields() {
        nameField.clear();
        phoneNumberField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        dateOfBirthPicker.setValue(null);
    }
    private void handleRegistrationResult(boolean registrationResult) {
        if (!registrationResult) {
            remoteServiceHandler.showAlert("User is Already Existed", Alert.AlertType.INFORMATION);
        } else {
            remoteServiceHandler.showAlert("Sign Up Successfully", Alert.AlertType.INFORMATION);
        }
    }

}
