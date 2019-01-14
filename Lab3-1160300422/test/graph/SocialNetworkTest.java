package graph;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import edge.Edge;
import factory.EdgeFactory.CommentTieFactory;
import factory.EdgeFactory.ForwardTieFactory;
import factory.EdgeFactory.FriendTieFactory;
import factory.GraphFactory.SocialNetworkFactory;
import factory.VertexFactory.PersonVertexFactory;
import helper.GraphMetrics;
import vertex.Vertex;
import static org.junit.Assert.*;

public class SocialNetworkTest extends ConcreteGraphTest {

	@Override
	public void testAddVertex() {
		SocialNetwork g = new SocialNetwork();
		Vertex Ren = new PersonVertexFactory().createVertex("Ren", new String[] { "M", "21" });
		Vertex Li = new PersonVertexFactory().createVertex("Li", new String[] { "F", "20" });
		Vertex An = new PersonVertexFactory().createVertex("An", new String[] { "F", "21" });
		g.addVertex(Ren);
		g.addVertex(Li);
		g.addVertex(An);
		assertTrue(g.vertices().contains(Ren) && g.vertices().contains(Li) && g.vertices().contains(An));
	}

	@Override
	public void testVertices() {
		SocialNetwork g = new SocialNetwork();
		Vertex Ren = new PersonVertexFactory().createVertex("Ren", new String[] { "M", "21" });
		Vertex Li = new PersonVertexFactory().createVertex("Li", new String[] { "F", "20" });
		Vertex An = new PersonVertexFactory().createVertex("An", new String[] { "F", "21" });
		Edge E1 = new CommentTieFactory().createEdge("E1", Arrays.asList(Li, Ren), 1);
		Edge E2 = new FriendTieFactory().createEdge("E2", Arrays.asList(Li, An), 0.5);
		Edge E3 = new ForwardTieFactory().createEdge("E3", Arrays.asList(Ren, An), 0.5);
		g.addEdge(E1);
		g.addEdge(E2);
		g.addEdge(E3);
		assertTrue(g.vertices().contains(Ren) && g.vertices().contains(Li) && g.vertices().contains(An));

	}

	@Override
	public void testSources() {
		SocialNetwork g = new SocialNetwork();
		Vertex Ren = new PersonVertexFactory().createVertex("Ren", new String[] { "M", "21" });
		Vertex Li = new PersonVertexFactory().createVertex("Li", new String[] { "F", "20" });
		Vertex An = new PersonVertexFactory().createVertex("An", new String[] { "F", "21" });
		Edge E1 = new CommentTieFactory().createEdge("E1", Arrays.asList(Li, Ren), 1);
		Edge E2 = new FriendTieFactory().createEdge("E2", Arrays.asList(Li, An), 0.5);
		Edge E3 = new ForwardTieFactory().createEdge("E3", Arrays.asList(Ren, An), 0.5);
		Edge E4 = new CommentTieFactory().createEdge("E4", Arrays.asList(Ren, An), 0.6);
		g.addEdge(E1);
		g.addEdge(E2);
		g.addEdge(E3);
		g.addEdge(E4);
		assertTrue(g.sources(An).get(Li).contains(0.1));
		assertTrue(g.sources(An).get(Ren).contains(0.2) && g.sources(An).get(Ren).contains(0.6));

	}

	@Override
	public void testTargets() {
		SocialNetwork g = new SocialNetwork();
		Vertex Ren = new PersonVertexFactory().createVertex("Ren", new String[] { "M", "21" });
		Vertex Li = new PersonVertexFactory().createVertex("Li", new String[] { "F", "20" });
		Vertex An = new PersonVertexFactory().createVertex("An", new String[] { "F", "21" });
		Edge E1 = new CommentTieFactory().createEdge("E1", Arrays.asList(Li, Ren), 1);
		Edge E2 = new FriendTieFactory().createEdge("E2", Arrays.asList(Li, An), 0.5);
		Edge E3 = new ForwardTieFactory().createEdge("E3", Arrays.asList(Ren, An), 0.5);
		Edge E4 = new CommentTieFactory().createEdge("E4", Arrays.asList(Li, An), 0.6);
		g.addEdge(E1);
		g.addEdge(E2);
		g.addEdge(E3);
		g.addEdge(E4);
		assertTrue(g.targets(Li).get(Ren).contains(0.1));
		assertTrue(g.targets(Li).get(An).contains(0.1) && g.targets(Li).get(An).contains(0.6));
	}

	@Override
	public void testAddEdge() {
		SocialNetwork g = new SocialNetwork();
		Vertex Ren = new PersonVertexFactory().createVertex("Ren", new String[] { "M", "21" });
		Vertex Li = new PersonVertexFactory().createVertex("Li", new String[] { "F", "20" });
		Vertex An = new PersonVertexFactory().createVertex("An", new String[] { "F", "21" });
		Edge E1 = new CommentTieFactory().createEdge("E1", Arrays.asList(Li, Ren), 1);
		Edge E2 = new FriendTieFactory().createEdge("E2", Arrays.asList(Li, An), 0.5);
		Edge E3 = new ForwardTieFactory().createEdge("E3", Arrays.asList(Ren, An), 0.5);
		g.addEdge(E1);
		g.addEdge(E2);
		g.addEdge(E3);
		assertEquals(0.25, E1.getWeight(), 0.01);

		Edge E4 = new ForwardTieFactory().createEdge("E4", Arrays.asList(Ren, An), 0.6);
		g.addEdge(E4);
		assertEquals(0.2, E1.getWeight(), 0.01);

		Edge E5 = new FriendTieFactory().createEdge("E1", Arrays.asList(Ren, An), 0.6);
		g.addEdge(E5);
		assertEquals(0.3, E3.getWeight(), 0.01);

	}

	@Override
	public void testRemoveVertex() {
		SocialNetwork g = new SocialNetwork();
		Vertex Ren = new PersonVertexFactory().createVertex("Ren", new String[] { "M", "21" });
		Vertex Li = new PersonVertexFactory().createVertex("Li", new String[] { "F", "20" });
		Vertex An = new PersonVertexFactory().createVertex("An", new String[] { "F", "21" });
		Edge E1 = new CommentTieFactory().createEdge("E1", Arrays.asList(Li, Ren), 1);
		Edge E2 = new FriendTieFactory().createEdge("E2", Arrays.asList(Li, An), 0.5);
		Edge E3 = new ForwardTieFactory().createEdge("E3", Arrays.asList(Ren, An), 0.5);
		Edge E4 = new CommentTieFactory().createEdge("E4", Arrays.asList(Li, An), 0.6);
		g.addEdge(E1);
		g.addEdge(E2);
		g.addEdge(E3);
		g.addEdge(E4);
		g.removeVertex(An);

		assertEquals(1.0, E1.getWeight(), 0.01);
	}

	@Override
	public void testRemoveEdge() {
		SocialNetwork g = new SocialNetwork();
		Vertex Ren = new PersonVertexFactory().createVertex("Ren", new String[] { "M", "21" });
		Vertex Li = new PersonVertexFactory().createVertex("Li", new String[] { "F", "20" });
		Vertex An = new PersonVertexFactory().createVertex("An", new String[] { "F", "21" });
		Edge E1 = new CommentTieFactory().createEdge("E1", Arrays.asList(Li, Ren), 1);
		Edge E2 = new FriendTieFactory().createEdge("E2", Arrays.asList(Li, An), 0.5);
		Edge E3 = new ForwardTieFactory().createEdge("E3", Arrays.asList(Ren, An), 0.5);
		Edge E4 = new CommentTieFactory().createEdge("E4", Arrays.asList(Li, An), 0.6);
		g.addEdge(E1);
		g.addEdge(E2);
		g.addEdge(E3);
		g.addEdge(E4);
		g.removeEdge(E4);
		assertEquals(0.25, E2.getWeight(), 0.01);
		g.removeEdge(E3);
		assertEquals(0.5, E2.getWeight(), 0.01);
		g.removeEdge(E2);
		assertEquals(1.0, E1.getWeight(), 0.01);

	}

	@Override
	public void testEdges() {
		SocialNetwork g = new SocialNetwork();
		Vertex Ren = new PersonVertexFactory().createVertex("Ren", new String[] { "M", "21" });
		Vertex Li = new PersonVertexFactory().createVertex("Li", new String[] { "F", "20" });
		Vertex An = new PersonVertexFactory().createVertex("An", new String[] { "F", "21" });
		Edge E1 = new CommentTieFactory().createEdge("E1", Arrays.asList(Li, Ren), 1);
		Edge E2 = new FriendTieFactory().createEdge("E2", Arrays.asList(Li, An), 0.5);
		Edge E3 = new ForwardTieFactory().createEdge("E3", Arrays.asList(Ren, An), 0.5);
		Edge E4 = new CommentTieFactory().createEdge("E4", Arrays.asList(Li, An), 0.6);
		g.addEdge(E1);
		g.addEdge(E2);
		g.addEdge(E3);
		g.addEdge(E4);
		Collection<Edge> edges = g.edges();
		assertTrue(edges.contains(E1) && edges.contains(E2) && edges.contains(E3) && edges.contains(E4));

	}

	@Test
	public void testSocialNetworkFactory() {
		SocialNetwork g = new SocialNetworkFactory().createGraph("src/factory/GraphFactory/SocialNetworkTest.txt");
		assertEquals(3, g.edges().size());
	}
}
