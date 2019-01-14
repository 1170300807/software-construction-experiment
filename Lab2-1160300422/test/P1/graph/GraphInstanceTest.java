/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST NOT add constructors, fields, or non-@Test methods
 * to this class, or change the spec of {@link #emptyInstance()}. Your tests
 * MUST only obtain Graph instances by calling emptyInstance(). Your tests MUST
 * NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {

	// Testing strategy
	// TODO test a graph without vertices ,a graph without edges and a common graph
	// Add():
	// return true,false
	// Set():
	// return 0,n
	// Remove():
	// return true,false
	// Vertices():
	// return an empty map or a common map
	// Sources():
	// return an empty map or a common map
	// Targets():
	// return an empty map or a common map
	//

	/**
	 * Overridden by implementation-specific test classes.
	 * 
	 * @return a new empty graph of the particular implementation being tested
	 */
	public abstract Graph<String> emptyInstance();

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testInitialVerticesEmpty() {
		// TODO Auto-generated method stub
		assertEquals("expected new graph to have no vertices", Collections.emptySet(), emptyInstance().vertices());
	}

	// TODO other tests for instance methods of Graph
	@Test
	public void testAdd() {
		Graph<String> graph = emptyInstance();
		boolean A = graph.add("a");
		boolean B = graph.add("b");
		boolean AA = graph.add("a");
		assertEquals(A, true);
		assertEquals(B, true);
		assertEquals(AA, false);
		assertEquals(2, graph.vertices().size());
		assertTrue(graph.vertices().contains("a"));
		assertTrue(graph.vertices().contains("b"));
		assertFalse(graph.vertices().contains("c"));
	}

	@Test
	public void testSet() {
		Graph<String> graph = emptyInstance();
		graph.add("a");
		graph.add("b");
		int A = graph.set("a", "b", 10);
		int B = graph.set("a", "b", 11);
		int C = graph.set("a", "c", 5);
		int D = graph.set("b", "c", 2);
		int E = graph.set("b", "c", 0);
		int F = graph.set("d", "a", 10);
		assertEquals(0, A);
		assertEquals(10, B);
		assertEquals(0, C);
		assertEquals(0, D);
		assertEquals(2, E);
		assertEquals(0, F);
		assertTrue(graph.targets("b").isEmpty());
	}

	@Test
	public void testRemove() {
		Graph<String> graph = emptyInstance();
		graph.add("a");
		graph.add("b");
		graph.add("c");
		graph.set("a", "b", 10);
		graph.set("b", "a", 10);
		boolean A = graph.remove("a");
		boolean B = graph.remove("b");
		boolean C = graph.remove("c");
		boolean D = graph.remove("d");
		assertEquals(true, A);
		assertFalse(graph.vertices().contains("a"));
		assertFalse(graph.targets("b").containsKey("a"));
		assertEquals(true, B);
		assertEquals(true, C);
		assertEquals(false, D);
	}

	@Test
	public void testVertices() {
		Graph<String> graph = emptyInstance();
		graph.add("a");
		graph.add("b");
		graph.add("c");
		graph.remove("b");
		assertEquals(2, graph.vertices().size());
		assertTrue(graph.vertices().contains("a"));
		assertFalse(graph.vertices().contains("b"));
		assertTrue(graph.vertices().contains("c"));
	}

	@Test
	public void testSources() {
		Graph<String> graph = emptyInstance();
		graph.add("a");
		graph.add("b");
		graph.add("c");
		graph.set("a", "b", 5);
		graph.set("b", "a", 5);
		graph.set("c", "b", 15);
		assertEquals(1, graph.sources("a").size());
		assertEquals(5, graph.sources("a").get("b").intValue());
		assertEquals(2, graph.sources("b").size());
		assertEquals(5, graph.sources("b").get("a").intValue());
		assertEquals(15, graph.sources("b").get("c").intValue());
		assertEquals(0, graph.sources("c").size());
	}

	@Test
	public void testTargets() {
		Graph<String> graph = emptyInstance();
		graph.add("a");
		graph.add("b");
		graph.add("c");
		graph.set("a", "b", 5);
		graph.set("b", "a", 5);
		graph.set("a", "c", 15);
		assertEquals(2, graph.targets("a").size());
		assertEquals(5, graph.targets("a").get("b").intValue());
		assertEquals(15, graph.targets("a").get("c").intValue());
		assertEquals(1, graph.targets("b").size());
		assertEquals(5, graph.targets("b").get("a").intValue());
		assertEquals(0, graph.targets("c").size());
	}

}
