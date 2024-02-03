package Model.Enums;

public enum UserField {
    USER_ID("UserID"),
    PHONE_NUMBER("PhoneNumber"),
    NAME("DisplayName"),
    EMAIL("EmailAddress"),
    PICTURE("ProfilePicture"),
    PASSWORD("PasswordHash"),
    GENDER("Gender"),
    COUNTRY("Country"),
    DATE_OF_BIRTH("DateOfBirth"),
    BIO("Bio"),
    STATUS("UserStatus"),
    Mode("UserMode");

    private final String fieldName;

    UserField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}