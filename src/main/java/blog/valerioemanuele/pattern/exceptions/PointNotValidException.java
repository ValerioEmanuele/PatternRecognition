package blog.valerioemanuele.pattern.exceptions;

public class PointNotValidException extends RuntimeException{
	private static final long serialVersionUID = 2686556849475321048L;

	public PointNotValidException(String message) {
		super(message);
	}
}
