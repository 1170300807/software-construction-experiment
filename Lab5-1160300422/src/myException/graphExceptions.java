package myException;

public abstract class graphExceptions extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public graphExceptions() {
		super();
	}

	public graphExceptions(String message) {
		super(message);
	}

	public graphExceptions(String message, Throwable cause) {
		super(message, cause);
	}

	public graphExceptions(Throwable cause) {
		super(cause);
	}
}
