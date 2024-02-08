package Model.DTO;

import java.io.Serializable;

public class BlockedContactDTO implements Serializable {
    private Integer userID;
    private String blockedUserPhoneNumber;

    public BlockedContactDTO(Integer userID, String blockedUserPhoneNumber) {
        this.userID = userID;
        this.blockedUserPhoneNumber = blockedUserPhoneNumber;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getBlockedUserPhoneNumber() {
        return blockedUserPhoneNumber;
    }

    public void setBlockedUserPhoneNumber(String blockedUserPhoneNumber) {
        this.blockedUserPhoneNumber = blockedUserPhoneNumber;
    }
}
