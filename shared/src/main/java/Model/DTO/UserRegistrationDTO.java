package Model.DTO;

import java.sql.Date;
import java.time.LocalDate;

public class UserRegistrationDTO extends UserLoginDTO {
    private String displayName;
    private String gender;
    private String country;
    private Date dateOfBirth;
    private String bio;
    private String status;
    private String specificStatus;

    private String email;

    public UserRegistrationDTO(String phoneNumber,String email, String displayName, String password, String gender, String country, Date dateOfBirth) {
        super();
        this.phoneNumber = phoneNumber;
        this.displayName = displayName;
        this.password = password;
        this.gender = gender;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
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
