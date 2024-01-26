package org.Server.ServerModels.Enums;

public enum StatusEnum {
    ONLINE("Online"),
    OFFLINE("Offline");

    private final String value;

    StatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // You might also want to add a method to convert a string to the enum value
    public static StatusEnum fromValue(String value) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown StatusEnum value: " + value);
    }
}
