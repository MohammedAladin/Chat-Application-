package Model;

import java.io.Serializable;
import java.util.Date;
public class User implements Serializable {
    private int userID;
    private String phoneNumber;
    private String displayName;
    private String email;
    private String picture;
    private String password;
    private String gender;
    private String country;
    private Date dateOfBirth;
    private String bio;
    private String status;
    private String specificStatus;

    // Constructors
    public User() {}

    public User(String phoneNumber, String displayName, String email, String password,
                String gender, String country, Date dateOfBirth, String bio, String status, String specificStatus) {
        this.phoneNumber = phoneNumber;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
        this.status = status;
        this.specificStatus = specificStatus;
    }

    // Getters and Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecificStatus() {
        return specificStatus;
    }

    public void setSpecificStatus(String specificStatus) {
        this.specificStatus = specificStatus;
    }
}
