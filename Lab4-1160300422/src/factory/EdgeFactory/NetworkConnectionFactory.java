package factory.EdgeFactory;

import java.util.List;

import edge.NetworkConnection;
import vertex.Vertex;

public class NetworkConnectionFactory extends EdgeFactory {

	

	@Override
	public NetworkConnection createEdge(String label, List<Vertex> vertices, double weight) {
		NetworkConnection g = new NetworkConnection(label,weight);
		g.addVertices(vertices);
		return g;
	}

}
