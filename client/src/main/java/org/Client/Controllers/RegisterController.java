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
        countryComboBox.getItems().addAll("Egypt", "USA", "UK", "Germany", "France", "Italy", "Spain", "China", "Japan", "Korea", "Brazil", "Argentina", "Australia", "Canada", "Russia", "India", "South Africa", "Nigeria", "Kenya", "Ghana", "Morocco", "Tunisia", "Saudi Arabia", "UAE", "Qatar", "Kuwait", "Bahrain", "Oman", "Jordan", "Lebanon");
        remoteServiceHandler = RemoteServiceHandler.getInstance();
        registerButton.setOnAction(e -> handleRegistration());
        alreadyAUser.setOnMouseClicked(mouseEvent -> Model.getInstance().getViewFactory().showLoginWindow());

    }
    private void countryPhoneRulesInitialization(){
        countryPhoneRules.put("United States", "^\\d{10}$");
        countryPhoneRules.put("Canada", "^\\(?[\\d]{3}\\)?[\\s-]?[\\d]{3}[\\s-]?[\\d]{4}$");
        countryPhoneRules.put("Egypt", "^01[0125][0-9]{8}$");
        countryPhoneRules.put("United Kingdom", "^\\+?44\\s?\\d{10}$");
        countryPhoneRules.put("Germany", "^\\+?49\\s?\\d{10,14}$");
        countryPhoneRules.put("France", "^\\+?33\\s?\\d{9}$");
        countryPhoneRules.put("Australia", "^\\+?61\\s?\\d{9}$");
        countryPhoneRules.put("India", "^\\+?91\\s?\\d{10}$");
        countryPhoneRules.put("Italy", "^\\+?39\\s?\\d{10}$");
        countryPhoneRules.put("Spain", "^\\+?34\\s?\\d{9}$");
        countryPhoneRules.put("China", "^\\+?86\\s?\\d{11}$");
        countryPhoneRules.put("Japan", "^\\+?81\\s?\\d{10}$");
        countryPhoneRules.put("Korea", "^\\+?82\\s?\\d{10}$");
        countryPhoneRules.put("Brazil", "^\\+?55\\s?\\d{11}$");
        countryPhoneRules.put("Argentina", "^\\+?54\\s?\\d{10}$");
        countryPhoneRules.put("Russia", "^\\+?7\\s?\\d{10}$");
        countryPhoneRules.put("South Africa", "^\\+?27\\s?\\d{9}$");
        countryPhoneRules.put("Nigeria", "^\\+?234\\s?\\d{10}$");
        countryPhoneRules.put("Kenya", "^\\+?254\\s?\\d{9}$");
        countryPhoneRules.put("Ghana", "^\\+?233\\s?\\d{9}$");
        countryPhoneRules.put("Morocco", "^\\+?212\\s?\\d{9}$");
        countryPhoneRules.put("Tunisia", "^\\+?216\\s?\\d{8}$");
        countryPhoneRules.put("Saudi Arabia", "^\\+?966\\s?\\d{9}$");
        countryPhoneRules.put("UAE", "^\\+?971\\s?\\d{9}$");
        countryPhoneRules.put("Qatar", "^\\+?974\\s?\\d{8}$");
        countryPhoneRules.put("Kuwait", "^\\+?965\\s?\\d{8}$");
        countryPhoneRules.put("Bahrain", "^\\+?973\\s?\\d{8}$");
        countryPhoneRules.put("Oman", "^\\+?968\\s?\\d{8}$");
        countryPhoneRules.put("Jordan", "^\\+?962\\s?\\d{9}$");
        countryPhoneRules.put("Lebanon", "^\\+?961\\s?\\d{7}$");
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

            boolean registrationResult = remoteServiceHandler.getRemoteUserService().registerUser(userRegistrationDTO);
            handleRegistrationResult(registrationResult);
            Model.getInstance().getViewFactory().showLoginWindow();

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
            remoteServiceHandler.showAlert("User Already Exists,Consider Logging in", Alert.AlertType.INFORMATION);
        } else {
            remoteServiceHandler.showAlert("Sign Up Successfully", Alert.AlertType.INFORMATION);
        }
    }

}
