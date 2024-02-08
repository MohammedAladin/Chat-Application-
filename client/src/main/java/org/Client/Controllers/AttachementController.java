package org.Client.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.Client.Models.Model;
import org.Client.Service.ImageServices;

import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ResourceBundle;


public class AttachementController implements Initializable {
    private String message ;
    private Integer attachmentID;
    private Integer senderID;
    private Integer ReceiverID;
    private String userName;
    private byte[] img;
    private Timestamp time;
    @FXML
    private Label msg;

    public Label getInstallLabel() {
        return installLabel;
    }

    public void setInstallLabel(Label installLabel) {
        this.installLabel = installLabel;
    }

    @FXML
    private Label installLabel;

    public VBox getLabelContainer() {
        return labelContainer;
    }

    public void setLabelContainer(VBox labelContainer) {
        this.labelContainer = labelContainer;
    }

    @FXML
    private VBox labelContainer;
    @FXML
    private Circle imgcircle;
    @FXML
    private Label name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        installLabel.setOnMouseClicked(e-> {
            try {
                Model.getInstance().getCallBackServicesServer().downloadAttachment(Model.getInstance().getClientId(), attachmentID,message);
            } catch (RemoteException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection Error");
                alert.setHeaderText("Connection Error");
                alert.setContentText("Connection Unstable, please check your connection try again later");
                throw new RuntimeException(ex);
            }
        });
        if(userName==null){
            name.setVisible(false);
            name.setManaged(false);
            imgcircle.setManaged(false);
            imgcircle.setVisible(false);
        }
        else{
            if(img==null||img.length==0){
                imgcircle.setFill(new ImagePattern(ImageServices.getDefaultImage()));
            }
            else imgcircle.setFill(new ImagePattern(ImageServices.convertToImage(img)));
            name.setText(userName);
        }
        msg.setText(message);

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAttachmentID() {
        return attachmentID;
    }

    public void setAttachmentID(Integer attachmentID) {
        this.attachmentID = attachmentID;
    }

    public Integer getSenderID() {
        return senderID;
    }

    public void setSenderID(Integer senderID) {
        this.senderID = senderID;
    }

    public Integer getReceiverID() {
        return ReceiverID;
    }

    public void setReceiverID(Integer receiverID) {
        ReceiverID = receiverID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public void setTime(Timestamp timestamp) {
        this.time = timestamp;
    }

    public Timestamp getTime() {
        return time;
    }

    public Label getMsg() {
        return msg;
    }

    public void setMsg(Label msg) {
        this.msg = msg;
    }
}
