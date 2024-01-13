package Model.DTO;

import java.io.Serializable;

public class UserLoginDTO implements Serializable {
     String phoneNumber;
     String password;

    public UserLoginDTO(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
    public UserLoginDTO() {

    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getPassword() {
        return password;
    }
}
