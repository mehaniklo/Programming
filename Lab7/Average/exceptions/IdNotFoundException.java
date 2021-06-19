package Folder.exceptions;

/**
 * Id not found exception
 */
public class IdNotFoundException extends RuntimeException {
    /**
     * @return error message
     */
    @Override
    public String getMessage() {
        return "Your item with this id could not be found.";
    }
}
