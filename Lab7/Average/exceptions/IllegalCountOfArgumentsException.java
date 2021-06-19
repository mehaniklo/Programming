package Folder.exceptions;
/**
 * Illegal count of arguments exception
 */
public class IllegalCountOfArgumentsException extends RuntimeException{
    /**
     * @return error message
     */
    @Override
    public String getMessage() {
        return "Incorrect number of argkments";
    }
}
