package Model.Enums;

import java.util.Date;

public enum UserField {
    USER_ID("userID"),
    PHONE_NUMBER("phoneNumber"),
    DISPLAY_NAME("displayName"),
    EMAIL("email"),
    PICTURE("picture"),
    PASSWORD("password"),
    GENDER("gender"),
    COUNTRY("country"),
    DATE_OF_BIRTH("dateOfBirth"),
    BIO("bio"),
    STATUS("status"),
    SPECIFIC_STATUS("specificStatus");

    private final String fieldName;

    UserField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}