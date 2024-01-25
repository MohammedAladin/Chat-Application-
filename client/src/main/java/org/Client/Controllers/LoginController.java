package org.Client.Controllers;
import Interfaces.RemoteLoginService;
import Model.DTO.UserLoginDTO;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.Client.Models.Model;


import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
    private void handleLoginResult(int loginResult) {
        if (loginResult == 0) {
            remoteServiceHandler.showAlert("Login Successful", Alert.AlertType.INFORMATION);
        } else if (loginResult == 1) {
            remoteServiceHandler.showAlert("Invalid Phone Number or Password", Alert.AlertType.WARNING);
        } else {
            remoteServiceHandler.showAlert("User Services Failed", Alert.AlertType.ERROR);
        }
    }
    private void handleException(String message, Exception exception) {
        remoteServiceHandler.showAlert(message + ": " + exception.getMessage(), Alert.AlertType.ERROR);
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
