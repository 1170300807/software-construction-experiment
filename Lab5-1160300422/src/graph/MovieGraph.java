package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edge.DirectedEdge;
import edge.Edge;
import edge.UndirectedEdge;
import vertex.Vertex;

public class MovieGraph extends ConcreteGraph {
	// Abstraction function:
	// a graph is expressed by two sets---vertices and edges
	//
	// Representation invariant:
	// vertices and edges has no repeated elements
	// the vertex mentioned in edges must be included by vertices
	// no loops in the graph
	// MovieActorRelation consist of Movie and Actor,weight > 0
	// MovieDirectorRelation consist of Movie and Director,no weight
	// SameMovieHyperEdge consist of all actor in the same movie
	//
	// Safety from rep exposure:
	// All fields are private
	private void checkRep() {
		for (Edge e : super.edges) {
			List<Vertex> vv = new ArrayList<Vertex>();
			for (Vertex v : e.vertices()) {

				assert !vv.contains(v);
				vv.add(v);
			}
		}
	}

	@Override
	public boolean removeVertex(Vertex v) {
		if (super.vertices.contains(v)) {
			super.vertices.remove(v);
			Iterator<Edge> edgeIter = super.edges.iterator();
			while (edgeIter.hasNext()) {
				Edge now = edgeIter.next();
				if (now.vertices().contains(v)) {
					boolean op = now.removeVertex(v);
					if (!op) {
						edgeIter.remove();
					}
				}
			}
			log.info(v.toString() + " has been removed");
			// checkRep();
			return true;
		}
		checkRep();
		log.info(v.toString() + " has been removed");
		return false;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GraphType = \"MovieGraph\"" + '\n');
		sb.append("GraphName = \"" + label + "\"" + '\n');
		sb.append("VertexType = \"Director\", \"Actor\", \"Movie\"" + '\n');
		for (Vertex v : vertices) {
			sb.append("Vertex = " + v.toString() + '\n');
		}
		sb.append("EdgeType = \"MovieDirectorRelation\", \"MovieActorRelation\", \"SameMovieHyperEdge\"" + '\n');
		for (Edge e : edges) {
			sb.append("Edge = " + e.toString() + '\n');
		}
		return sb.toString();
	}
}
