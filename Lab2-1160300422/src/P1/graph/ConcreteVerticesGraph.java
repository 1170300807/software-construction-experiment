/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

	private final List<Vertex<L>> vertices = new ArrayList<>();

	// Abstraction function:
	// TODO
	// represent a list which contains all vertices of the graph
	// Representation invariant:
	// TODO
	// vertices contains no repeated vertices
	// Safety from rep exposure:
	// TODO
	// all fields are private
	// make defensive copies to avoid sharing the rep in function vertices
	// TODO constructor
	public ConcreteVerticesGraph() {

	}

	// TODO checkRep
	private void checkRep() {
		HashMap<Vertex<L>, Integer> hashMap = new HashMap<Vertex<L>, Integer>();
		for (Vertex<L> v : vertices) {
			assert !hashMap.containsKey(v);
			hashMap.put(v, 1);
		}
	}

	@Override
	public boolean add(L vertex) {
		Vertex<L> v = new Vertex<L>(vertex);
		if (!vertices.contains(v)) {
			vertices.add(v);
			checkRep();
			return true;
		}
		checkRep();
		return false;
	}

	@Override
	public int set(L source, L target, int weight) {
		int w = 0;
		Vertex<L> s = new Vertex<L>(source);
		Vertex<L> t = new Vertex<L>(target);
		boolean containSource = false;
		boolean containTarget = false;
		Iterator<Vertex<L>> iterList = vertices.iterator();
		while (iterList.hasNext()) {
			Vertex<L> now = iterList.next();
			if (now.getName().equals(source)) {
				s = now;
				containSource = true;
			} else if (now.getName().equals(target)) {
				t = now;
				containTarget = true;
			}
		}
		if (!containSource) {
			vertices.add(s);
		}
		if (!containTarget) {
			vertices.add(t);
		}
		if (s.getEdges().containsKey(t)) {
			w = s.getEdges().get(t).intValue();
		}
		Map<Vertex<L>, Integer> m = s.getEdges();
		if (weight == 0) {
			m.remove(t);
		} else
			m.put(t, weight);
		s.changeEdges(m);
		checkRep();
		return w;
	}

	@Override
	public boolean remove(L vertex) {
		boolean containVertex = false;
		Iterator<Vertex<L>> iterList = vertices.iterator();
		while (iterList.hasNext()) {
			Vertex<L> now = iterList.next();
			if (now.getName().equals(vertex)) {
				iterList.remove();
				containVertex = true;
			} else {
				Map<Vertex<L>, Integer> m = now.getEdges();
				Iterator<Vertex<L>> iterMap = m.keySet().iterator();
				while (iterMap.hasNext()) {
					Vertex<L> n = iterMap.next();
					if (n.getName().equals(vertex)) {
						iterMap.remove();
					}
				}
				now.changeEdges(m);
			}
		}
		checkRep();
		if (containVertex)
			return true;
		else
			return false;
	}

	@Override
	public Set<L> vertices() {
		Set<L> ver = new HashSet<L>();
		for (Vertex<L> now : vertices) {
			ver.add(now.getName());
		}
		checkRep();
		;
		return ver;
	}

	@Override
	public Map<L, Integer> sources(L target) {
		Map<L, Integer> sources = new HashMap<L, Integer>();
		Iterator<Vertex<L>> iterList = vertices.iterator();
		while (iterList.hasNext()) {
			Vertex<L> now = iterList.next();
			Iterator<Vertex<L>> iterMap = now.getEdges().keySet().iterator();
			while (iterMap.hasNext()) {
				Vertex<L> n = iterMap.next();
				if (n.getName().equals(target)) {
					sources.put(now.getName(), now.getEdges().get(n));
					break;
				}
			}
		}
		checkRep();
		;
		return sources;
	}

	@Override
	public Map<L, Integer> targets(L source) {
		Map<L, Integer> targets = new HashMap<L, Integer>();
		Iterator<Vertex<L>> iterList = vertices.iterator();
		while (iterList.hasNext() && (targets.isEmpty())) {
			Vertex<L> now = iterList.next();
			if (now.getName().equals(source)) {
				Iterator<Vertex<L>> iterMap = now.getEdges().keySet().iterator();
				while (iterMap.hasNext()) {
					Vertex<L> n = iterMap.next();
					targets.put(n.getName(), now.getEdges().get(n));
				}
			}
		}
		checkRep();
		;
		return targets;
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		for (Vertex<L> a : vertices) {
			str.append(a.toString());
		}
		return str.toString();
	}
	// TODO getName()

}

/**
 * TODO specification Mutable. This class is internal to the rep of
 * ConcreteVerticesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Vertex<L> {

	// TODO fields
	private L name;
	private Map<Vertex<L>, Integer> Edges = new HashMap<Vertex<L>, Integer>();

	// Abstraction function:
	// TODO
	// represent name saving the vertex's name and a Map<Vertex<L> saving its
	// targets and weight
	// Representation invariant:
	// TODO
	// the value of the edges must be a positive number or zero
	// Safety from rep exposure:
	// TODO
	// all fields are private
	// make defensive copies to avoid sharing the rep in function getEdges
	// TODO constructor
	public Vertex(L str) {
		this.name = str;
		checkRep();
	}

	// TODO checkRep
	private void checkRep() {
		for (Vertex<L> v : Edges.keySet()) {
			assert Edges.get(v) > 0;
		}
	}
	// TODO methods

	/**
	 * 
	 * @return an copy of edges
	 */
	public Map<Vertex<L>, Integer> getEdges() {
		Map<Vertex<L>, Integer> edges = new HashMap<Vertex<L>, Integer>();
		edges.putAll(Edges);
		checkRep();
		return edges;
	}

	/**
	 * use a new map replace the old
	 * 
	 * @param m
	 *            a new map Map<Vertex<L>, Integer> to describe the vertex's targets
	 */
	public void changeEdges(Map<Vertex<L>, Integer> m) {
		Edges.clear();
		Edges.putAll(m);
		checkRep();
	}

	// TODO getName()
	public L getName() {
		return this.name;
	}

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer(this.name + "\n");
		for (Vertex<L> a : Edges.keySet()) {
			str.append("(").append(a.name).append(",").append(Edges.get(a)).append(")\n");
		}
		return str.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vertex) {
			Vertex<?> vertex = (Vertex<?>) obj;
			if (this.name.equals(vertex.name)) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
