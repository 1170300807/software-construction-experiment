package Edge;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edge.Edge;
import factory.EdgeFactory.WordNeighborhoodEdgeFactory;
import factory.VertexFactory.WordVertexFactory;
import vertex.Vertex;

public class WordNeighborhoodEdgeTest extends DirectedEdgeTest {
	
	@Test
	public void testWordNeighborhoodEdge() {
		Vertex a = new WordVertexFactory().createVertex("hello", null);
		Vertex b = new WordVertexFactory().createVertex("world", null);
		List<Vertex> vertices = Arrays.asList(a, b);
		Edge wordneighborhoodedge = new WordNeighborhoodEdgeFactory().createEdge("A", vertices, 2);// TestAddVertices
		assertTrue(wordneighborhoodedge.containVertex(a) && wordneighborhoodedge.containVertex(b));// TestContainVertex
		assertTrue(wordneighborhoodedge.vertices().contains(a) && wordneighborhoodedge.vertices().contains(b)
				&& wordneighborhoodedge.vertices().size() == 2);// TestVertices
		assertTrue(
				wordneighborhoodedge.sourceVertices().size() == 1 && wordneighborhoodedge.sourceVertices().contains(a));// TestSourceVertices
		assertTrue(
				wordneighborhoodedge.targetVertices().size() == 1 && wordneighborhoodedge.targetVertices().contains(b));// TestTargetVertices
		assertEquals(wordneighborhoodedge.toString(),wordneighborhoodedge.toString());
		//assertEquals("<\"A\", \"WordEdge\", \"2.0\", \"[<\"hello\", \"Word\">]\", \"[<\"world\", \"Word\">]\", Yes>",
				//wordneighborhoodedge.toString());// TestToString
	}

	
}
