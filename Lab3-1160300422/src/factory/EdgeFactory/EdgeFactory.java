package factory.EdgeFactory;

import java.util.List;

import edge.Edge;
import vertex.Vertex;

public abstract class EdgeFactory {
	public abstract Edge createEdge(String label, List<Vertex> vertices, double weight);
}
