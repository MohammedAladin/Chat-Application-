package org.Client.Controllers;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class MessageSent
{


    @javafx.fxml.FXML
    private Label messageLabelID;
    public void setLabelID(Text text){
        messageLabelID.setText(text.getText());
    }
    public Label getMessageLabelID(){return messageLabelID;}
}