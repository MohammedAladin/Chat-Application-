package org.Server.ServerModels.ServerEntities;

public class GroupMember {
    private int groupID;
    private int userID;

    // Constructors
    public GroupMember() {}

    public GroupMember(int groupID, int userID) {
        this.groupID = groupID;
        this.userID = userID;
    }

    // Getters and Setters
    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}