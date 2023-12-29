package customExceptions;

/**
 * Exception thrown when attempting to access an unexisting user.
 */
public class UnexistingUserException extends RuntimeException {

    /**
     * Constructs an UnexistingUserException with the specified message.
     *
     * @param message The message describing the exception.
     */
    public UnexistingUserException(String message) {
        super(message);
    }
}
