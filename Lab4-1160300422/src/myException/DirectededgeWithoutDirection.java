package myException;

public class DirectededgeWithoutDirection extends graphExceptions{

	private static final long serialVersionUID = 1L;

	public DirectededgeWithoutDirection() {
		super();
	}

	public DirectededgeWithoutDirection(String message) {
		super(message);
	}

	public DirectededgeWithoutDirection(String message, Throwable cause) {
		super(message, cause);
	}

	public DirectededgeWithoutDirection(Throwable cause) {
		super(cause);
	}
}
