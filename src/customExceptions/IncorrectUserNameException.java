package customExceptions;

public class IncorrectUserNameException extends RuntimeException{
    public IncorrectUserNameException(String errorMessage) {
        super(errorMessage);
    }
}
