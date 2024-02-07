package org.Server.ServerModels.ServerEntities;

public class BlockedEntity {
    private Integer id;
    private Integer userID;
    private Integer blockedContactID;

    public BlockedEntity(Integer userID, Integer blockedContactID) {
        this.userID = userID;
        this.blockedContactID = blockedContactID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getBlockedContactID() {
        return blockedContactID;
    }

    public void setBlockedContactID(Integer blockedContactID) {
        this.blockedContactID = blockedContactID;
    }
}
