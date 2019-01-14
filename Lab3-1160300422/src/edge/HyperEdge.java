package edge;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public abstract class HyperEdge extends Edge {

	public HyperEdge(String label, double weight) {
		super(label, weight);
	}

	@Override
	public boolean addVertices(List<Vertex> vertices) {
		Collection<Vertex> vv = super.vertices();
		if (vertices.isEmpty())
			return false;
		else {
			for (Vertex v : vertices) {
				vv.add(v);
			}
			super.setVertices(vv);
			return true;
		}
		
	}
	
	@Override
	public boolean removeVertex(Vertex v) {
		Collection<Vertex> vertices = super.vertices();
		vertices.remove(v);
		if(vertices.size()>=2) {
			super.setVertices(vertices);
			return true;
		}
		else {
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
