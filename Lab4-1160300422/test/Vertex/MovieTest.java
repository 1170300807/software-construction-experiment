package Vertex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import factory.VertexFactory.MovieVertexFactory;
import vertex.Movie;

public class MovieTest extends VertexTest {
	@Test
	public void test() {
		Movie a = new MovieVertexFactory().createVertex("hello", new String[] {"2008","C","9.9"});
		Movie b = new MovieVertexFactory().createVertex("hello", new String[] {"2001","A","8.8"});
		b.modifyLabel("world");
		assertEquals("world",b.getLabel());
		assertEquals("C",a.getCountry());
		assertEquals(2008,a.getDate());
		assertEquals(9.9,a.getIMDb(),0.01);
		assertTrue(a.equals(a));
		assertFalse(a.equals(b));
	}
}
