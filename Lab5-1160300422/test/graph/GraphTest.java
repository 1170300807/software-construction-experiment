package graph;

import org.junit.Test;

public abstract class GraphTest {
	
	@Test
	public abstract void testAddVertex();

	@Test
	public abstract void testVertices();

	@Test
	public abstract void testSources();

	@Test
	public abstract void testTargets();

	@Test
	public abstract void testAddEdge();

	@Test
	public abstract void testRemoveVertex();

	@Test
	public abstract void testRemoveEdge();

	@Test
	public abstract void testEdges();
}
