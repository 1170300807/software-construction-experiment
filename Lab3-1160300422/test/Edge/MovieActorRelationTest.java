package Edge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edge.Edge;
import factory.EdgeFactory.MovieActorRelationFactory;
import factory.VertexFactory.ActorVertexFactory;
import vertex.Vertex;

public class MovieActorRelationTest extends UndirectedEdgeTest {
	@Test
	public void testMovieActorRelation() {
		Vertex a = new ActorVertexFactory().createVertex("hello", new String[] {"20","F"});
		Vertex b = new ActorVertexFactory().createVertex("world", new String[] {"21","M"});
		List<Vertex> vertices = Arrays.asList(a, b);
		Edge e = new MovieActorRelationFactory().createEdge("A", vertices, 2);// TestAddVertices
		assertTrue(e.containVertex(a) && e.containVertex(b));// TestContainVertex
		assertTrue(e.vertices().contains(a) && e.vertices().contains(b)
				&& e.vertices().size() == 2);// TestVertices
		assertTrue(
				e.sourceVertices().size() == 2 && e.sourceVertices().contains(a));// TestSourceVertices
		assertTrue(
				e.targetVertices().size() == 2 && e.targetVertices().contains(b));// TestTargetVertices
		assertEquals(e.toString(),e.toString());
	}
}
