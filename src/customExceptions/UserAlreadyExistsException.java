package customExceptions;

/**
 * Exception thrown when attempting to create a user that already exists.
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a UserAlreadyExistsException with the specified message.
     *
     * @param message The message describing the exception.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
