package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.Client.Service.ImageServices;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatUserController implements Initializable {
    @FXML
    public TextField textFieldID;
    @FXML
    private Circle circleID;
    @FXML
    private Label nameID;
    @FXML
    private Label statusID;
    @FXML
    private VBox chatVBoxID;
    byte[] image;
    String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameID.setText(name);
        if (image == null) {
            circleID.setFill(new ImagePattern(new Image(getClass().getResource("/ClientImages/defaultUser.jpg").toString())));
        } else circleID.setFill(new ImagePattern(ImageServices.convertToImage(image)));
        //statusID.setText();
        //testing gui
        textFieldID.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ClientFxml/Message_sent.fxml"));
            if (textFieldID.getText().length() > 0) {
                Text text = new Text(textFieldID.getText());
                try {
                    HBox hBox = loader.load();
//                    MessageSent messageSent = loader.getController();
//                    messageSent.setLabelID(text);
                    chatVBoxID.getChildren().add(hBox);
                    chatVBoxID.setSpacing(10);
                    textFieldID.clear();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
}
