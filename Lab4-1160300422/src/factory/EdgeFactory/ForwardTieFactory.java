package factory.EdgeFactory;

import java.util.List;

import edge.CommentTie;
import edge.Edge;
import edge.ForwardTie;
import vertex.Vertex;

public class ForwardTieFactory extends EdgeFactory {

	@Override
	public ForwardTie createEdge(String label, List<Vertex> vertices, double weight) {
		ForwardTie f = new ForwardTie(label,weight);
		f.addVertices(vertices);
		return f;
	}

}
