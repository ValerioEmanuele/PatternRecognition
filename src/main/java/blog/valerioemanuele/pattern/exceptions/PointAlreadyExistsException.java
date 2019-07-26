package blog.valerioemanuele.pattern.exceptions;

public class PointAlreadyExistsException extends RuntimeException{
	private static final long serialVersionUID = 1695118085688857999L;

	public PointAlreadyExistsException(String message) {
		super(message);
	}
}
