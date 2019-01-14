package edge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public abstract class DirectedEdge extends Edge {
	private Set<Vertex> sources = new HashSet<Vertex>();
	private Set<Vertex> targets = new HashSet<Vertex>();

	// Abstraction function:
	// directed edge has attributes--sources and targets
	//
	// Representation invariant:
	// directed edge's weight is non-negative
	//
	// Safety from rep exposure:
	// All fields are private
	public DirectedEdge(String label, double weight) {
		super(label, weight);
	}


	@Override
	public boolean addVertices(List<Vertex> vertices) {
		if (vertices.isEmpty())
			return false;
		else {
			super.setVertices(vertices);
			if (vertices.size() == 1) {
				this.sources.add(vertices.get(0));
				this.targets.add(vertices.get(0));
			} else {
				this.sources.add(vertices.get(0));
				this.targets.add(vertices.get(1));
			}
			return true;
		}
	}

	@Override
	public Set<Vertex> sourceVertices() {
		return sources;
	}

	@Override
	public Set<Vertex> targetVertices() {
		return targets;
	}
	@Override
	public boolean removeVertex(Vertex v) {
		return false;
	}
}
