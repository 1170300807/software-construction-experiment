package factory.VertexFactory;

import vertex.Server;
import vertex.Vertex;

public class ServerVertexFactory extends VertexFactory {



	@Override
	public Server createVertex(String label, String[] args) {
		Server server = new Server(label);
		server.fillVertexInfo(args);
		return server;
	}

}
