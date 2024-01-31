package Model.DTO;

import java.io.Serializable;

public class GroupDTO implements Serializable {
    private Integer ChatID;
    private String groupName;
    private byte[] groupImage;
    private Integer adminID;

    public GroupDTO(int groupID, String groupName, byte[] groupImage, Integer adminID) {
        this.ChatID = groupID;
        this.groupName = groupName;
        this.groupImage = groupImage;
        this.adminID = adminID;
    }

    public GroupDTO(String groupName, byte[] groupImage, Integer adminID) {
        this.groupName = groupName;
        this.groupImage = groupImage;
        this.adminID = adminID;
    }

    public GroupDTO() {
    }

    public Integer getChatID() {
        return ChatID;
    }

    public String getGroupName() {
        return groupName;
    }

    public byte[] getGroupImage() {
        return groupImage;
    }

    public Integer getAdminID() {
        return adminID;
    }

    public void setChatID(Integer chatID) {
        this.ChatID = chatID;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupImage(byte[] groupImage) {
        this.groupImage = groupImage;
    }

    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }
}
