package org.Client.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

    @FXML
    private Label lastText;


    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    private String name;

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    private StringProperty status = new SimpleStringProperty();
    private String message;
    private byte[] image;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Circle clip = new Circle(25, 25, 25);
        contactImage.setClip(clip);
        contactName.setText(name);
        //contactImage.setImage(image);
        lastText.setText(message);
        changeStatus();
        status.addListener((observableValue, s, t1) -> {
            changeStatus();
            System.out.println(t1);
        });


    }

    public void changeStatus() {
        if (status.get().equals("Online")) {
            contactStatus.setStyle("-fx-fill: green");
            contactStatus.setStroke(null);
        } else {
            contactStatus.setStyle("-fx-fill: red");
            contactStatus.setStroke(null);
        }
    }
}