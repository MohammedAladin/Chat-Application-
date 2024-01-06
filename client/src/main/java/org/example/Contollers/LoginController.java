package org.example.Contollers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.Models.Model;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label register_label;
    public Button signin_button;
    public PasswordField password_field;
    public TextField phone_field;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        register_label.setOnMouseClicked(e-> Model.getInstance().getViewFactory().showRegisterWindow());

    }
}
