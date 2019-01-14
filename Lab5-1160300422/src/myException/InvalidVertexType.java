package myException;

public class InvalidVertexType extends graphExceptions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidVertexType() {
		super();
	}

	public InvalidVertexType(String message) {
		super(message);
	}

	public InvalidVertexType(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidVertexType(Throwable cause) {
		super(cause);
	}
}
