package myException;

public class NotEnoughVerticesForHyperedge extends graphExceptions{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotEnoughVerticesForHyperedge() {
		super();
	}

	public NotEnoughVerticesForHyperedge(String message) {
		super(message);
	}

	public NotEnoughVerticesForHyperedge(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEnoughVerticesForHyperedge(Throwable cause) {
		super(cause);
	}
}
