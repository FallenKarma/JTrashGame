package customExceptions;

/**
 * Exception thrown when an incorrect user name is encountered.
 */
public class IncorrectUserNameException extends RuntimeException {

    /**
     * Constructs an IncorrectUserNameException with the specified error message.
     *
     * @param errorMessage The error message describing the exception.
     */
    public IncorrectUserNameException(String errorMessage) {
        super(errorMessage);
    }
}
