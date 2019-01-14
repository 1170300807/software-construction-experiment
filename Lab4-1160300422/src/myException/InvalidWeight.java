package myException;

public class InvalidWeight extends graphExceptions{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidWeight() {
		super();
	}

	public InvalidWeight(String message) {
		super(message);
	}

	public InvalidWeight(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidWeight(Throwable cause) {
		super(cause);
	}
}
