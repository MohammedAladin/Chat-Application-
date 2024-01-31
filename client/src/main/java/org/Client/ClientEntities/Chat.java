package org.Client.ClientEntities;

import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Chat {

    private StringProperty message;
    private Image image;
    private StringProperty name;
    private StringProperty status;
    private StringProperty lastText;

    private String phoneNumber;

    public Chat(StringProperty message, Image image, StringProperty name, StringProperty status, StringProperty lastText) {
        this.message = message;
        this.image = image;
        this.name = name;
        this.status = status;
        this.lastText = lastText;
    }

    public StringProperty messageProperty() {
        return message;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty lastTextProperty() {
        return lastText;
    }

    public String getName(){
        return name.get();
    }
    public String getStatus(){
        return status.get();
    }

    public String getMessage(){
        return message.get();
    }

    public String getLastText(){
        return lastText.get();
    }

    public Image getImage(){
        return image;
    }



    public Chat() {
    }





}
