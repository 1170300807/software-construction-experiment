package myException;

public class NoAppropriateAttributes extends graphExceptions {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoAppropriateAttributes() {
		super();
	}

	public NoAppropriateAttributes(String message) {
		super(message);
	}

	public NoAppropriateAttributes(String message, Throwable cause) {
		super(message, cause);
	}

	public NoAppropriateAttributes(Throwable cause) {
		super(cause);
	}
}
