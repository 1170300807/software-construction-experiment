package factory.VertexFactory;

import vertex.Actor;
import vertex.Vertex;

public class ActorVertexFactory extends VertexFactory {

	@Override
	public Actor createVertex(String label, String[] args) {
		Actor actor = new Actor(label);
		actor.fillVertexInfo(args);
		return actor;
	}

}
