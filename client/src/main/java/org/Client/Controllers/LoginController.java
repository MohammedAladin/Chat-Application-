package org.Client.Controllers;
import Interfaces.CallBacks.Client.CallBackServicesClient;
import Interfaces.CallBacks.Server.CallBackServicesServer;
import Model.DTO.UserLoginDTO;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.Client.Models.Model;
import org.Client.Service.ClientServicesImp;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label registerLabel;
    public Label phoneNumberLabel;
    public Label passwordLabel;
    public Label notAUser;
    public Button signingButton;
    public Button proceedButton;
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

        passwordLabel.setVisible(false);
        passwordField.setVisible(false);
        signingButton.setVisible(false);

    }

    private void handleSignIn() {
        try {
//            validateUserInputLogin();

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

    @FXML
    private void phoneNumberExists(){
        phoneNumber = phoneField.getText();
        UserLoginDTO userLogin = new UserLoginDTO(phoneNumber, "");
        try {
            boolean phoneNumberExists = remoteServiceHandler.getRemoteUserService().existsByUserLoginDTO(userLogin);
            if (phoneNumberExists) {
                remoteServiceHandler.showAlert("Phone number exists. Please enter the corresponding password!" , Alert.AlertType.INFORMATION);
                showPasswordField();
            } else {
                remoteServiceHandler.showAlert("Phone number does not exist. Please enter a valid phone number." , Alert.AlertType.INFORMATION);
//                System.exit(0);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void showPasswordField() {
        phoneNumberLabel.setVisible(false);
        phoneField.setVisible(false);
        proceedButton.setVisible(false);

        passwordLabel.setVisible(true);
        passwordField.setVisible(true);
        signingButton.setVisible(true);
    }

    private void handleLoginResult(boolean loginResult) {
        if (loginResult) {
            try {
                remoteServiceHandler.showAlert("Login Successful", Alert.AlertType.INFORMATION);

                Model.getInstance().setCallBackServicesClient(new ClientServicesImp());// client representation to be sent.
                callBackServicesServer =  remoteServiceHandler.getCallbacks();
                Model.getInstance().setCallBackServicesServer(callBackServicesServer);
                setData();

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }


        } else {
            remoteServiceHandler.showAlert("Invalid Phone Number or Password", Alert.AlertType.WARNING);
        }
    }

    private void setData() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                callBackServicesServer.register(Model.getInstance().getCallBackServicesClient(), phoneNumber);
                return null;
            }
        };

        // Handle any exceptions that occurred in the task
        task.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Throwable ex = newValue;
                System.out.println("Exception occurred in task: " + ex);
            }
        });

        // Update the UI after the task has completed
        task.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == javafx.concurrent.Worker.State.SUCCEEDED) {
                Platform.runLater(() -> {
                    Model.getInstance().getViewFactory().showHomePage(signingButton);
                });
            }
        });

        // Start the task in a new thread
        new Thread(task).start();
    }



    private void handleException(Exception exception) {
        remoteServiceHandler.showAlert("Error during login" + ": " + exception.getMessage(), Alert.AlertType.ERROR);
    }
    private void validateUserInputLogin() {
        if (passwordField.getText().isEmpty()) {
            throw new IllegalArgumentException("Please enter a password");
        }
        if (!isPhoneNumberValid(phoneNumber)) {
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
