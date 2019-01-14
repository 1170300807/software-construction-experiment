package factory.EdgeFactory;

import java.util.List;

import edge.Edge;
import edge.ForwardTie;
import edge.FriendTie;
import vertex.Vertex;

public class FriendTieFactory extends EdgeFactory {

	@Override
	public FriendTie createEdge(String label, List<Vertex> vertices, double weight) {
		FriendTie f = new FriendTie(label,weight);
		f.addVertices(vertices);
		return f;
	}
}
