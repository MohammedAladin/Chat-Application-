package org.Client.Contollers;

import Interfaces.UserServices;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.Client.Models.Model;
import org.Server.Service.LoginService;
import org.Server.Service.RegistrationService;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label register_label;
    public Button signing_button;
    public PasswordField password_field;
    public TextField phone_field;

    LoginService loginService;
    UserController userController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userController = UserController.getInstance();
        UserServices userService = userController.remoteServices();
        loginService = LoginService.getInstance(userService);

        signing_button.setOnAction((e)->handleSignIn());
        register_label.setOnMouseClicked(e-> Model.getInstance().getViewFactory().showRegisterWindow());
    }

    private void handleSignIn(){
        String phoneNumber = phone_field.getText();
        String password = password_field.getText();

        loginService.loginUser(phoneNumber,password);
        phone_field.clear();
        password_field.clear();

    }
}
