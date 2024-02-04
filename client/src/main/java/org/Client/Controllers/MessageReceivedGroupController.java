package org.Client.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.Client.Service.ImageServices;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class MessageReceivedGroupController implements Initializable {
    public Circle imageCircle;
    public Label messageLabel;
    public Label time;
    public Label senderName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    private String message;
    private Timestamp timestamp;
    private byte [] image;

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    private String sendername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLabel.setText(message);
        senderName.setText(sendername);
        time.setText(timestamp.toString());
        if(image==null||image.length==0){
            imageCircle.setFill(new ImagePattern(ImageServices.getDefaultImage()));
        }
        else{
            imageCircle.setFill(new ImagePattern(ImageServices.convertToImage(image)));
        }

    }
}
