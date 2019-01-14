package myException;

public class InvalidLabel extends graphExceptions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidLabel() {
		super();
	}

	public InvalidLabel(String message) {
		super(message);
	}

	public InvalidLabel(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidLabel(Throwable cause) {
		super(cause);
	}
}
