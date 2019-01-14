package factory.EdgeFactory;

import java.util.List;

import edge.CommentTie;
import edge.Edge;
import vertex.Vertex;

public class CommentTieFactory extends EdgeFactory {

	@Override
	public CommentTie createEdge(String label, List<Vertex> vertices, double weight) {
		CommentTie c = new CommentTie(label, weight);
		c.addVertices(vertices);
		return c;
	}

}
