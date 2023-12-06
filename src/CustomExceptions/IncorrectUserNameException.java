package customExceptions;

public class IncorrectUserNameException extends Exception{
    public IncorrectUserNameException(String errorMessage) {
        super(errorMessage);
    }
}
