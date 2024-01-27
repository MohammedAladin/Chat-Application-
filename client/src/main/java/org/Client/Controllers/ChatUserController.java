package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatUserController implements Initializable {
    @FXML
    private ImageView imageView;

    public void setImage(Image image) {
        this.image = image;
    }

    private Image image;

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    private Label name;
    public String username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText(username);
        imageView.setImage(image);
    }
}
