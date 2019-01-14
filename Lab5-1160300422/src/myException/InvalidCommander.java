package myException;

public class InvalidCommander extends graphExceptions {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCommander() {
		super();
	}

	public InvalidCommander(String message) {
		super(message);
	}

	public InvalidCommander(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCommander(Throwable cause) {
		super(cause);
	}
}
