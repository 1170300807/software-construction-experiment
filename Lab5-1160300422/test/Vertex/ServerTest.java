package Vertex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import factory.VertexFactory.ComputerVertexFactory;
import factory.VertexFactory.ServerVertexFactory;
import vertex.Computer;
import vertex.Server;

public class ServerTest extends VertexTest {
	@Test
	public void test() {
		String[] args1 = {"172.20.10.4"};
		String[] args2 = {"172.20.10.4"};
		Server A = new ServerVertexFactory().createVertex("hp", args1);
		Server B = new ServerVertexFactory().createVertex("dell", args2);
		Server C = null;
		Computer D = new ComputerVertexFactory().createVertex("gee", args1);
		assertEquals("<\"hp\", \"Server\", <\"172.20.10.4\">>",A.toString());
		assertFalse(A.equals(B));
		assertFalse(A.equals(C));
		assertFalse(A.equals(D));
		assertEquals("172.20.10.4",A.getIP());
		assertEquals("hp",A.getLabel());
	}
}
