package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatHomeIconController implements Initializable {

    Image image = new Image(getClass().getResource("/ClientImages/home.png").toString());

    @FXML
    private ImageView homeIcon;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //homeIcon.setImage(image);
    }
}
