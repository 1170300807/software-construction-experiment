package Vertex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import factory.VertexFactory.DirectorVertexFactory;
import vertex.Director;

public class DirectorTest extends VertexTest {
	@Test
	public void test() {
		Director a = new DirectorVertexFactory().createVertex("hello", new String[] {"20","F"});
		Director b = new DirectorVertexFactory().createVertex("hello", new String[] {"21","M"});
		b.modifyLabel("world");
		assertEquals("world",b.getLabel());
		assertEquals(20,a.getAge());
		assertEquals("F",a.getSex());
		assertTrue(a.equals(a));
		assertFalse(a.equals(b));
	}
}
