package org.Client.Controllers;
import Model.Enums.UserField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import java.sql.Date;

import java.io.File;
import java.net.URL;
import java.util.*;

public class ProfileEditController implements Initializable {

    @FXML
    private Button editProfilePicButton;

    @FXML
    private Button saveButton;
    @FXML
    private TextField nameTextField;

    @FXML
    private ComboBox<String> countryChooser;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private ImageView profileImageView;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField oldPasswordTextField;

    @FXML
    private TextField newPasswordTextField;

    @FXML
    private TextField confirmPasswordTextField;
    Image profilePic = new Image(getClass().getResource("/ClientImages/defaultUser.jpg").toExternalForm());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Circle clip = new Circle(50, 50, 50);
        profileImageView.setClip(clip);

        editProfilePicButton.setOnAction((e)->handleEditProfilePicButton());
        saveButton.setOnAction((e)->handleSaveButtonAction());

    }
    private void handleSaveButtonAction() {
        String name = nameTextField.getText();
        String country = countryChooser.getValue();
        String dob = dateOfBirth.getValue().toString();// Assuming you want the date as a string
        String phone = phoneTextField.getText();
        String email = emailTextField.getText();
        String oldPassword = oldPasswordTextField.getText();
        String newPassword = newPasswordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        if(isValidPassword(confirmPassword,newPassword)) {
            Map<String, String> updatedFields = new HashMap<>();
            updatedFields.put(UserField.PASSWORD.getFieldName(), newPassword);
            if (name != null && !name.isEmpty()) {
                updatedFields.put(UserField.NAME.getFieldName(), name);
            }
            if (country != null) {
                updatedFields.put(UserField.COUNTRY.getFieldName(), country);
            }
            if (!dob.isEmpty()) {
                updatedFields.put(UserField.DATE_OF_BIRTH.getFieldName(), dob);
            }
            if (phone != null && !phone.isEmpty()) {
                updatedFields.put(UserField.PHONE_NUMBER.getFieldName(), phone);
            }
            if (email != null && !email.isEmpty()) {
                updatedFields.put(UserField.EMAIL.getFieldName(), email);
            }

        }
    }
    private boolean isValidPassword(String newPassword, String confirmPassword) {
        return newPassword != null && !newPassword.isEmpty() &&
                confirmPassword != null && !confirmPassword.isEmpty() &&
                newPassword.equals(confirmPassword);
    }
    private void handleEditProfilePicButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            profileImageView.setImage(image);
        }
    }
}
