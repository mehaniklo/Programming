package Folder.exceptions;

public class InvalidInputException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Too long";
    }
}
