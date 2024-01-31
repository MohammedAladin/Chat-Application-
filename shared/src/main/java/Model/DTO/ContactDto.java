package Model.DTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ContactDto implements Serializable {
    int contactID;
    String contactName;

    String mode;
    String bio;
    byte[] contactImage;


    public ContactDto() {
    }

    public Integer getContactID() {
        return contactID;
    }

    public ContactDto(int contactID, String contactName, String status, String bio, byte[] contactImage) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.status.set(status);
        this.bio = bio;
        this.contactImage = contactImage;

    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }



    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public byte[] getContactImage() {
        return contactImage;
    }

    public void setContactImage(byte[] contactImage) {
        this.contactImage = contactImage;
    }



    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    private transient StringProperty status = new SimpleStringProperty();

    // Implement custom serialization
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(status.get());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        status = new SimpleStringProperty((String) in.readObject());
    }




}
