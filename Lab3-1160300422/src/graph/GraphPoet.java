package graph;

import java.util.Collection;
import java.util.Iterator;
import edge.Edge;
import vertex.Vertex;

public class GraphPoet extends ConcreteGraph {
	
	// Abstraction function:
	// vertices is the set of all words in an article
	// when one word next to the other one ,they are an edge,the number of times that the couple has occurred is weight
	//
	// Representation invariant:
	// extends ConcreteGraph
	//
	// Safety from rep exposure:
	// All fields are private

	/**
	 * Add an wordedge to this graph.
	 * 
	 * @param edge
	 *            label for the new edge
	 * @return true if this graph did not already include an edge with the given
	 *         label; otherwise false, but its weight will increase by 1
	 */
	@Override
	public boolean addEdge(Edge edge) {
		for (Vertex v : edge.vertices()) {
			addVertex(v);
		}
		Collection<Edge> edges = super.edges();
		Iterator<Edge> edgeIter = edges.iterator();
		while (edgeIter.hasNext()) {
			Edge e = edgeIter.next();
			if (e.getLabel().equals(edge.getLabel())) {
				edgeIter.remove();
				edges.add(edge);
				super.modifyE(edges);
				return false;
			} 
			
			else if (e.sourceVertices().equals(edge.sourceVertices())
					&& e.targetVertices().equals(edge.targetVertices())) {
				double weight = e.getWeight() + 1;
				e.modifyW(weight);
				return false;
			}
		}
		edges.add(edge);
		super.modifyE(edges);
		return true;
	}

}
