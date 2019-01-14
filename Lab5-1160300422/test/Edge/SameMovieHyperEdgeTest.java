package Edge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edge.Edge;
import factory.EdgeFactory.MovieActorRelationFactory;
import factory.EdgeFactory.SameMovieHyperEdgeFactory;
import factory.VertexFactory.ActorVertexFactory;
import vertex.Vertex;

public class SameMovieHyperEdgeTest extends HyperEdgeTest {
	@Test
	public void testSameMovieHyperEdge() {
		Vertex a = new ActorVertexFactory().createVertex("hello", new String[] {"20","F"});
		Vertex b = new ActorVertexFactory().createVertex("world", new String[] {"21","M"});
		Vertex c = new ActorVertexFactory().createVertex("again", new String[] {"22","M"});

		List<Vertex> vertices = Arrays.asList(a, b,c);
		Edge e = new SameMovieHyperEdgeFactory().createEdge("A", vertices, 2);// TestAddVertices
		assertTrue(e.containVertex(a) && e.containVertex(b)&&e.containVertex(c));// TestContainVertex
		assertTrue(e.vertices().contains(a) && e.vertices().contains(b)
				&& e.vertices().size() == 3);// TestVertices
		assertTrue(
				e.sourceVertices().size() == 3 && e.sourceVertices().contains(a));// TestSourceVertices
		assertTrue(
				e.targetVertices().size() == 3 && e.targetVertices().contains(b));// TestTargetVertices
		assertEquals(e.toString(),e.toString());
	}
}
