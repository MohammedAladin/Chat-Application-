package Model;

public class BlockedContact {
    private int userID;
    private int blockedUserID;

    // Constructors
    public BlockedContact() {}

    public BlockedContact(int userID, int blockedUserID) {
        this.userID = userID;
        this.blockedUserID = blockedUserID;
    }

    // Getters and Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBlockedUserID() {
        return blockedUserID;
    }

    public void setBlockedUserID(int blockedUserID) {
        this.blockedUserID = blockedUserID;
    }
}