package Model.DTO;

import java.io.Serializable;

public class ParticipantDto implements Serializable {
    private Integer participantID;
    private String participantName;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    private byte[] image;

    public ParticipantDto() {
    }

    public ParticipantDto(Integer participantID, String participantName, byte[] image) {
        this.participantID = participantID;
        this.participantName = participantName;
        this.image = image;
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
