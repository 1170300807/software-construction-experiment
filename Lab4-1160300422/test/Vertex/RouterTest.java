package Vertex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import factory.VertexFactory.ComputerVertexFactory;
import factory.VertexFactory.RouterVertexFactory;
import factory.VertexFactory.RouterVertexFactory;
import vertex.Computer;
import vertex.Router;
import vertex.Router;

public class RouterTest extends VertexTest {
	@Test
	public void test() {
		String[] args1 = {"172.20.10.4"};
		String[] args2 = {"172.20.10.4"};
		Router A = new RouterVertexFactory().createVertex("hp", args1);
		Router B = new RouterVertexFactory().createVertex("dell", args2);
		Router C = null;
		Computer D = new ComputerVertexFactory().createVertex("gee", args1);
		assertEquals("<\"hp\", \"Router\", <\"172.20.10.4\">>",A.toString());
		assertFalse(A.equals(B));
		assertFalse(A.equals(C));
		assertFalse(A.equals(D));
		assertEquals("172.20.10.4",A.getIP());
		assertEquals("hp",A.getLabel());
	}
}
