package Vertex;

import static org.junit.Assert.*;

import org.junit.Test;

import factory.VertexFactory.ServerVertexFactory;
import factory.VertexFactory.WordVertexFactory;
import vertex.Server;
import vertex.Vertex;
import vertex.Word;

public class WordTest extends VertexTest {
	@Test
	public void test() {
		Vertex word = new WordVertexFactory().createVertex("abandon", null);
		Vertex word1 = new WordVertexFactory().createVertex("abandon", null);
		Vertex word2 = new WordVertexFactory().createVertex("ant", null);
		String[] args1 = {"172.20.10.4"};
		Server A = new ServerVertexFactory().createVertex("hp", args1);
		assertEquals("<\"abandon\", \"Word\">",word.toString());
		word.modifyLabel("ant");
		assertTrue(word.equals(word));
		assertFalse(word.equals(word1));
		assertFalse(word1.equals(word2));
		assertFalse(word.equals(A));
	}
}
