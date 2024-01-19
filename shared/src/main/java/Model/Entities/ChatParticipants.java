package Model.Entities;

import java.sql.Timestamp;

public class ChatParticipants {
    private int chatID;
    private int participantUserID;
    private Timestamp participantStartDate;

    public ChatParticipants(int chatID, int participantUserID, Timestamp participantStartDate) {
        this.chatID = chatID;
        this.participantUserID = participantUserID;
        this.participantStartDate = participantStartDate;
    }

    public ChatParticipants() {
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public int getParticipantUserID() {
        return participantUserID;
    }

    public void setParticipantUserID(int participantUserID) {
        this.participantUserID = participantUserID;
    }

    public Timestamp getParticipantStartDate() {
        return participantStartDate;
    }

    public void setParticipantStartDate(Timestamp participantStartDate) {
        this.participantStartDate = participantStartDate;
    }
}
