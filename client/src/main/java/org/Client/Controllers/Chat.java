package org.Client.Controllers;

import javafx.scene.image.Image;

public class Chat {
    private String message;

    public Chat() {
    }

    public String getMessage() {
        return message;
    }

    public Chat(String message, Image image, String name, String status, String lastText) {
        this.message = message;
        this.image = image;
        this.name = name;
        this.status = status;
        this.lastText = lastText;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastText() {
        return lastText;
    }

    public void setLastText(String lastText) {
        this.lastText = lastText;
    }

    private Image image;
    private String name;
    private String status;
    private String lastText;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String phoneNumber;



}
