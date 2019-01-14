/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

	/*
	 * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
	 */
	@Override
	public Graph<String> emptyInstance() {
		return new ConcreteVerticesGraph<String>();
	}

	/*
	 * Testing ConcreteVerticesGraph...
	 */

	// Testing strategy for ConcreteVerticesGraph.toString()
	// TODO test a graph without vertices ,a graph without edges,a common graph

	// TODO tests for ConcreteVerticesGraph.toString()
	@Test
	public void testToString() {
		Graph<String> graph = emptyInstance();
		assertEquals("", graph.toString());
		graph.add("a");
		graph.add("b");
		graph.add("c");
		assertEquals("a\n" + "b\n" + "c\n" + "", graph.toString());
		graph.set("a", "b", 10);
		graph.set("b", "a", 10);
		assertEquals("a\n" + "(b,10)\n" + "b\n" + "(a,10)\n" + "c\n", graph.toString());
	}
	/*
	 * Testing Vertex...
	 */

	// Testing strategy for Vertex
	// TODO
	// testgetName():
	// return a String including upcase ,lowcase,single character
	// changeEdges():
	// change the vertex's edges map,it can do nothing ,modify a value ,add a new
	// key and remove an existed key
	// getEdges():
	// return a map from the vertex's targets to the distance between them,it can be
	// an empty map or a common map

	// TODO tests for operations of Vertex
	@Test
	public void testgetName() {
		Vertex<String> v = new Vertex<String>("a");
		assertEquals("a", v.getName());
		Vertex<String> v2 = new Vertex<String>("A");
		assertEquals("A", v2.getName());
		Vertex<String> v3 = new Vertex<String>("Aa");
		assertEquals("Aa", v3.getName());
	}

	@Test
	public void testchangeEdges() {
		Vertex<String> v = new Vertex<String>("a");
		Vertex<String> v2 = new Vertex<String>("b");
		Vertex<String> v3 = new Vertex<String>("c");
		assertEquals(0, v.getEdges().size());
		Map<Vertex<String>, Integer> map = v.getEdges();
		// add
		map.put(v2, 2);
		map.put(v3, 3);
		v.changeEdges(map);
		map = v.getEdges();
		assertEquals(2, map.size());
		assertEquals(2, map.get(v2).intValue());
		assertEquals(3, map.get(v3).intValue());
		// modify
		map.put(v3, 4);
		v.changeEdges(map);
		map = v.getEdges();
		assertEquals(2, map.size());
		assertEquals(2, map.get(v2).intValue());
		assertEquals(4, map.get(v3).intValue());
		// remove
		map.remove(v3);
		v.changeEdges(map);
		map = v.getEdges();
		assertEquals(1, map.size());
		assertEquals(2, map.get(v2).intValue());
		// empty
		map.clear();
		v.changeEdges(map);
		map = v.getEdges();
		assertEquals(0, map.size());
	}

	@Test
	public void testgetEdges() {
		Vertex<String> v = new Vertex<String>("a");
		Vertex<String> v2 = new Vertex<String>("b");
		Vertex<String> v3 = new Vertex<String>("c");
		assertEquals(0, v.getEdges().size());
		Map<Vertex<String>, Integer> map = v.getEdges();
		map.put(v2, 2);
		map.put(v3, 3);
		v.changeEdges(map);
		map = v.getEdges();
		assertEquals(2, map.size());
		assertEquals(2, map.get(v2).intValue());
		assertEquals(3, map.get(v3).intValue());
	}

}
