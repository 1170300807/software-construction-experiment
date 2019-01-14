package Edge;

import org.junit.Test;

public abstract class EdgeTest {
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}
	
}
