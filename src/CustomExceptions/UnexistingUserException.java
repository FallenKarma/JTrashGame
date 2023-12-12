package customExceptions;

public class UnexistingUserException extends RuntimeException{
	public UnexistingUserException(String message) {
		super(message);
	}
	
}
