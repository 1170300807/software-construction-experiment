package edge;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public abstract class HyperEdge extends Edge {
	// Abstraction function:
	// hyper edge is made of two or more vertices
	//
	// Representation invariant:
	// the number of vertices should be over two
	//
	// Safety from rep exposure:
	// All fields are private
	public HyperEdge(String label, double weight) {
		super(label, weight);
	}

	@Override
	public boolean addVertices(List<Vertex> vertices) {
		if (vertices.isEmpty())
			return false;
		else {
			for (Vertex v : vertices) {
				super.vertices.add(v);
			}
			return true;
		}

	}

	@Override
	public boolean removeVertex(Vertex v) {
		vertices.remove(v);
		if (vertices.size() >= 2) {
			return true;
		} else {
			return false;
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

}
