package factory.VertexFactory;

import vertex.Router;
import vertex.Vertex;

public class RouterVertexFactory extends VertexFactory {

	@Override
	public Router createVertex(String label, String[] args) {
		Router router= new Router(label);
		router.fillVertexInfo(args);
		return router;
	}



}
