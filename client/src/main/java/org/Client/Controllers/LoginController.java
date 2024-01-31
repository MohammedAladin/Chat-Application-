package org.Client.Controllers;
import Interfaces.CallBacks.Client.CallBackServicesClient;
import Interfaces.CallBacks.Server.CallBackServicesServer;
import Model.DTO.UserLoginDTO;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.Client.Models.Model;
import org.Client.Service.ClientServicesImp;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label registerLabel;
    public Button signingButton;
    public PasswordField passwordField;
    public TextField phoneField;
    private RemoteServiceHandler remoteServiceHandler;
    String phoneNumber;
    private CallBackServicesClient callBackServicesClient;
    private CallBackServicesServer callBackServicesServer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        remoteServiceHandler = RemoteServiceHandler.getInstance();
        Model.getInstance().getViewFactory().serverAnnouncementProperty().addListener((observable, newValue, oldValue)->{
            remoteServiceHandler.showAlert(oldValue, Alert.AlertType.INFORMATION);
        } );
        signingButton.setOnAction((e)->handleSignIn());
        registerLabel.setOnMouseClicked(e-> Model.getInstance().getViewFactory().showRegistrationWindow());
    }

    private void handleSignIn() {
        try {
            validateUserInputLogin();

            phoneNumber = phoneField.getText();
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
            try {
                remoteServiceHandler.showAlert("Login Successful", Alert.AlertType.INFORMATION);

                Model.getInstance().setCallBackServicesClient(new ClientServicesImp());// client representation to be sent.
                callBackServicesServer =  remoteServiceHandler.getCallbacks();
                Model.getInstance().setCallBackServicesServer(callBackServicesServer);
                Model.getInstance().getViewFactory().showHomePage(signingButton);
                callBackServicesServer.register(Model.getInstance().getCallBackServicesClient(), phoneNumber);

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }


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
