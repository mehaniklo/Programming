package Folder.exceptions;
/**
 * Incorrect input data exception
 */
public class IncorrectInputDataException extends RuntimeException {
    /**
     * @return error message
     */
    @Override
    public String getMessage() {
        return "incorrect input data";
    }
}
