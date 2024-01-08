package org.Server.Service;

import Interfaces.UserServices;
import javafx.scene.control.Alert;

public class LoginService {

    private static LoginService loginService;
    private static UserServices userService;

    private LoginService() {
    }

    public static LoginService getInstance(UserServices userService) {
        if (loginService == null) {
            LoginService.userService = userService;
            loginService = new LoginService();
        }
        return loginService;
    }

    public void loginUser(String phone, String password) {
        try {
            if (userService.signInUser(phone, password)) {
                showAlert("Login Successful", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Invalid Phone Number or Password", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            showAlert("User Services Failed", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("User Services");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
