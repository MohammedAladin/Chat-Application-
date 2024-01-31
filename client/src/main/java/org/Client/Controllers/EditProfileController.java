package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.net.URL;
import java.util.ResourceBundle;

public class EditProfileController implements Initializable {
    @FXML
    ImageView userImage;
    @FXML
    Label editPic;
    @FXML
    TextField bio;
    @FXML
    TextField emailField;
    @FXML
    TextField nameField;
    Image profilePic = new Image(getClass().getResource("/ClientImages/defaultUser.jpg").toExternalForm());
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Circle clip = new Circle(50, 50, 50);
        userImage.setClip(clip);
        editPic.setOnMouseClicked(mouseEvent -> {changePic();});
        userImage.setImage(profilePic);

    }

    private void changePic() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a profile picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        Image image = new Image(fileChooser.showOpenDialog(editPic.getScene().getWindow()).toURI().toString());
        profilePic = image;
        userImage.setImage(image);
    }
}
