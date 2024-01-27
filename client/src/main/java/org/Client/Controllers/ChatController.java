package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private Label contactName;
    @FXML
    private ImageView contactImage;

    @FXML
    private Circle contactStatus;


    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    private String name;
    private String status;
    private String message;
    private Image image;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Circle clip = new Circle(25, 25, 25);
        contactImage.setClip(clip);
        contactName.setText(name);
        contactImage.setImage(image);
        contactName.setText(name);
        if (status.equals("online")) {
            contactStatus.setStyle("-fx-fill: green");
            contactStatus.setStroke(null);
        } else {
            contactStatus.setStyle("-fx-fill: red");
            contactStatus.setStroke(null);
        }
    }
}