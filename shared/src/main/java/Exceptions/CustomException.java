package Exceptions;
public class CustomException extends Exception{
    private final String description;

    public CustomException(String description) {
        this.description = description;

    }
    public String getDescription() {
        return description;
    }
}