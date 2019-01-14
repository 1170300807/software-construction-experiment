package edge;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public abstract class UndirectedEdge extends Edge {
	// Abstraction function:
	// compare to class Edge, DirectedEdge isn't different from it
	//
	// Representation invariant:
	// its sources and targets are equal to vertices
	//
	// Safety from rep exposure:
	// All fields are private
	UndirectedEdge(String label, double weight) {
		super(label, weight);
	}

	@Override
	public boolean addVertices(List<Vertex> vertices) {
		Collection<Vertex> vs = super.vertices();
		if (vertices.isEmpty())
			return false;
		else {
			for (Vertex v : vertices) {
				vs.add(v);
			}
			super.setVertices(vs);
			return true;
		}

	}

	@Override
	public Set<Vertex> sourceVertices() {
		return super.vertices();
	}

	@Override
	public Set<Vertex> targetVertices() {
		return super.vertices();
	}
	@Override
	public boolean removeVertex(Vertex v) {
		// TODO Auto-generated method stub
		return false;
	}
}
