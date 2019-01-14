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
import factory.VertexFactory.ActorVertexFactory;
import factory.VertexFactory.DirectorVertexFactory;
import factory.VertexFactory.MovieVertexFactory;
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
		Vertex a3 = new ActorVertexFactory().createVertex("a3", new String[] {"23","M"});
		Vertex a4 = new ActorVertexFactory().createVertex("a4", new String[] {"24","F"});
		Vertex m2 = new MovieVertexFactory().createVertex("m2", new String[] {"2017","American","8.8"});
		Edge dm1 = new MovieDirectorRelationFactory().createEdge("dm1", Arrays.asList(d1,m1), -1);
		Edge dm2 = new MovieDirectorRelationFactory().createEdge("dm2", Arrays.asList(d1,m2), -1);
		Edge m1a1 = new MovieActorRelationFactory().createEdge("m1a1", Arrays.asList(m1,a1), 100);
		Edge m1a2 = new MovieActorRelationFactory().createEdge("m1a2", Arrays.asList(m1,a2), 50);
		Edge m1a3 = new MovieActorRelationFactory().createEdge("m1a3", Arrays.asList(m1,a3), 25);
		Edge m2a2 = new MovieActorRelationFactory().createEdge("m2a2", Arrays.asList(m2,a2), 90);
		Edge m2a3 = new MovieActorRelationFactory().createEdge("m2a3", Arrays.asList(m2,a3), 60);
		Edge m2a4 = new MovieActorRelationFactory().createEdge("m2a4", Arrays.asList(m2,a4), 40);
		Edge sa1 = new SameMovieHyperEdgeFactory().createEdge("sa1", Arrays.asList(a1,a2,a3), -1);
		Edge sa2 = new SameMovieHyperEdgeFactory().createEdge("sa2", Arrays.asList(a2,a3,a4), -1);
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
		assertEquals(40,g.sources(m2).get(a4).get(0),0.01);
		assertEquals(-1,g.sources(m2).get(d1).get(0),0.01);
		assertEquals(-1,g.sources(a2).get(a1).get(0),0.01);
	}

	@Override
	public void testTargets() {
		MovieGraph g = new MovieGraph();
		Vertex d1 = new DirectorVertexFactory().createVertex("d1", new String[] { "20", "M" });
		Vertex a1 = new ActorVertexFactory().createVertex("a1", new String[] { "21", "F" });
		Vertex m1 = new MovieVertexFactory().createVertex("m1", new String[] { "2018", "China", "9.9" });
		Vertex a2 = new ActorVertexFactory().createVertex("a2", new String[] { "21", "F" });
		Vertex a3 = new ActorVertexFactory().createVertex("a3", new String[] {"23","M"});
		Vertex a4 = new ActorVertexFactory().createVertex("a4", new String[] {"24","F"});
		Vertex m2 = new MovieVertexFactory().createVertex("m2", new String[] {"2017","American","8.8"});
		Edge dm1 = new MovieDirectorRelationFactory().createEdge("dm1", Arrays.asList(d1,m1), -1);
		Edge dm2 = new MovieDirectorRelationFactory().createEdge("dm2", Arrays.asList(d1,m2), -1);
		Edge m1a1 = new MovieActorRelationFactory().createEdge("m1a1", Arrays.asList(m1,a1), 100);
		Edge m1a2 = new MovieActorRelationFactory().createEdge("m1a2", Arrays.asList(m1,a2), 50);
		Edge m1a3 = new MovieActorRelationFactory().createEdge("m1a3", Arrays.asList(m1,a3), 25);
		Edge m2a2 = new MovieActorRelationFactory().createEdge("m2a2", Arrays.asList(m2,a2), 90);
		Edge m2a3 = new MovieActorRelationFactory().createEdge("m2a3", Arrays.asList(m2,a3), 60);
		Edge m2a4 = new MovieActorRelationFactory().createEdge("m2a4", Arrays.asList(m2,a4), 40);
		Edge sa1 = new SameMovieHyperEdgeFactory().createEdge("sa1", Arrays.asList(a1,a2,a3), -1);
		Edge sa2 = new SameMovieHyperEdgeFactory().createEdge("sa2", Arrays.asList(a2,a3,a4), -1);
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
		assertEquals(40,g.targets(m2).get(a4).get(0),0.01);
		assertEquals(-1,g.targets(m2).get(d1).get(0),0.01);
		assertEquals(-1,g.targets(a2).get(a1).get(0),0.01);
	}

	@Override
	public void testAddEdge() {
		MovieGraph g = new MovieGraph();
		Vertex d1 = new DirectorVertexFactory().createVertex("d1", new String[] { "20", "M" });
		Vertex a1 = new ActorVertexFactory().createVertex("a1", new String[] { "21", "F" });
		Vertex m1 = new MovieVertexFactory().createVertex("m1", new String[] { "2018", "China", "9.9" });
		Vertex a2 = new ActorVertexFactory().createVertex("a2", new String[] { "21", "F" });
		Vertex a3 = new ActorVertexFactory().createVertex("a3", new String[] {"23","M"});
		Vertex a4 = new ActorVertexFactory().createVertex("a4", new String[] {"24","F"});
		Vertex m2 = new MovieVertexFactory().createVertex("m2", new String[] {"2017","American","8.8"});
		Edge dm1 = new MovieDirectorRelationFactory().createEdge("dm1", Arrays.asList(d1,m1), -1);
		Edge dm2 = new MovieDirectorRelationFactory().createEdge("dm2", Arrays.asList(d1,m2), -1);
		Edge m1a1 = new MovieActorRelationFactory().createEdge("m1a1", Arrays.asList(m1,a1), 100);
		Edge m1a2 = new MovieActorRelationFactory().createEdge("m1a2", Arrays.asList(m1,a2), 50);
		Edge m1a3 = new MovieActorRelationFactory().createEdge("m1a3", Arrays.asList(m1,a3), 25);
		Edge m2a2 = new MovieActorRelationFactory().createEdge("m2a2", Arrays.asList(m2,a2), 90);
		Edge m2a3 = new MovieActorRelationFactory().createEdge("m2a3", Arrays.asList(m2,a3), 60);
		Edge m2a4 = new MovieActorRelationFactory().createEdge("m2a4", Arrays.asList(m2,a4), 40);
		Edge sa1 = new SameMovieHyperEdgeFactory().createEdge("sa1", Arrays.asList(a1,a2,a3), -1);
		Edge sa2 = new SameMovieHyperEdgeFactory().createEdge("sa2", Arrays.asList(a2,a3,a4), -1);
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
		assertEquals(10,g.edges().size());
	}

	@Override
	public void testRemoveVertex() {
		MovieGraph g = new MovieGraph();
		Vertex d1 = new DirectorVertexFactory().createVertex("d1", new String[] { "20", "M" });
		Vertex a1 = new ActorVertexFactory().createVertex("a1", new String[] { "21", "F" });
		Vertex m1 = new MovieVertexFactory().createVertex("m1", new String[] { "2018", "China", "9.9" });
		Vertex a2 = new ActorVertexFactory().createVertex("a2", new String[] { "21", "F" });
		Vertex a3 = new ActorVertexFactory().createVertex("a3", new String[] {"23","M"});
		Vertex a4 = new ActorVertexFactory().createVertex("a4", new String[] {"24","F"});
		Vertex m2 = new MovieVertexFactory().createVertex("m2", new String[] {"2017","American","8.8"});
		Edge dm1 = new MovieDirectorRelationFactory().createEdge("dm1", Arrays.asList(d1,m1), -1);
		Edge dm2 = new MovieDirectorRelationFactory().createEdge("dm2", Arrays.asList(d1,m2), -1);
		Edge m1a1 = new MovieActorRelationFactory().createEdge("m1a1", Arrays.asList(m1,a1), 100);
		Edge m1a2 = new MovieActorRelationFactory().createEdge("m1a2", Arrays.asList(m1,a2), 50);
		Edge m1a3 = new MovieActorRelationFactory().createEdge("m1a3", Arrays.asList(m1,a3), 25);
		Edge m2a2 = new MovieActorRelationFactory().createEdge("m2a2", Arrays.asList(m2,a2), 90);
		Edge m2a3 = new MovieActorRelationFactory().createEdge("m2a3", Arrays.asList(m2,a3), 60);
		Edge m2a4 = new MovieActorRelationFactory().createEdge("m2a4", Arrays.asList(m2,a4), 40);
		Edge sa1 = new SameMovieHyperEdgeFactory().createEdge("sa1", Arrays.asList(a1,a2,a3), -1);
		Edge sa2 = new SameMovieHyperEdgeFactory().createEdge("sa2", Arrays.asList(a2,a3,a4), -1);
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
		assertEquals(6,g.vertices().size());
		assertEquals(8,g.edges().size());
	}

	@Override
	public void testRemoveEdge() {
		MovieGraph g = new MovieGraph();
		Vertex d1 = new DirectorVertexFactory().createVertex("d1", new String[] { "20", "M" });
		Vertex a1 = new ActorVertexFactory().createVertex("a1", new String[] { "21", "F" });
		Vertex m1 = new MovieVertexFactory().createVertex("m1", new String[] { "2018", "China", "9.9" });
		Vertex a2 = new ActorVertexFactory().createVertex("a2", new String[] { "21", "F" });
		Vertex a3 = new ActorVertexFactory().createVertex("a3", new String[] {"23","M"});
		Vertex a4 = new ActorVertexFactory().createVertex("a4", new String[] {"24","F"});
		Vertex m2 = new MovieVertexFactory().createVertex("m2", new String[] {"2017","American","8.8"});
		Edge dm1 = new MovieDirectorRelationFactory().createEdge("dm1", Arrays.asList(d1,m1), -1);
		Edge dm2 = new MovieDirectorRelationFactory().createEdge("dm2", Arrays.asList(d1,m2), -1);
		Edge m1a1 = new MovieActorRelationFactory().createEdge("m1a1", Arrays.asList(m1,a1), 100);
		Edge m1a2 = new MovieActorRelationFactory().createEdge("m1a2", Arrays.asList(m1,a2), 50);
		Edge m1a3 = new MovieActorRelationFactory().createEdge("m1a3", Arrays.asList(m1,a3), 25);
		Edge m2a2 = new MovieActorRelationFactory().createEdge("m2a2", Arrays.asList(m2,a2), 90);
		Edge m2a3 = new MovieActorRelationFactory().createEdge("m2a3", Arrays.asList(m2,a3), 60);
		Edge m2a4 = new MovieActorRelationFactory().createEdge("m2a4", Arrays.asList(m2,a4), 40);
		Edge sa1 = new SameMovieHyperEdgeFactory().createEdge("sa1", Arrays.asList(a1,a2,a3), -1);
		Edge sa2 = new SameMovieHyperEdgeFactory().createEdge("sa2", Arrays.asList(a2,a3,a4), -1);
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
		assertEquals(9,g.edges().size());
	}

	@Override
	public void testEdges() {
		MovieGraph g = new MovieGraph();
		Vertex d1 = new DirectorVertexFactory().createVertex("d1", new String[] { "20", "M" });
		Vertex a1 = new ActorVertexFactory().createVertex("a1", new String[] { "21", "F" });
		Vertex m1 = new MovieVertexFactory().createVertex("m1", new String[] { "2018", "China", "9.9" });
		Vertex a2 = new ActorVertexFactory().createVertex("a2", new String[] { "21", "F" });
		Vertex a3 = new ActorVertexFactory().createVertex("a3", new String[] {"23","M"});
		Vertex a4 = new ActorVertexFactory().createVertex("a4", new String[] {"24","F"});
		Vertex m2 = new MovieVertexFactory().createVertex("m2", new String[] {"2017","American","8.8"});
		Edge dm1 = new MovieDirectorRelationFactory().createEdge("dm1", Arrays.asList(d1,m1), -1);
		Edge dm2 = new MovieDirectorRelationFactory().createEdge("dm2", Arrays.asList(d1,m2), -1);
		Edge m1a1 = new MovieActorRelationFactory().createEdge("m1a1", Arrays.asList(m1,a1), 100);
		Edge m1a2 = new MovieActorRelationFactory().createEdge("m1a2", Arrays.asList(m1,a2), 50);
		Edge m1a3 = new MovieActorRelationFactory().createEdge("m1a3", Arrays.asList(m1,a3), 25);
		Edge m2a2 = new MovieActorRelationFactory().createEdge("m2a2", Arrays.asList(m2,a2), 90);
		Edge m2a3 = new MovieActorRelationFactory().createEdge("m2a3", Arrays.asList(m2,a3), 60);
		Edge m2a4 = new MovieActorRelationFactory().createEdge("m2a4", Arrays.asList(m2,a4), 40);
		Edge sa1 = new SameMovieHyperEdgeFactory().createEdge("sa1", Arrays.asList(a1,a2,a3), -1);
		Edge sa2 = new SameMovieHyperEdgeFactory().createEdge("sa2", Arrays.asList(a2,a3,a4), -1);
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
		assertEquals(10,g.edges().size());
	}

	@Test
	public void testMovieGraphFactory() {
		MovieGraph g = new MovieGraphFactory().createGraph("src/factory/GraphFactory/MovieGraphTest.txt");
		assertEquals(10,g.edges().size());
	}
}
