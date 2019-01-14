package graph;

import java.util.Collection;
import java.util.Iterator;
import edge.Edge;
import vertex.Vertex;

public class GraphPoet extends ConcreteGraph {

	// Abstraction function:
	// vertices is the set of all words in an article
	// when one word next to the other one ,they are an edge,the number of times
	// that the couple has occurred is weight
	//
	// Representation invariant:
	// extends ConcreteGraph
	//
	// Safety from rep exposure:
	// All fields are private

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GraphType = \"GraphPoet\"" + '\n');
		sb.append("GraphName = \"" + label + "\"" + '\n');
		sb.append("VertexType = \"Word\"" + '\n');
		for (Vertex v : vertices) {
			sb.append("Vertex = " + v.toString() + '\n');
		}
		sb.append("EdgeType = \"WordNeighborhood\"");
		for (Edge e : edges) {
			sb.append("Edge = " + e.toString() + '\n');
		}
		return sb.toString();
	}

}
