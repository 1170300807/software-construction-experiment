package myException;

public class InvalidEdgeType extends graphExceptions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidEdgeType() {
		super();
	}

	public InvalidEdgeType(String message) {
		super(message);
	}

	public InvalidEdgeType(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidEdgeType(Throwable cause) {
		super(cause);
	}
}
