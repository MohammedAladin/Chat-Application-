package Model.DTO;

import java.io.Serializable;

public class ParticipantDto implements Serializable {
    private Integer participantID;
    private String participantName;

    public ParticipantDto() {
    }

    public ParticipantDto(Integer participantID, String participantName) {
        this.participantID = participantID;
        this.participantName = participantName;
    }

    public Integer getParticipantID() {
        return participantID;
    }

    public void setParticipantID(Integer participantID) {
        this.participantID = participantID;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }
}
