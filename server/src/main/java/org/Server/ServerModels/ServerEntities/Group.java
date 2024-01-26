package org.Server.ServerModels.ServerEntities;

public class Group {
    private int groupID;
    private String groupName;
    private int creatorID;

    // Constructors
    public Group() {}

    public Group(String groupName, int creatorID) {
        this.groupName = groupName;
        this.creatorID = creatorID;
    }

    // Getters and Setters
    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }
}