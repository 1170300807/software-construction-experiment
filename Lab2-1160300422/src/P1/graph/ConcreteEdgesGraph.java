/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {

	private final Set<L> vertices = new HashSet<L>();
	private final List<Edge<L>> edges = new ArrayList<Edge<L>>();

	// Abstraction function:
	// TODO
	// represent the Set which contains all vertices and the List which contains all
	// edges
	//
	// Representation invariant:
	// TODO
	// vertices contains no repeated vertex
	// edges contains no repeated edge,every edge must be made of the vertices from
	// vertices
	//
	// Safety from rep exposure:
	// TODO
	// All fields are private
	// make defensive copies to avoid sharing the rep
	//
	// TODO constructor
	public ConcreteEdgesGraph() {

	}

	// TODO checkRep
	private void checkRep() {
		HashMap<L, Integer> vertexMap = new HashMap<L, Integer>();
		HashMap<Edge<L>, Integer> edgeMap = new HashMap<Edge<L>, Integer>();
		for (L s : vertices) {
			assert !vertexMap.containsKey(s);
			vertexMap.put(s, 1);
		}
		for (Edge<L> e : edges) {
			assert !edgeMap.containsKey(e);
			edgeMap.put(e, 1);
			assert vertices.contains(e.getSource()) && vertices.contains(e.getTarget());
		}
	}

	@Override
	public boolean add(L vertex) {
		if (vertices.contains(vertex)) {
			checkRep();
			return false;
		} else {
			vertices.add(vertex);
			checkRep();
			return true;
		}
	}

	@Override
	public int set(L source, L target, int weight) {
		Edge<L> e = new Edge<L>(source, target, weight);

		int w = 0;
		Edge<L> old = null;
		if (!(vertices.contains(source) && vertices.contains(target))) {
			if (weight != 0)
				edges.add(e);
			if (!vertices.contains(source)) {
				vertices.add(source);
			}
			if (!vertices.contains(target)) {
				vertices.add(target);
			}

		} else {
			for (Edge<L> now : edges) {
				old = now;
				if (now.hashCode() == e.hashCode()) {
					w = now.getWeight();
					break;
				}
			}
			if (weight == 0) {
				edges.remove(old);
			} else
				edges.add(e);
		}
		checkRep();
		return w;
	}

	@Override
	public boolean remove(L vertex) {
		if (!vertices.contains(vertex))
			return false;
		else {
			vertices.remove(vertex);
			Iterator<Edge<L>> iterSet = edges.iterator();
			while (iterSet.hasNext()) {
				Edge<L> now = iterSet.next();
				if (now.getSource().equals(vertex) || now.getTarget().equals(vertex)) {
					iterSet.remove();
				}
			}
			checkRep();
			return true;
		}
	}

	@Override
	public Set<L> vertices() {
		Set<L> ver = new HashSet<L>();
		for (L now : vertices) {
			ver.add(now);
		}
		checkRep();
		return ver;
	}

	@Override
	public Map<L, Integer> sources(L target) {
		Map<L, Integer> sources = new HashMap<L, Integer>();
		for (Edge<L> e : edges) {
			if (e.getTarget().equals(target)) {
				sources.put((L) e.getSource(), e.getWeight());
			}
		}
		checkRep();
		return sources;
	}

	@Override
	public Map<L, Integer> targets(L source) {
		Map<L, Integer> targets = new HashMap<L, Integer>();
		for (Edge<L> e : edges) {
			if (e.getSource().equals(source)) {
				targets.put(e.getTarget(), e.getWeight());
			}
		}
		checkRep();
		return targets;
	}

	// TODO toString()
	public String toString() {
		StringBuffer str = new StringBuffer("vertices:");
		for (L now : vertices) {
			str.append(now + ",");
		}
		str.deleteCharAt(str.length() - 1);
		str.append("\n" + "edges:");
		for (Edge<L> e : edges) {
			str.append("(" + e.getSource() + "," + e.getTarget() + "," + e.getWeight() + ")" + "\n");
		}
		return str.toString();
	}

}

/**
 * TODO specification Immutable. This class is internal to the rep of
 * ConcreteEdgesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Edge<L> {

	// TODO fields
	private L source;
	private L target;
	private int weight;

	// Abstraction function:
	// TODO
	// represent source,target and weight to describe an edge
	// Representation invariant:
	// TODO
	// the weight must be a positive number
	// Safety from rep exposure:
	// TODO
	// all fields are private
	// provide creators and observers but no mutators in this class
	// TODO constructor
	public Edge(L source, L target, int weight) {
		this.source = source;
		this.target = target;
		if (weight == 0)
			this.weight = weight + 1;
		else
			this.weight = weight;
		checkRep();
	}

	/**
	 * 
	 * @return the graph's source
	 */
	public L getSource() {
		return this.source;
	}

	/**
	 * 
	 * @return the graph's target
	 */
	public L getTarget() {
		return this.target;
	}

	/**
	 * 
	 * @return the graph's weight
	 */
	public int getWeight() {
		return this.weight;
	}

	@Override
	public String toString() {
		return new String("(" + this.source.toString() + "," + this.target.toString() + "," + this.weight + ")");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Edge) {
			Edge<?> edge = (Edge<?>) obj;
			if (this.source.equals(edge.getSource()) && this.target.equals(edge.getTarget())
					&& this.weight == edge.weight) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	@Override
	public int hashCode() {
		return source.toString().concat(",").concat(target.toString()).hashCode();
	}

	// TODO checkRep
	private void checkRep() {
		assert weight > 0;
	}

	// TODO methods

	// TODO toString()

}
