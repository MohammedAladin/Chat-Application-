package org.Client.Contollers;

import Interfaces.UserServices;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.Server.Service.RegistrationService;

import java.net.URL;

import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public TextField phone_field, name_field, email_field;
    public PasswordField password_field;
    public Button register_button;
    RegistrationService registrationService;
    UserController userController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userController = UserController.getInstance();
        UserServices userService = userController.remoteServices();
        registrationService = RegistrationService.getInstance(userService);
        register_button.setOnAction(e->handleRegistration());

    }
    private void handleRegistration() {
        registrationService.registerUser(phone_field.getText(),name_field.getText(),email_field.getText(),password_field.getText());
        phone_field.clear();
        name_field.clear();
        email_field.clear();
        password_field.clear();
    }

}

