package graph;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;
import java.util.Set;

import edge.Edge;
import factory.EdgeFactory.WordNeighborhoodEdgeFactory;
import factory.GraphFactory.GraphPoetFactory;
import factory.VertexFactory.WordVertexFactory;
import vertex.Vertex;

public class GraphPoetTest extends ConcreteGraphTest {

	public static GraphPoet empty() {
		return new GraphPoet();
	}

	@Override
	public void testAddEdge() {
		GraphPoet g = empty();
		Vertex v1 = new WordVertexFactory().createVertex("A", null);
		Vertex v2 = new WordVertexFactory().createVertex("B", null);
		Vertex v3 = new WordVertexFactory().createVertex("C", null);
		Edge e1 = new WordNeighborhoodEdgeFactory().createEdge("E1", Arrays.asList(v1, v2), 1);
		Edge e2 = new WordNeighborhoodEdgeFactory().createEdge("E2", Arrays.asList(v2, v3), 1);
		Edge e3 = new WordNeighborhoodEdgeFactory().createEdge("E3", Arrays.asList(v1, v2), 1);
		Edge e4 = new WordNeighborhoodEdgeFactory().createEdge("E1", Arrays.asList(v1, v3), 1);
		boolean A = g.addEdge(e1);
		boolean B = g.addEdge(e2);
		Set<Vertex> vertices = g.vertices();
		assertTrue(vertices.contains(v1) && vertices.contains(v2) && vertices.contains(v3));
		assertTrue(g.edges().contains(e1) && g.edges().contains(e2));
		boolean C = g.addEdge(e3);
		assertEquals(2, e1.getWeight(), 0.01);
		boolean D = g.addEdge(e4);
		assertFalse(g.edges().contains(e1));
		assertTrue(g.edges().contains(e4));
		assertEquals(true, A);
		assertEquals(true, B);
		assertEquals(false, C);
		assertEquals(false, D);

	}

	@Override
	public void testAddVertex() {
		GraphPoet g = empty();
		Vertex v1 = new WordVertexFactory().createVertex("A", null);
		Vertex v2 = new WordVertexFactory().createVertex("B", null);
		Vertex v3 = new WordVertexFactory().createVertex("C", null);
		Vertex v4 = new WordVertexFactory().createVertex("D", null);
		boolean A = g.addVertex(v1);
		boolean B = g.addVertex(v2);
		boolean C = g.addVertex(v3);
		boolean D = g.addVertex(v1);
		assertEquals(true, A);
		assertEquals(true, B);
		assertEquals(true, C);
		assertEquals(false, D);
		Set<Vertex> s = g.vertices();
		assertTrue(s.contains(v1) && s.contains(v2) && s.contains(v3) && !s.contains(v4));
	}

	@Override
	public void testVertices() {
		GraphPoet g = empty();
		Vertex v1 = new WordVertexFactory().createVertex("A", null);
		Vertex v2 = new WordVertexFactory().createVertex("B", null);
		Vertex v3 = new WordVertexFactory().createVertex("C", null);
		Vertex v4 = new WordVertexFactory().createVertex("D", null);
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v1);
		Set<Vertex> s = g.vertices();
		assertTrue(s.contains(v1) && s.contains(v2) && s.contains(v3) && !s.contains(v4));

	}

	@Override
	public void testSources() {
		GraphPoet g = empty();
		Vertex v1 = new WordVertexFactory().createVertex("A", null);
		Vertex v2 = new WordVertexFactory().createVertex("B", null);
		Vertex v3 = new WordVertexFactory().createVertex("C", null);
		Edge e1 = new WordNeighborhoodEdgeFactory().createEdge("E1", Arrays.asList(v1, v2), 1);

		Edge e3 = new WordNeighborhoodEdgeFactory().createEdge("E3", Arrays.asList(v1, v2), 1);
		Edge e4 = new WordNeighborhoodEdgeFactory().createEdge("E4", Arrays.asList(v2, v2), 1);
		g.addEdge(e1);

		g.addEdge(e3);
		g.addEdge(e4);
		assertEquals(2, g.sources(v2).get(v1).get(0), 0.01);
		assertEquals(1, g.sources(v2).get(v2).get(0), 0.01);
		assertTrue(g.sources(v1).isEmpty());
	}

	@Override
	public void testTargets() {
		GraphPoet g = empty();
		Vertex v1 = new WordVertexFactory().createVertex("A", null);
		Vertex v2 = new WordVertexFactory().createVertex("B", null);
		Vertex v3 = new WordVertexFactory().createVertex("C", null);
		Edge e1 = new WordNeighborhoodEdgeFactory().createEdge("E1", Arrays.asList(v2, v3), 1);
		Edge e2 = new WordNeighborhoodEdgeFactory().createEdge("E2", Arrays.asList(v2, v3), 1);
		Edge e3 = new WordNeighborhoodEdgeFactory().createEdge("E3", Arrays.asList(v1, v2), 1);
		Edge e4 = new WordNeighborhoodEdgeFactory().createEdge("E4", Arrays.asList(v2, v2), 1);
		g.addEdge(e1);
		g.addEdge(e2);
		g.addEdge(e3);
		g.addEdge(e4);
		assertEquals(1, g.targets(v2).get(v2).get(0), 0.01);
		assertEquals(2, g.targets(v2).get(v3).get(0), 0.01);
		assertTrue(g.targets(v3).isEmpty());

	}

	@Override
	public void testRemoveEdge() {
		GraphPoet g = empty();
		Vertex v1 = new WordVertexFactory().createVertex("A", null);
		Vertex v2 = new WordVertexFactory().createVertex("B", null);
		Vertex v3 = new WordVertexFactory().createVertex("C", null);
		Edge e1 = new WordNeighborhoodEdgeFactory().createEdge("E1", Arrays.asList(v2, v3), 1);
		Edge e2 = new WordNeighborhoodEdgeFactory().createEdge("E2", Arrays.asList(v2, v3), 1);
		Edge e3 = new WordNeighborhoodEdgeFactory().createEdge("E3", Arrays.asList(v1, v2), 1);
		Edge e4 = new WordNeighborhoodEdgeFactory().createEdge("E4", Arrays.asList(v2, v2), 1);
		g.addEdge(e1);
		g.addEdge(e2);
		g.addEdge(e3);
		g.addEdge(e4);
		g.removeEdge(e1);
		assertEquals(2, g.edges().size());
		assertFalse(g.edges().contains(e1));
	}

	@Override
	public void testEdges() {
		GraphPoet g = empty();
		Vertex v1 = new WordVertexFactory().createVertex("A", null);
		Vertex v2 = new WordVertexFactory().createVertex("B", null);
		Vertex v3 = new WordVertexFactory().createVertex("C", null);
		Edge e1 = new WordNeighborhoodEdgeFactory().createEdge("E1", Arrays.asList(v2, v3), 1);
		Edge e2 = new WordNeighborhoodEdgeFactory().createEdge("E2", Arrays.asList(v2, v3), 1);
		Edge e3 = new WordNeighborhoodEdgeFactory().createEdge("E3", Arrays.asList(v1, v2), 1);
		Edge e4 = new WordNeighborhoodEdgeFactory().createEdge("E4", Arrays.asList(v2, v2), 1);
		g.addEdge(e1);
		g.addEdge(e2);
		g.addEdge(e3);
		g.addEdge(e4);
		assertEquals(3, g.edges().size());
		Set<Edge> s = g.edges();
		assertTrue(s.contains(e1) && s.contains(e3) && s.contains(e4));
	}

	@Override
	public void testRemoveVertex() {
		GraphPoet g = empty();
		Vertex v1 = new WordVertexFactory().createVertex("A", null);
		Vertex v2 = new WordVertexFactory().createVertex("B", null);
		Vertex v3 = new WordVertexFactory().createVertex("C", null);
		Edge e1 = new WordNeighborhoodEdgeFactory().createEdge("E1", Arrays.asList(v2, v3), 1);
		Edge e2 = new WordNeighborhoodEdgeFactory().createEdge("E2", Arrays.asList(v2, v3), 1);
		Edge e3 = new WordNeighborhoodEdgeFactory().createEdge("E3", Arrays.asList(v1, v2), 1);
		Edge e4 = new WordNeighborhoodEdgeFactory().createEdge("E4", Arrays.asList(v1, v3), 1);
		g.addEdge(e1);
		g.addEdge(e2);
		g.addEdge(e3);
		g.addEdge(e4);
		g.removeVertex(v2);
		assertEquals(1, g.edges().size());

	}

	@Test
	public void testGraphPoetFactory() {
		GraphPoet g = new GraphPoetFactory().createGraph("src/factory/GraphFactory/GraphPoetTest.txt");
		assertEquals(13, g.edges().size());
	}
}
