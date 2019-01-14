package graph;

import factory.EdgeFactory.NetworkConnectionFactory;
import factory.GraphFactory.NetworkTopologyFactory;
import factory.VertexFactory.ComputerVertexFactory;
import factory.VertexFactory.RouterVertexFactory;
import factory.VertexFactory.ServerVertexFactory;
import helper.GraphMetrics;
import vertex.Vertex;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import edge.Edge;

public class NetworkTopologyTest extends GraphTest {

	@Override
	public void testAddVertex() {
		NetworkTopology g = new NetworkTopology();
		Vertex v1 = new ComputerVertexFactory().createVertex("v1", new String[] { "192.168.88.106" });
		Vertex v2 = new ServerVertexFactory().createVertex("v2", new String[] { "192.168.199.1" });
		Vertex v3 = new RouterVertexFactory().createVertex("v3", new String[] { "192.168.199.52" });
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		assertEquals(false, g.addVertex(v3));
		assertEquals(3, g.vertices().size());
		assertTrue(g.vertices().contains(v1));
		assertTrue(g.vertices().contains(v2));
		assertTrue(g.vertices().contains(v3));
		Vertex v4 = new RouterVertexFactory().createVertex("v4", new String[] { "192.168.199.2" });
		g.addVertex(v4);
		g.addVertex(v4);
		g.addVertex(v4);
		assertEquals(4, g.vertices().size());
	}

	@Override
	public void testVertices() {
		NetworkTopology g = new NetworkTopology();
		Vertex v1 = new ComputerVertexFactory().createVertex("v1", new String[] { "192.168.88.106" });
		Vertex v2 = new ServerVertexFactory().createVertex("v2", new String[] { "192.168.199.1" });
		Vertex v3 = new RouterVertexFactory().createVertex("v3", new String[] { "192.168.199.52" });
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		assertEquals(3, g.vertices().size());
		assertTrue(g.vertices().contains(v1));
		assertTrue(g.vertices().contains(v2));
		assertTrue(g.vertices().contains(v3));
	}

	@Override
	public void testSources() {
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
		assertEquals(100, g.sources(v3).get(v4).get(0), 0.01);
		assertEquals(10, g.sources(v3).get(v1).get(0), 0.01);
		assertEquals(50, g.sources(v2).get(v3).get(0), 0.01);
		assertEquals(50, g.sources(v3).get(v2).get(0), 0.01);

	}

	@Override
	public void testTargets() {
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
		assertEquals(100, g.targets(v3).get(v4).get(0), 0.01);
		assertEquals(10, g.targets(v3).get(v1).get(0), 0.01);
		assertEquals(50, g.targets(v2).get(v3).get(0), 0.01);
		assertEquals(50, g.targets(v3).get(v2).get(0), 0.01);
	}

	@Override
	public void testAddEdge() {
		NetworkTopology g = new NetworkTopology();
		Vertex v1 = new ComputerVertexFactory().createVertex("v1", new String[] { "192.168.88.106" });
		Vertex v2 = new ServerVertexFactory().createVertex("v2", new String[] { "192.168.199.1" });
		Vertex v3 = new RouterVertexFactory().createVertex("v3", new String[] { "192.168.199.52" });
		Edge e1 = new NetworkConnectionFactory().createEdge("e1", Arrays.asList(v1, v2), 100);
		Edge e2 = new NetworkConnectionFactory().createEdge("e2", Arrays.asList(v3, v2), 50);
		Edge e3 = new NetworkConnectionFactory().createEdge("e3", Arrays.asList(v1, v3), 10);
		g.addEdge(e1);
		g.addEdge(e2);
		g.addEdge(e3);
		assertEquals(3, g.edges().size());
		assertTrue(g.edges().contains(e1));
		assertTrue(g.edges().contains(e2));
		assertTrue(g.edges().contains(e3));

	}

	@Override
	public void testRemoveVertex() {
		NetworkTopology g = new NetworkTopology();
		Vertex v1 = new ComputerVertexFactory().createVertex("v1", new String[] { "192.168.88.106" });
		Vertex v2 = new ServerVertexFactory().createVertex("v2", new String[] { "192.168.199.1" });
		Vertex v3 = new RouterVertexFactory().createVertex("v3", new String[] { "192.168.199.52" });
		Vertex v4 = new RouterVertexFactory().createVertex("v4", new String[] { "192.168.199.9" });
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);
		g.removeVertex(v1);
		g.removeVertex(v2);
		g.removeVertex(v3);
		g.removeVertex(v4);
		assertTrue(g.vertices().isEmpty());
	}

	@Override
	public void testRemoveEdge() {
		NetworkTopology g = new NetworkTopology();
		Vertex v1 = new ComputerVertexFactory().createVertex("v1", new String[] { "192.168.88.106" });
		Vertex v2 = new ServerVertexFactory().createVertex("v2", new String[] { "192.168.199.1" });
		Vertex v3 = new RouterVertexFactory().createVertex("v3", new String[] { "192.168.199.52" });
		Edge e1 = new NetworkConnectionFactory().createEdge("e1", Arrays.asList(v1, v2), 100);
		Edge e2 = new NetworkConnectionFactory().createEdge("e2", Arrays.asList(v3, v2), 50);
		Edge e3 = new NetworkConnectionFactory().createEdge("e3", Arrays.asList(v1, v3), 10);
		g.addEdge(e1);
		g.addEdge(e2);
		g.addEdge(e3);
		g.removeEdge(e1);
		g.removeEdge(e2);
		g.removeEdge(e3);
		assertTrue(g.edges().isEmpty());
	}

	@Override
	public void testEdges() {
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
		assertEquals(4, g.edges().size());
		assertTrue(g.edges().contains(e1));
		assertTrue(g.edges().contains(e2));
		assertTrue(g.edges().contains(e3));
		assertTrue(g.edges().contains(e4));

	}

	@Test
	public void testNetworkTopologyFactory() {
		NetworkTopology g = new NetworkTopologyFactory()
				.createGraph("src/factory/GraphFactory/NetworkTopologyTest.txt");
		assertEquals(4, g.edges().size());
		NetworkTopology G = new NetworkTopologyFactory().createGraph("/home/theking/test123.txt");
		assertEquals(5, G.vertices().size());
		assertEquals(5, G.edges().size());
	}
}
