package factory.EdgeFactory;

import java.util.List;

import edge.Edge;
import edge.SameMovieHyperEdge;
import vertex.Vertex;

public class SameMovieHyperEdgeFactory extends EdgeFactory {

	@Override
	public SameMovieHyperEdge createEdge(String label, List<Vertex> vertices, double weight) {
		SameMovieHyperEdge e = new SameMovieHyperEdge(label, weight);
		e.addVertices(vertices);
		return e;
	}

}
