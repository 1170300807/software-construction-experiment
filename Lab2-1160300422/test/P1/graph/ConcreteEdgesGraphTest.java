/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

	/*
	 * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
	 */
	@Override
	public Graph<String> emptyInstance() {
		return new ConcreteEdgesGraph<String>();
	}

	/*
	 * Testing ConcreteEdgesGraph...
	 */

	// Testing strategy for ConcreteEdgesGraph.toString()
	// TODO test a graph without vertices ,a graph without edges,a common graph

	// TODO tests for ConcreteEdgesGraph.toString()
	@Test
	public void testToString() {
		Graph<String> graph = emptyInstance();
		assertEquals("vertices\n" + "edges:", graph.toString());
		graph.add("a");
		graph.add("b");
		graph.add("c");
		assertEquals("vertices:a,b,c\n" + "edges:", graph.toString());
		graph.set("a", "b", 10);
		graph.set("b", "a", 10);
		assertEquals("vertices:a,b,c\n" + "edges:(a,b,10)\n" + "(b,a,10)\n", graph.toString());
	}
	/*
	 * Testing Edge...
	 */

	// Testing strategy for Edge
	// TODO
	// getSource(),getTarget():
	// return a String including upcase ,lowcase,single character
	// getWeight():
	// return an integer more than zero

	// TODO tests for operations of Edge

	@Test
	public void testgetSource() {
		Edge<String> edge = new Edge<String>("a", "b", 1);
		assertEquals("a", edge.getSource());
		Edge<String> edge1 = new Edge<String>("A", "B", 1);
		assertEquals("A", edge1.getSource());
		Edge<String> edge2 = new Edge<String>("Aa", "Bb", 1);
		assertEquals("Aa", edge2.getSource());
	}

	@Test
	public void testgetTarget() {
		Edge<String> edge = new Edge<String>("a", "b", 1);
		assertEquals("b", edge.getTarget());
		Edge<String> edge1 = new Edge<String>("A", "B", 1);
		assertEquals("B", edge1.getTarget());
		Edge<String> edge2 = new Edge<String>("Aa", "Bb", 1);
		assertEquals("Bb", edge2.getTarget());
	}

	@Test
	public void testgetWeight() {
		Edge<String> edge = new Edge<String>("a", "b", 1);
		assertEquals(1, edge.getWeight());
	}
	@Test
	public void testoString() {
		Edge<String> edge = new Edge<String>("a", "b", 1);		
		Edge<String> edge1 = new Edge<String>("A", "B", 1);
		Edge<String> edge2 = new Edge<String>("Aa", "Bb", 1);
		assertEquals("(a,b,1)",edge.toString());
		assertEquals("(A,B,1)",edge1.toString());
		assertEquals("(Aa,Bb,1)",edge2.toString());
	}
}
