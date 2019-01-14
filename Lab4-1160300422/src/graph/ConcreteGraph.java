package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import edge.DirectedEdge;
import edge.Edge;
import edge.UndirectedEdge;
import vertex.Vertex;

public abstract class ConcreteGraph implements Graph<Vertex, Edge> {
	private String label;
	private Collection<Vertex> vertices = new HashSet<Vertex>();
	private Collection<Edge> edges = new HashSet<Edge>();
	Logger log = Logger.getLogger("errorlog");
	// Abstraction function:
	// a graph is expressed by two sets---vertices and edges
	//
	// Representation invariant:
	// vertices and edges has no repeated elements
	// the vertex mentioned in edges must be included by vertices
	//
	// Safety from rep exposure:
	// All fields are private

	/*
	 * mutator
	 */
	public void modifyV(Collection<Vertex> vertices) {
		this.vertices.clear();
		this.vertices.addAll(vertices);
	}

	public void modifyE(Collection<Edge> edges) {
		this.edges.clear();
		this.edges.addAll(edges);
	}

	public void modifyL(String label) {
		String l = new String(label);
		this.label = l;
	}

	private void checkRep() {
		for (Edge e : edges) {
			for (Vertex v : e.vertices()) {
				assert vertices.contains(v);
			}
		}
	}

	@Override
	public boolean addVertex(Vertex v) {
		if (vertices.contains(v))
			return false;
		else {
			int num = 0;
			Iterator<Vertex> iter = vertices.iterator();
			while (iter.hasNext()) {
				Vertex now = iter.next();
				if (now.getLabel().equals(v.getLabel())) {
					num++;
				}
			}
			if (num > 0) {
				v.modifyLabel(v.getLabel() + num);
			}
			vertices.add(v);
			checkRep();
		}
		log.info(v.toString()+" has been added");
		return true;
	}

	@Override
	public boolean removeVertex(Vertex v) {
		if (vertices.contains(v)) {
			vertices.remove(v);
			Iterator<Edge> edgeIter = edges.iterator();
			while (edgeIter.hasNext()) {
				Edge now = edgeIter.next();
				if (now.vertices().contains(v)) {
					edgeIter.remove();
				}
			}
			checkRep();
			log.info(v.toString()+" has been removed");
			return true;
		}
		checkRep();
		
		return false;
	}

	@Override
	public Set<Vertex> vertices() {
		Set<Vertex> set = new HashSet<Vertex>();
		for (Vertex v : vertices) {
			set.add(v);
		}
		checkRep();
		return set;
	}

	@Override
	public Map<Vertex, List<Double>> sources(Vertex target) {
		Map<Vertex, List<Double>> sources = new HashMap<Vertex, List<Double>>();
		for (Edge e : edges) {
			if (e.targetVertices().contains(target)) {
				for (Vertex v : e.sourceVertices()) {
					if (sources.containsKey(v)) {
						List<Double> weights = sources.get(v);
						weights.add(e.getWeight());
						sources.put(v, weights);
					} else {
						List<Double> weights = new ArrayList<Double>();
						weights.add(e.getWeight());
						sources.put(v, weights);
					}
				}
			}
		}
		checkRep();
		return sources;
	}

	@Override
	public Map<Vertex, List<Double>> targets(Vertex source) {
		Map<Vertex, List<Double>> targets = new HashMap<Vertex, List<Double>>();
		for (Edge e : edges) {
			if (e.sourceVertices().contains(source)) {
				for (Vertex v : e.targetVertices()) {
					if (targets.containsKey(v)) {
						List<Double> weights = targets.get(v);
						weights.add(e.getWeight());
						targets.put(v, weights);
					} else {
						List<Double> weights = new ArrayList<Double>();
						weights.add(e.getWeight());
						targets.put(v, weights);
					}
				}
			}
		}
		checkRep();
		return targets;
	}

	@Override
	public boolean addEdge(Edge edge) {
		for (Vertex v : edge.vertices()) {
			addVertex(v);
		}
		int num = 0;
		Iterator<Edge> edgeIter = this.edges.iterator();
		while (edgeIter.hasNext()) {
			Edge e = edgeIter.next();
			String[] str = e.getLabel().split("\\_"); 
			if (e.getLabel().equals(edge.getLabel()) || edge.getLabel().equals(str[0])) {
				num++;
			}			
		}
		if(num != 0) {
			edge.modifyLabel(edge.getLabel()+"_"+num);
		}
		this.edges.add(edge);
		checkRep();
		log.info(edge.toString()+" has been added");
		return true;
	}

	@Override
	public boolean modifyEdge(Edge edge) {
		Iterator<Edge> edgeIter = this.edges.iterator();
		while (edgeIter.hasNext()) {
			Edge e = edgeIter.next();
			if (e.getLabel().equals(edge.getLabel())) {
				edgeIter.remove();
				break;
			}
		}
		this.edges.add(edge);
		log.info(edge.toString()+" has been modified");
		return false;

	}

	@Override
	public boolean removeEdge(Edge edge) {
		boolean tag = false;
		Iterator<Edge> edgeIter = edges.iterator();
		while (edgeIter.hasNext()) {
			Edge now = edgeIter.next();
			if (now.equals(edge)) {
				edgeIter.remove();
				log.info(edge.toString()+" has been removed");
				tag = true;
			}
			if (tag) {
				checkRep();
				log.info(edge.toString()+" has been removed");
				return true;
			}
		}
		checkRep();
		
		return false;
	}

	@Override
	public Set<Edge> edges() {
		Set<Edge> set = new HashSet<Edge>();
		for (Edge v : edges) {
			set.add(v);
		}
		this.checkRep();
		return set;
	}

}
