package Vertex;

import static org.junit.Assert.*;

import org.junit.Test;

import factory.VertexFactory.ActorVertexFactory;
import vertex.Actor;
import vertex.Vertex;

public class ActorTest extends VertexTest {
	@Test
	public void test() {
		Actor a = new ActorVertexFactory().createVertex("hello", new String[] {"20","F"});
		Actor b = new ActorVertexFactory().createVertex("hello", new String[] {"21","M"});
		b.modifyLabel("world");
		assertEquals("world",b.getLabel());
		assertEquals(20,a.getAge());
		assertEquals("F",a.getSex());
		assertTrue(a.equals(a));
		assertFalse(a.equals(b));
	}
}
