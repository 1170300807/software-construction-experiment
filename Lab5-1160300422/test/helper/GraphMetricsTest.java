package helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import edge.Edge;
import factory.EdgeFactory.CommentTieFactory;
import factory.EdgeFactory.ForwardTieFactory;
import factory.EdgeFactory.FriendTieFactory;
import factory.EdgeFactory.NetworkConnectionFactory;
import factory.VertexFactory.ComputerVertexFactory;
import factory.VertexFactory.PersonVertexFactory;
import factory.VertexFactory.RouterVertexFactory;
import factory.VertexFactory.ServerVertexFactory;
import graph.NetworkTopology;
import graph.SocialNetwork;
import vertex.Vertex;

public class GraphMetricsTest {
	@Test
	public void testDistance() {
		NetworkTopology g = new NetworkTopology();
		Vertex v1 = new ComputerVertexFactory().createVertex("v1", new String[] { "192.168.88.106" });
		Vertex v2 = new ServerVertexFactory().createVertex("v2", new String[] { "192.168.199.1" });
		Vertex v3 = new RouterVertexFactory().createVertex("v3", new String[] { "192.168.199.52" });
		Vertex v4 = new RouterVertexFactory().createVertex("v4", new String[] { "192.168.199.9" });
		Edge e1 = new NetworkConnectionFactory().createEdge("e1", Arrays.asList(v1, v2), 100);
		Edge e2 = new NetworkConnectionFactory().createEdge("e2", Arrays.asList(v3, v2), 50);
		Edge e3 = new NetworkConnectionFactory().createEdge("e3", Arrays.asList(v1, v3), 10);
		Edge e4 = new NetworkConnectionFactory().createEdge("e4", Arrays.asList(v4, v3), 100);
		g.addEdge(e1);
		g.addEdge(e2);
		g.addEdge(e3);
		g.addEdge(e4);

		assertEquals(0.126, GraphMetrics.closenessCentrality(g, v1), 0.01);

		Edge e5 = new NetworkConnectionFactory().createEdge("e5", Arrays.asList(v4, v1), 50);
		Edge e6 = new NetworkConnectionFactory().createEdge("e6", Arrays.asList(v4, v2), 200);
		Vertex v5 = new RouterVertexFactory().createVertex("v5", new String[] { "192.168.199.8" });
		Edge e7 = new NetworkConnectionFactory().createEdge("e7", Arrays.asList(v1, v5), 20);
		Edge e8 = new NetworkConnectionFactory().createEdge("e8", Arrays.asList(v4, v5), 20);
		g.addEdge(e5);
		g.addEdge(e6);
		g.addEdge(e7);
		g.addEdge(e8);
		assertEquals(40, GraphMetrics.distance(g, v1, v4), 0.01);
		assertEquals(100, GraphMetrics.distance(g, v2, v4), 0.01);
		assertEquals(0, GraphMetrics.distance(g, v3, v3), 0.01);
		assertEquals(50, GraphMetrics.distance(g, v3, v4), 0.01);
		assertEquals(60, GraphMetrics.distance(g, v1, v2), 0.01);
		assertEquals(80, GraphMetrics.distance(g, v5, v2), 0.01);
	}

	@Test
	public void testclosenessCentrality() {
		NetworkTopology g = new NetworkTopology();
		Vertex v1 = new ComputerVertexFactory().createVertex("v1", new String[] { "192.168.88.106" });
		Vertex v2 = new ServerVertexFactory().createVertex("v2", new String[] { "192.168.199.1" });
		Vertex v3 = new RouterVertexFactory().createVertex("v3", new String[] { "192.168.199.52" });
		Vertex v4 = new RouterVertexFactory().createVertex("v4", new String[] { "192.168.199.9" });
		Edge e1 = new NetworkConnectionFactory().createEdge("e1", Arrays.asList(v1, v2), 100);
		Edge e2 = new NetworkConnectionFactory().createEdge("e2", Arrays.asList(v3, v2), 50);
		Edge e3 = new NetworkConnectionFactory().createEdge("e3", Arrays.asList(v1, v3), 10);
		Edge e4 = new NetworkConnectionFactory().createEdge("e4", Arrays.asList(v4, v3), 100);
		g.addEdge(e1);
		g.addEdge(e2);
		g.addEdge(e3);
		g.addEdge(e4);

		assertEquals(0.126, GraphMetrics.closenessCentrality(g, v1), 0.01);
	}
	@Test
	public void testdegreeCentrality() {
		NetworkTopology g = new NetworkTopology();
		Vertex v1 = new ComputerVertexFactory().createVertex("v1", new String[] { "192.168.88.106" });
		Vertex v2 = new ServerVertexFactory().createVertex("v2", new String[] { "192.168.199.1" });
		Vertex v3 = new RouterVertexFactory().createVertex("v3", new String[] { "192.168.199.52" });
		Vertex v4 = new RouterVertexFactory().createVertex("v4", new String[] { "192.168.199.9" });
		Edge e1 = new NetworkConnectionFactory().createEdge("e1", Arrays.asList(v1, v2), 100);
		Edge e2 = new NetworkConnectionFactory().createEdge("e2", Arrays.asList(v3, v2), 50);
		Edge e3 = new NetworkConnectionFactory().createEdge("e3", Arrays.asList(v1, v3), 10);
		Edge e4 = new NetworkConnectionFactory().createEdge("e4", Arrays.asList(v4, v3), 100);
		g.addEdge(e1);
		g.addEdge(e2);
		g.addEdge(e3);
		g.addEdge(e4);
		assertEquals(0.67,GraphMetrics.degreeCentrality(g),0.01);
	}
	@Test
	public void testbetweennessCentrality() {
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
		assertEquals(0,GraphMetrics.betweennessCentrality(g, Ren),0.01);
		
		
		NetworkTopology gg = new NetworkTopology();
		Vertex v1 = new ComputerVertexFactory().createVertex("v1", new String[] { "192.168.88.106" });
		Vertex v2 = new ServerVertexFactory().createVertex("v2", new String[] { "192.168.199.1" });
		Vertex v3 = new RouterVertexFactory().createVertex("v3", new String[] { "192.168.199.52" });
		Vertex v4 = new RouterVertexFactory().createVertex("v4", new String[] { "192.168.199.9" });
		Edge e1 = new NetworkConnectionFactory().createEdge("e1", Arrays.asList(v1, v2), 100);
		Edge e2 = new NetworkConnectionFactory().createEdge("e2", Arrays.asList(v3, v2), 50);
		Edge e3 = new NetworkConnectionFactory().createEdge("e3", Arrays.asList(v1, v3), 10);
		Edge e4 = new NetworkConnectionFactory().createEdge("e4", Arrays.asList(v4, v3), 100);
		gg.addEdge(e1);
		gg.addEdge(e2);
		gg.addEdge(e3);
		gg.addEdge(e4);
		Edge e5 = new NetworkConnectionFactory().createEdge("e5", Arrays.asList(v4, v1), 50);
		Edge e6 = new NetworkConnectionFactory().createEdge("e6", Arrays.asList(v4, v2), 200);
		Vertex v5 = new RouterVertexFactory().createVertex("v5", new String[] { "192.168.199.8" });
		Edge e7 = new NetworkConnectionFactory().createEdge("e7", Arrays.asList(v1, v5), 20);
		Edge e8 = new NetworkConnectionFactory().createEdge("e8", Arrays.asList(v4, v5), 20);
		gg.addEdge(e5);
		gg.addEdge(e6);
		gg.addEdge(e7);
		gg.addEdge(e8);
		assertEquals(8,GraphMetrics.betweennessCentrality(gg, v1),0.01);
	}
	@Test
	public void testdegree() {
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
		assertEquals(1,GraphMetrics.inDegreeCentrality(g, Ren),0.01);
		assertEquals(3,GraphMetrics.inDegreeCentrality(g, An),0.01);
		assertEquals(3,GraphMetrics.outDegreeCentrality(g, Li),0.01);
	}
}
