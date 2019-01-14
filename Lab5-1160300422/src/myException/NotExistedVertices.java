package myException;

public class NotExistedVertices extends graphExceptions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotExistedVertices() {
		super();
	}

	public NotExistedVertices(String message) {
		super(message);
	}

	public NotExistedVertices(String message, Throwable cause) {
		super(message, cause);
	}

	public NotExistedVertices(Throwable cause) {
		super(cause);
	}
}
