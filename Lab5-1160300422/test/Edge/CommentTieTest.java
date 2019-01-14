package Edge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edge.Edge;
import factory.EdgeFactory.CommentTieFactory;
import factory.EdgeFactory.WordNeighborhoodEdgeFactory;
import factory.VertexFactory.PersonVertexFactory;
import vertex.Vertex;

public class CommentTieTest extends DirectedEdgeTest {
	
	@Test
	public void testCommentTie() {
		String[] args1 = { "M", "21" };
		String[] args2 = { "F", "21" };
		Vertex a = new PersonVertexFactory().createVertex("hello", args1);
		Vertex b = new PersonVertexFactory().createVertex("world", args2);
		List<Vertex> vertices = Arrays.asList(a, b);
		Edge commenttie = new CommentTieFactory().createEdge("A", vertices, 2);// TestAddVertices
		assertTrue(commenttie.containVertex(a) && commenttie.containVertex(b));// TestContainVertex
		assertTrue(commenttie.vertices().contains(a) && commenttie.vertices().contains(b)
				&& commenttie.vertices().size() == 2);// TestVertices
		assertTrue(
				commenttie.sourceVertices().size() == 1 && commenttie.sourceVertices().contains(a));// TestSourceVertices
		assertTrue(
				commenttie.targetVertices().size() == 1 && commenttie.targetVertices().contains(b));// TestTargetVertices
		
		
		
	}
}
