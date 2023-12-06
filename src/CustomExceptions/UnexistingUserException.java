package customExceptions;

public class UnexistingUserException extends Exception{
	public UnexistingUserException(String message) {
		super(message);
	}
	
}
