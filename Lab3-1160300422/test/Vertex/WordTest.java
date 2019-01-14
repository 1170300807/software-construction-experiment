package Vertex;

import static org.junit.Assert.*;

import org.junit.Test;

import factory.VertexFactory.WordVertexFactory;
import vertex.Vertex;
import vertex.Word;

public class WordTest extends VertexTest {
	@Test
	public void testToString() {
		Vertex word = new WordVertexFactory().createVertex("abandon", null);
		assertEquals("<\"abandon\", \"Word\">",word.toString());
	}
}
