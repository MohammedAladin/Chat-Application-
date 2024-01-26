package org.Client.Controllers;
import Model.DTO.UserLoginDTO;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.Client.Models.Model;


import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label registerLabel;
    public Button signingButton;
    public PasswordField passwordField;
    public TextField phoneField;
    private RemoteServiceHandler remoteServiceHandler;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        remoteServiceHandler = RemoteServiceHandler.getInstance();
        signingButton.setOnAction((e)->handleSignIn());
        registerLabel.setOnMouseClicked(e-> Model.getInstance().getViewFactory().showRegistrationWindow());
    }

    private void handleSignIn() {
        try {
            validateUserInputLogin();

            String phoneNumber = phoneField.getText();
            String password = passwordField.getText();
            UserLoginDTO userLogin = new UserLoginDTO(phoneNumber, password);

            boolean loginResult = remoteServiceHandler.getRemoteUserService().signInUser(userLogin);
            handleLoginResult(loginResult);

        }catch (IllegalArgumentException e) {
            remoteServiceHandler.showAlert(e.getMessage(), Alert.AlertType.ERROR);
        } catch (RemoteException e) {
            handleException(e);
        } finally {
            clearLoginFields();
        }
    }
    private void handleLoginResult(boolean loginResult) {
        if (loginResult) {
            remoteServiceHandler.showAlert("Login Successful", Alert.AlertType.INFORMATION);
        } else {
            remoteServiceHandler.showAlert("Invalid Phone Number or Password", Alert.AlertType.WARNING);
        }
    }
    private void handleException(Exception exception) {
        remoteServiceHandler.showAlert("Error during login" + ": " + exception.getMessage(), Alert.AlertType.ERROR);
    }
    private void validateUserInputLogin() {
        if (phoneField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            throw new IllegalArgumentException("Please enter both phone number and password");
        }
        if (!isPhoneNumberValid(phoneField.getText())) {
            throw new IllegalArgumentException("Please enter a valid phone number");
        }
    }
    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches("[0-9]+");
    }

    private void clearLoginFields() {
        phoneField.clear();
        passwordField.clear();
    }
}
