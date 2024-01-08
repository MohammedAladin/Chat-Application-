package Model.Entities;

public class Contact {
    private int contactID;
    private int userID;
    private String contactPhoneNumber;
    private String invitationStatus;
    private boolean blockStatus;

    // Constructors
    public Contact() {}

    public Contact(int userID, String contactPhoneNumber, String invitationStatus, boolean blockStatus) {
        this.userID = userID;
        this.contactPhoneNumber = contactPhoneNumber;
        this.invitationStatus = invitationStatus;
        this.blockStatus = blockStatus;
    }

    // Getters and Setters
    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public String getInvitationStatus() {
        return invitationStatus;
    }

    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    public boolean isBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(boolean blockStatus) {
        this.blockStatus = blockStatus;
    }
}