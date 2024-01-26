package Model.DTO;


import java.io.Serializable;
import java.sql.Date;

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
