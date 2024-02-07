package Model.DTO;

import java.io.Serializable;

public class BlockedContactDTO implements Serializable {
    private Integer userID;
    private Integer blockedUserID;

    public BlockedContactDTO(Integer userID, Integer blockedUserID) {
        this.userID = userID;
        this.blockedUserID = blockedUserID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getBlockedUserID() {
        return blockedUserID;
    }

    public void setBlockedUserID(Integer blockedUserID) {
        this.blockedUserID = blockedUserID;
    }
}
