package Vertex;

import static org.junit.Assert.*;

import org.junit.Test;

import factory.VertexFactory.ComputerVertexFactory;
import factory.VertexFactory.RouterVertexFactory;
import vertex.Computer;
import vertex.Router;

public class ComouterTest extends VertexTest {
	@Test
	public void test() {
		String[] args1 = {"172.20.10.4"};
		String[] args2 = {"172.20.10.4"};
		Computer A = new ComputerVertexFactory().createVertex("hp", args1);
		Computer B = new ComputerVertexFactory().createVertex("dell", args2);
		Computer C = null;
		Router D = new RouterVertexFactory().createVertex("gee", args1);
		assertEquals("<\"hp\", \"Computer\", <\"172.20.10.4\">>",A.toString());
		assertFalse(A.equals(B));
		assertFalse(A.equals(C));
		assertFalse(A.equals(D));
		assertEquals("172.20.10.4",A.getIP());
		assertEquals("hp",A.getLabel());
	}
	
}
