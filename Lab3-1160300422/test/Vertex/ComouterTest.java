package Vertex;

import static org.junit.Assert.*;

import org.junit.Test;

import factory.VertexFactory.ComputerVertexFactory;
import vertex.Computer;

public class ComouterTest extends VertexTest {
	@Test
	public void testToString() {
		String[] args1 = {"172.20.10.4"};
		String[] args2 = {"a"};
		Computer A = new ComputerVertexFactory().createVertex("hp", args1);
		//Computer B = new ComputerVertexFactory().createVertex("dell", args2);
		assertEquals("<\"hp\", \"Computer\", <\"172.20.10.4\">>",A.toString());
	}
	
}
