package Vertex;

import static org.junit.Assert.*;

import org.junit.Test;

import factory.VertexFactory.PersonVertexFactory;
import factory.VertexFactory.WordVertexFactory;
import vertex.Person;
import vertex.Vertex;

public class PersonTest extends VertexTest {
	@Test
	public void testToString() {
		String[] args1 = { "M", "21" };
		String[] args2 = { "F", "21" };
		String[] args3 = { "M", "a" };
		String[] args4 = { "MF", "1" };
		Vertex R = new PersonVertexFactory().createVertex("Ren", args1);
		//Person B = new PersonVertexFactory().createVertex("B", args3);
		Vertex A = new PersonVertexFactory().createVertex("A", args2);
		//Person C = new PersonVertexFactory().createVertex("C", args4);
		Vertex W = new WordVertexFactory().createVertex("A", null);
		assertEquals("<\"Ren\", \"Person\", <\"M\", \"21\">>", R.toString());
		assertEquals("<\"A\", \"Person\", <\"F\", \"21\">>", A.toString());
		//assertEquals(A.getClass(),W.getClass());
	}
}
