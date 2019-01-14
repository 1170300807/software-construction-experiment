package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edge.DirectedEdge;
import edge.Edge;
import edge.UndirectedEdge;
import vertex.Computer;
import vertex.Server;
import vertex.Vertex;

public class NetworkTopology extends ConcreteGraph {

	// Abstraction function:
	// a graph is expressed by two sets---vertices and edges
	//
	// Representation invariant:
	// vertices and edges has no repeated elements
	// the vertex mentioned in edges must be included by vertices
	// no loops in the graph
	// Computer can't be next to Computer
	// Server can't be next to Server
	//
	// Safety from rep exposure:
	// All fields are private

	private void checkRep() {
		Collection<Vertex> vertices = super.vertices();
		Collection<Edge> edges = super.edges();
		

		for (Edge e : edges) {
			List<Vertex> vv = new ArrayList<Vertex>();
			for (Vertex v : e.vertices()) {
				assert vertices.contains(v);
				vv.add(v);
			}
			Vertex v1 = vv.get(0);
			Vertex v2 = vv.get(1);
			
			
			assert !v1.getLabel().equals(v2.getLabel());
			if (v1.getClass() == v2.getClass()) {
				assert !(v1 instanceof Computer);
				assert !(v1 instanceof Server);
			}
		}
	}

	@Override
	public boolean addEdge(Edge edge) {
		Collection<Edge> edges = super.edges();
		for (Vertex v : edge.vertices()) {
			addVertex(v);
		}
		Iterator<Edge> edgeIter = edges.iterator();
		while (edgeIter.hasNext()) {
			Edge e = edgeIter.next();
			if (e.getClass() == edge.getClass()) {
				if (e.getLabel().equals(edge.getLabel())) {
					edgeIter.remove();
					edges.add(edge);
					super.modifyE(edges);
					checkRep();
					log.info(edge.toString()+" has been added");
					return false;

				}
				if (e.vertices().equals(edge.vertices())) {
					if (e.getWeight() != edge.getWeight()) {
						edgeIter.remove();
						edges.add(edge);
						super.modifyE(edges);
						checkRep();
						log.info(edge.toString()+" has been added");
						return false;
					}
				}

			}
		}
		edges.add(edge);
		super.modifyE(edges);
		log.info(edge.toString()+" has been added");
		checkRep();
		return true;
	}

	@Override
	public Map<Vertex, List<Double>> sources(Vertex target) {
		Collection<Edge> edges = super.edges();
		Map<Vertex, List<Double>> sources = new HashMap<Vertex, List<Double>>();
		for (Edge e : edges) {
			if (e.vertices().contains(target)) {
				List<Double> weights = new ArrayList<Double>();
				weights.add(e.getWeight());
				Vertex source = null;
				for (Vertex v : e.vertices()) {
					if (!v.equals(target))
						source = v;
				}
				sources.put(source, weights);
			}
		}
		checkRep();
		return sources;
	}

	@Override
	public Map<Vertex, List<Double>> targets(Vertex source) {
		Map<Vertex, List<Double>> targets = new HashMap<Vertex, List<Double>>();
		Collection<Edge> edges = super.edges();
		for (Edge e : edges) {
			if (e.vertices().contains(source)) {
				List<Double> weights = new ArrayList<Double>();
				weights.add(e.getWeight());
				Vertex target = null;
				for (Vertex v : e.vertices()) {
					if (!v.equals(source))
						target = v;
				}
				targets.put(target, weights);
			}
		}
		checkRep();
		return targets;
	}

}
