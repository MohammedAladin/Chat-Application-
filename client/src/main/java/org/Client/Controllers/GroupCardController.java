package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.Client.Service.ImageServices;

import java.net.URL;
import java.util.ResourceBundle;

public class GroupCardController implements Initializable {
    @FXML
    Circle imageCircle;
    @FXML
    Label chatNamelabel;
    @FXML
    Text participants;
    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public byte[] getChatImage() {
        return chatImage;
    }

    public void setChatImage(byte[] chatImage) {
        this.chatImage = chatImage;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    private String chatName;
    private byte[] chatImage;
    private Integer adminID;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(chatImage.length>0){
            imageCircle.setFill(new ImagePattern(ImageServices.convertToImage(chatImage)));
            System.out.println("Image set and not the default");
        }
        else {
            imageCircle.setFill(new ImagePattern(new Image(getClass().getResource("/ClientImages/groupgray.png").toString())));
            System.out.println("Image set to default");
        }
        chatNamelabel.setText(chatName);


    }
}
