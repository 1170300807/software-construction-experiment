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
	public boolean addVertices(List<Vertex> vertice) {
		if (vertice.isEmpty())
			return false;
		else {
			for (Vertex v : vertice) {
				vertices.add(v);
			}
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

	@Override
	public String getSE() {
		String sande = null;
		String[] se = new String[2];
		int i = 0;
		for (Vertex v : vertices) {
			se[i++] = v.getLabel();
		}
		if (se[0] != null && se[1] != null) {
			if (se[0].compareTo(se[1]) < 0) {
				sande = se[0] + se[1];
			} else {
				sande = se[1] + se[0];
			}
		}
		return sande;
	}
}
