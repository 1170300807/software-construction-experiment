package graph;

import org.junit.Test;

import edge.Edge;
import edge.MovieDirectorRelation;

import static org.junit.Assert.*;

import java.util.Arrays;

import factory.EdgeFactory.MovieActorRelationFactory;
import factory.EdgeFactory.MovieDirectorRelationFactory;
import factory.EdgeFactory.SameMovieHyperEdgeFactory;
import factory.GraphFactory.MovieGraphFactory;
import factory.GraphFactory.IO1;
import factory.GraphFactory.IO2;
import factory.GraphFactory.IO3;
import factory.GraphFactory.IOStrategy;
import factory.VertexFactory.ActorVertexFactory;
import factory.VertexFactory.DirectorVertexFactory;
import factory.VertexFactory.MovieVertexFactory;
import myException.InvalidCommander;
import myException.InvalidEdgeType;
import myException.InvalidLabel;
import myException.InvalidVertexType;
import myException.InvalidWeight;
import myException.NoAppropriateAttributes;
import myException.NotEnoughVerticesForHyperedge;
import myException.NotExistedVertices;
import myException.graphExceptions;
import vertex.Vertex;

public class MovieGraphTest extends ConcreteGraphTest {

	@Override
	public void testAddVertex() {
		MovieGraph g = new MovieGraph();
		Vertex v1 = new DirectorVertexFactory().createVertex("v1", new String[] { "20", "M" });
		Vertex v2 = new ActorVertexFactory().createVertex("v2", new String[] { "21", "F" });
		Vertex v3 = new MovieVertexFactory().createVertex("v3", new String[] { "2018", "China", "9.9" });
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		assertEquals(3, g.vertices().size());
		assertTrue(g.vertices().contains(v1) && g.vertices().contains(v2) && g.vertices().contains(v3));
	}

	@Override
	public void testVertices() {
		MovieGraph g = new MovieGraph();
		Vertex v1 = new DirectorVertexFactory().createVertex("v1", new String[] { "20", "M" });
		Vertex v2 = new ActorVertexFactory().createVertex("v2", new String[] { "21", "F" });
		Vertex v3 = new MovieVertexFactory().createVertex("v3", new String[] { "2018", "China", "9.9" });
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		assertEquals(3, g.vertices().size());
		assertTrue(g.vertices().contains(v1) && g.vertices().contains(v2) && g.vertices().contains(v3));
	}

	@Override
	public void testSources() {
		MovieGraph g = new MovieGraph();
		Vertex d1 = new DirectorVertexFactory().createVertex("d1", new String[] { "20", "M" });
		Vertex a1 = new ActorVertexFactory().createVertex("a1", new String[] { "21", "F" });
		Vertex m1 = new MovieVertexFactory().createVertex("m1", new String[] { "2018", "China", "9.9" });
		Vertex a2 = new ActorVertexFactory().createVertex("a2", new String[] { "21", "F" });
		Vertex a3 = new ActorVertexFactory().createVertex("a3", new String[] { "23", "M" });
		Vertex a4 = new ActorVertexFactory().createVertex("a4", new String[] { "24", "F" });
		Vertex m2 = new MovieVertexFactory().createVertex("m2", new String[] { "2017", "American", "8.8" });
		Edge dm1 = new MovieDirectorRelationFactory().createEdge("dm1", Arrays.asList(d1, m1), -1);
		Edge dm2 = new MovieDirectorRelationFactory().createEdge("dm2", Arrays.asList(d1, m2), -1);
		Edge m1a1 = new MovieActorRelationFactory().createEdge("m1a1", Arrays.asList(m1, a1), 100);
		Edge m1a2 = new MovieActorRelationFactory().createEdge("m1a2", Arrays.asList(m1, a2), 50);
		Edge m1a3 = new MovieActorRelationFactory().createEdge("m1a3", Arrays.asList(m1, a3), 25);
		Edge m2a2 = new MovieActorRelationFactory().createEdge("m2a2", Arrays.asList(m2, a2), 90);
		Edge m2a3 = new MovieActorRelationFactory().createEdge("m2a3", Arrays.asList(m2, a3), 60);
		Edge m2a4 = new MovieActorRelationFactory().createEdge("m2a4", Arrays.asList(m2, a4), 40);
		Edge sa1 = new SameMovieHyperEdgeFactory().createEdge("sa1", Arrays.asList(a1, a2, a3), -1);
		Edge sa2 = new SameMovieHyperEdgeFactory().createEdge("sa2", Arrays.asList(a2, a3, a4), -1);
		g.addEdge(dm1);
		g.addEdge(dm2);
		g.addEdge(m1a1);
		g.addEdge(m1a2);
		g.addEdge(m1a3);
		g.addEdge(m2a2);
		g.addEdge(m2a3);
		g.addEdge(m2a4);
		g.addEdge(sa1);
		g.addEdge(sa2);
		assertEquals(40, g.sources(m2).get(a4).get(0), 0.01);
		assertEquals(-1, g.sources(m2).get(d1).get(0), 0.01);
		assertEquals(-1, g.sources(a2).get(a1).get(0), 0.01);
	}

	@Override
	public void testTargets() {
		MovieGraph g = new MovieGraph();
		Vertex d1 = new DirectorVertexFactory().createVertex("d1", new String[] { "20", "M" });
		Vertex a1 = new ActorVertexFactory().createVertex("a1", new String[] { "21", "F" });
		Vertex m1 = new MovieVertexFactory().createVertex("m1", new String[] { "2018", "China", "9.9" });
		Vertex a2 = new ActorVertexFactory().createVertex("a2", new String[] { "21", "F" });
		Vertex a3 = new ActorVertexFactory().createVertex("a3", new String[] { "23", "M" });
		Vertex a4 = new ActorVertexFactory().createVertex("a4", new String[] { "24", "F" });
		Vertex m2 = new MovieVertexFactory().createVertex("m2", new String[] { "2017", "American", "8.8" });
		Edge dm1 = new MovieDirectorRelationFactory().createEdge("dm1", Arrays.asList(d1, m1), -1);
		Edge dm2 = new MovieDirectorRelationFactory().createEdge("dm2", Arrays.asList(d1, m2), -1);
		Edge m1a1 = new MovieActorRelationFactory().createEdge("m1a1", Arrays.asList(m1, a1), 100);
		Edge m1a2 = new MovieActorRelationFactory().createEdge("m1a2", Arrays.asList(m1, a2), 50);
		Edge m1a3 = new MovieActorRelationFactory().createEdge("m1a3", Arrays.asList(m1, a3), 25);
		Edge m2a2 = new MovieActorRelationFactory().createEdge("m2a2", Arrays.asList(m2, a2), 90);
		Edge m2a3 = new MovieActorRelationFactory().createEdge("m2a3", Arrays.asList(m2, a3), 60);
		Edge m2a4 = new MovieActorRelationFactory().createEdge("m2a4", Arrays.asList(m2, a4), 40);
		Edge sa1 = new SameMovieHyperEdgeFactory().createEdge("sa1", Arrays.asList(a1, a2, a3), -1);
		Edge sa2 = new SameMovieHyperEdgeFactory().createEdge("sa2", Arrays.asList(a2, a3, a4), -1);
		g.addEdge(dm1);
		g.addEdge(dm2);
		g.addEdge(m1a1);
		g.addEdge(m1a2);
		g.addEdge(m1a3);
		g.addEdge(m2a2);
		g.addEdge(m2a3);
		g.addEdge(m2a4);
		g.addEdge(sa1);
		g.addEdge(sa2);
		assertEquals(40, g.targets(m2).get(a4).get(0), 0.01);
		assertEquals(-1, g.targets(m2).get(d1).get(0), 0.01);
		assertEquals(-1, g.targets(a2).get(a1).get(0), 0.01);
	}

	@Override
	public void testAddEdge() {
		MovieGraph g = new MovieGraph();
		Vertex d1 = new DirectorVertexFactory().createVertex("d1", new String[] { "20", "M" });
		Vertex a1 = new ActorVertexFactory().createVertex("a1", new String[] { "21", "F" });
		Vertex m1 = new MovieVertexFactory().createVertex("m1", new String[] { "2018", "China", "9.9" });
		Vertex a2 = new ActorVertexFactory().createVertex("a2", new String[] { "21", "F" });
		Vertex a3 = new ActorVertexFactory().createVertex("a3", new String[] { "23", "M" });
		Vertex a4 = new ActorVertexFactory().createVertex("a4", new String[] { "24", "F" });
		Vertex m2 = new MovieVertexFactory().createVertex("m2", new String[] { "2017", "American", "8.8" });
		Edge dm1 = new MovieDirectorRelationFactory().createEdge("dm1", Arrays.asList(d1, m1), -1);
		Edge dm2 = new MovieDirectorRelationFactory().createEdge("dm2", Arrays.asList(d1, m2), -1);
		Edge m1a1 = new MovieActorRelationFactory().createEdge("m1a1", Arrays.asList(m1, a1), 100);
		Edge m1a2 = new MovieActorRelationFactory().createEdge("m1a2", Arrays.asList(m1, a2), 50);
		Edge m1a3 = new MovieActorRelationFactory().createEdge("m1a3", Arrays.asList(m1, a3), 25);
		Edge m2a2 = new MovieActorRelationFactory().createEdge("m2a2", Arrays.asList(m2, a2), 90);
		Edge m2a3 = new MovieActorRelationFactory().createEdge("m2a3", Arrays.asList(m2, a3), 60);
		Edge m2a4 = new MovieActorRelationFactory().createEdge("m2a4", Arrays.asList(m2, a4), 40);
		Edge sa1 = new SameMovieHyperEdgeFactory().createEdge("sa1", Arrays.asList(a1, a2, a3), -1);
		Edge sa2 = new SameMovieHyperEdgeFactory().createEdge("sa2", Arrays.asList(a2, a3, a4), -1);
		g.addEdge(dm1);
		g.addEdge(dm2);
		g.addEdge(m1a1);
		g.addEdge(m1a2);
		g.addEdge(m1a3);
		g.addEdge(m2a2);
		g.addEdge(m2a3);
		g.addEdge(m2a4);
		g.addEdge(sa1);
		g.addEdge(sa2);
		assertEquals(10, g.edges().size());
	}

	@Override
	public void testRemoveVertex() {
		MovieGraph g = new MovieGraph();
		Vertex d1 = new DirectorVertexFactory().createVertex("d1", new String[] { "20", "M" });
		Vertex a1 = new ActorVertexFactory().createVertex("a1", new String[] { "21", "F" });
		Vertex m1 = new MovieVertexFactory().createVertex("m1", new String[] { "2018", "China", "9.9" });
		Vertex a2 = new ActorVertexFactory().createVertex("a2", new String[] { "21", "F" });
		Vertex a3 = new ActorVertexFactory().createVertex("a3", new String[] { "23", "M" });
		Vertex a4 = new ActorVertexFactory().createVertex("a4", new String[] { "24", "F" });
		Vertex m2 = new MovieVertexFactory().createVertex("m2", new String[] { "2017", "American", "8.8" });
		Edge dm1 = new MovieDirectorRelationFactory().createEdge("dm1", Arrays.asList(d1, m1), -1);
		Edge dm2 = new MovieDirectorRelationFactory().createEdge("dm2", Arrays.asList(d1, m2), -1);
		Edge m1a1 = new MovieActorRelationFactory().createEdge("m1a1", Arrays.asList(m1, a1), 100);
		Edge m1a2 = new MovieActorRelationFactory().createEdge("m1a2", Arrays.asList(m1, a2), 50);
		Edge m1a3 = new MovieActorRelationFactory().createEdge("m1a3", Arrays.asList(m1, a3), 25);
		Edge m2a2 = new MovieActorRelationFactory().createEdge("m2a2", Arrays.asList(m2, a2), 90);
		Edge m2a3 = new MovieActorRelationFactory().createEdge("m2a3", Arrays.asList(m2, a3), 60);
		Edge m2a4 = new MovieActorRelationFactory().createEdge("m2a4", Arrays.asList(m2, a4), 40);
		Edge sa1 = new SameMovieHyperEdgeFactory().createEdge("sa1", Arrays.asList(a1, a2, a3), -1);
		Edge sa2 = new SameMovieHyperEdgeFactory().createEdge("sa2", Arrays.asList(a2, a3, a4), -1);
		g.addEdge(dm1);
		g.addEdge(dm2);
		g.addEdge(m1a1);
		g.addEdge(m1a2);
		g.addEdge(m1a3);
		g.addEdge(m2a2);
		g.addEdge(m2a3);
		g.addEdge(m2a4);
		g.addEdge(sa1);
		g.addEdge(sa2);
		g.removeVertex(d1);
		assertEquals(6, g.vertices().size());
		assertEquals(8, g.edges().size());
	}

	@Override
	public void testRemoveEdge() {
		MovieGraph g = new MovieGraph();
		Vertex d1 = new DirectorVertexFactory().createVertex("d1", new String[] { "20", "M" });
		Vertex a1 = new ActorVertexFactory().createVertex("a1", new String[] { "21", "F" });
		Vertex m1 = new MovieVertexFactory().createVertex("m1", new String[] { "2018", "China", "9.9" });
		Vertex a2 = new ActorVertexFactory().createVertex("a2", new String[] { "21", "F" });
		Vertex a3 = new ActorVertexFactory().createVertex("a3", new String[] { "23", "M" });
		Vertex a4 = new ActorVertexFactory().createVertex("a4", new String[] { "24", "F" });
		Vertex m2 = new MovieVertexFactory().createVertex("m2", new String[] { "2017", "American", "8.8" });
		Edge dm1 = new MovieDirectorRelationFactory().createEdge("dm1", Arrays.asList(d1, m1), -1);
		Edge dm2 = new MovieDirectorRelationFactory().createEdge("dm2", Arrays.asList(d1, m2), -1);
		Edge m1a1 = new MovieActorRelationFactory().createEdge("m1a1", Arrays.asList(m1, a1), 100);
		Edge m1a2 = new MovieActorRelationFactory().createEdge("m1a2", Arrays.asList(m1, a2), 50);
		Edge m1a3 = new MovieActorRelationFactory().createEdge("m1a3", Arrays.asList(m1, a3), 25);
		Edge m2a2 = new MovieActorRelationFactory().createEdge("m2a2", Arrays.asList(m2, a2), 90);
		Edge m2a3 = new MovieActorRelationFactory().createEdge("m2a3", Arrays.asList(m2, a3), 60);
		Edge m2a4 = new MovieActorRelationFactory().createEdge("m2a4", Arrays.asList(m2, a4), 40);
		Edge sa1 = new SameMovieHyperEdgeFactory().createEdge("sa1", Arrays.asList(a1, a2, a3), -1);
		Edge sa2 = new SameMovieHyperEdgeFactory().createEdge("sa2", Arrays.asList(a2, a3, a4), -1);
		g.addEdge(dm1);
		g.addEdge(dm2);
		g.addEdge(m1a1);
		g.addEdge(m1a2);
		g.addEdge(m1a3);
		g.addEdge(m2a2);
		g.addEdge(m2a3);
		g.addEdge(m2a4);
		g.addEdge(sa1);
		g.addEdge(sa2);
		g.removeEdge(m1a1);
		assertEquals(9, g.edges().size());
	}

	@Override
	public void testEdges() {
		MovieGraph g = new MovieGraph();
		Vertex d1 = new DirectorVertexFactory().createVertex("d1", new String[] { "20", "M" });
		Vertex a1 = new ActorVertexFactory().createVertex("a1", new String[] { "21", "F" });
		Vertex m1 = new MovieVertexFactory().createVertex("m1", new String[] { "2018", "China", "9.9" });
		Vertex a2 = new ActorVertexFactory().createVertex("a2", new String[] { "21", "F" });
		Vertex a3 = new ActorVertexFactory().createVertex("a3", new String[] { "23", "M" });
		Vertex a4 = new ActorVertexFactory().createVertex("a4", new String[] { "24", "F" });
		Vertex m2 = new MovieVertexFactory().createVertex("m2", new String[] { "2017", "American", "8.8" });
		Edge dm1 = new MovieDirectorRelationFactory().createEdge("dm1", Arrays.asList(d1, m1), -1);
		Edge dm2 = new MovieDirectorRelationFactory().createEdge("dm2", Arrays.asList(d1, m2), -1);
		Edge m1a1 = new MovieActorRelationFactory().createEdge("m1a1", Arrays.asList(m1, a1), 100);
		Edge m1a2 = new MovieActorRelationFactory().createEdge("m1a2", Arrays.asList(m1, a2), 50);
		Edge m1a3 = new MovieActorRelationFactory().createEdge("m1a3", Arrays.asList(m1, a3), 25);
		Edge m2a2 = new MovieActorRelationFactory().createEdge("m2a2", Arrays.asList(m2, a2), 90);
		Edge m2a3 = new MovieActorRelationFactory().createEdge("m2a3", Arrays.asList(m2, a3), 60);
		Edge m2a4 = new MovieActorRelationFactory().createEdge("m2a4", Arrays.asList(m2, a4), 40);
		Edge sa1 = new SameMovieHyperEdgeFactory().createEdge("sa1", Arrays.asList(a1, a2, a3), -1);
		Edge sa2 = new SameMovieHyperEdgeFactory().createEdge("sa2", Arrays.asList(a2, a3, a4), -1);
		g.addEdge(dm1);
		g.addEdge(dm2);
		g.addEdge(m1a1);
		g.addEdge(m1a2);
		g.addEdge(m1a3);
		g.addEdge(m2a2);
		g.addEdge(m2a3);
		g.addEdge(m2a4);
		g.addEdge(sa1);
		g.addEdge(sa2);
		assertEquals(10, g.edges().size());
	}

	@Test
	public void testMovieGraphFactory() throws graphExceptions {
		MovieGraph g;
		IOStrategy strategy = new IO3();
		
		g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphTest.txt");
		assertEquals(10, g.edges().size());
		
	}

	@Test
	public void testException() {
		MovieGraph g;
		IOStrategy strategy = new IO3();
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/1.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidCommander;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/2.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidCommander;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/3.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidCommander;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/4.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidLabel;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/5.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidCommander;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/6.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidVertexType;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/7.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidCommander;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/8.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidLabel;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/9.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidVertexType;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/10.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/11.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/12.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/12+1.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/12+2.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/12+3.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/12+4.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/12+5.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/13.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/13+1.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/13+2.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/13+3.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/14.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/14+1.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/14+2.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/15.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidCommander;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/16.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidEdgeType;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/17.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidCommander;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/18.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidLabel;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/19.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidEdgeType;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/20.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidWeight;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/21.txt");
			assertEquals(10, g.edges().size());
		} catch (graphExceptions e) {
			assert false;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/22.txt");
			assertEquals(10, g.edges().size());
		} catch (graphExceptions e) {
			assert false;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/23.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NotExistedVertices;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/24.txt");
			assertEquals(10, g.edges().size());
		} catch (graphExceptions e) {
			assert false;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/24+1.txt");
			assertEquals(10, g.edges().size());
		} catch (graphExceptions e) {
			assert false;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/25.txt");
			assertEquals(10, g.edges().size());
		} catch (graphExceptions e) {
			assert false;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/26.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidCommander;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/27.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidLabel;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/28.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidEdgeType;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/29.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NotEnoughVerticesForHyperedge;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/30.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof InvalidVertexType;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/31.txt");
			assert false; 
		} catch (graphExceptions e) {
			assert e instanceof InvalidCommander;
		}
		try {
			g = new MovieGraphFactory().createGraph(strategy,"test/graph/MovieGraphExceptionTest/32.txt");
			assert false;
		} catch (graphExceptions e) {
			assert e instanceof NoAppropriateAttributes;
		}
	}
}
