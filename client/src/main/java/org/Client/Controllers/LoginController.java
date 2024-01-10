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
    public Label register_label;
    public Button signing_button;
    public PasswordField password_field;
    public TextField phone_field;
    RemoteLoginService remoteLoginService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            remoteLoginService = (RemoteLoginService) registry.lookup("LoginServices");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }

        signing_button.setOnAction((e)->handleSignIn());
        register_label.setOnMouseClicked(e-> Model.getInstance().getViewFactory().showRegisterWindow());
    }

    private void handleSignIn(){
        String phoneNumber = phone_field.getText();
        String password = password_field.getText();

        UserLoginDTO userLogin = new UserLoginDTO(phoneNumber,password);
        try {
            if(remoteLoginService.loginUser(userLogin)==0){
                showAlert("Login Successful", Alert.AlertType.INFORMATION);
            }
            else if(remoteLoginService.loginUser(userLogin)==1){
                showAlert("Invalid Phone Number or Password", Alert.AlertType.WARNING);
            }
            else {
                showAlert("User Services Failed", Alert.AlertType.ERROR);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        phone_field.clear();
        password_field.clear();
    }
    private void showAlert(String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("User Services");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
