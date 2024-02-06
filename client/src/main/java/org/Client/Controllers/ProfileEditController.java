package org.Client.Controllers;
import Model.Enums.UserField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import org.Client.Models.Model;
import org.Client.Service.ImageServices;
import org.Client.UserSessionManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Date;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ProfileEditController implements Initializable {

    @FXML
    private Label editProfilePicButton;

    @FXML
    private Button saveButton;
    @FXML
    private TextField nameTextField;

    @FXML
    private ComboBox<String> countryChooser;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private Circle profileImageView;

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

    private byte [] img;
    Image profilePic = new Image(getClass().getResource("/ClientImages/defaultUser.jpg").toExternalForm());
    private Model model = Model.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setDefaultInfo();

        editProfilePicButton.setOnMouseClicked((e)->handleEditProfilePicButton());
        saveButton.setOnAction((e)->handleSaveButtonAction());

    }
    private void setDefaultInfo(){
        emailTextField.setText(model.getEmail());
        nameTextField.setText(model.getName());
        phoneTextField.setText(model.getPhoneNumber());
        dateOfBirth.setValue(model.getBirthDate().toLocalDate());
        emailTextField.setText(model.getEmail());
        if(model.getProfilePicture()==null||model.getProfilePicture().length==0){
            profileImageView.setFill(new ImagePattern(ImageServices.getDefaultImage()));
        }
        else{
            profileImageView.setFill(new ImagePattern(ImageServices.convertToImage(model.getProfilePicture())));
        }
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


            Map<String, String> updatedFields = new HashMap<>();

            if (!name.equals(model.getName())) {
                updatedFields.put(UserField.NAME.getFieldName(), name);
            }
            if (country !=null) {
                updatedFields.put(UserField.COUNTRY.getFieldName(), country);
            }
            if (!dob.equals(model.getBirthDate().toString())) {
                updatedFields.put(UserField.DATE_OF_BIRTH.getFieldName(), dob);
            }
            if (!phone.equals(model.getPhoneNumber())) {
                updatedFields.put(UserField.PHONE_NUMBER.getFieldName(), phone);
            }
            if (!email.equals(model.getEmail())) {
                updatedFields.put(UserField.EMAIL.getFieldName(), email);
            }

//            String []userInfo = UserSessionManager.loadUserInfo();
//            if(userInfo[1].equals(oldPassword) && isValidPassword(newPassword, oldPassword)){
//                updatedFields.put(UserField.PASSWORD.getFieldName(), newPassword);
//            }

            try {
                model.getCallBackServicesServer().updateProfile(model.getClientId(), updatedFields);
                Model.getInstance().setDisplayName(name);
            } catch (RemoteException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection Error");
                alert.setHeaderText(null);
                alert.setContentText("Could not save your changes due to connection issues. Please try again later.");
                alert.showAndWait();
            }

            if(img!=null&&img.length>0){
                try {
                    model.getCallBackServicesServer().updateProfilePic(model.getClientId(), img);
                    Model.getInstance().setProfilePicture(img);
                } catch (RemoteException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Connection Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Could not update profile picture. Please try again later.");

                    alert.showAndWait();
                }
            }
            else System.out.println("no image to update");
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

        try {

            if (selectedFile != null) {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                System.out.println("buffered Image "+bufferedImage.getWidth() + bufferedImage.getHeight());
                img = ImageServices.convertToByte(bufferedImage);
                Image image = new Image(selectedFile.toURI().toString());
                profileImageView.setFill(new ImagePattern(image));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
