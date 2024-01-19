package Model.DTO;

import Model.Entities.User;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

public class UserRegistrationDTO implements Serializable {
    private String phoneNumber;
    private String displayName;
    private String emailAddress;
    private String passwordHash;
    private String gender;
    private String country;
    private Date dateOfBirth;

    public UserRegistrationDTO(String phoneNumber, String displayName, String emailAddress, String passwordHash, String gender, String country, Date dateOfBirth) {
        this.phoneNumber = phoneNumber;
        this.displayName = displayName;
        this.emailAddress = emailAddress;
        this.passwordHash = passwordHash;
        this.gender = gender;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
    }
    public User toUser() {
        User user = new User(// Assuming userID is not available at the time of registration or set to 0 as a placeholder
                this.phoneNumber,
                this.displayName,
                this.emailAddress,
                null,  // You may need to handle profile picture separately
                this.passwordHash,
                this.gender,
                this.country,
                this.dateOfBirth,
                null,  // bio is not available at the time of registration
                "Available",  // Assuming default user status is "Offline"
                "Offline",  // Assuming default user mode is "Offline"
                null  // lastLogin is not available at the time of registration
        );
        return user;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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
}
