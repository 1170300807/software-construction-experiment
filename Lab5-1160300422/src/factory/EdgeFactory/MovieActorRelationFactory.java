package factory.EdgeFactory;

import java.util.List;

import edge.MovieActorRelation;
import vertex.Vertex;

public class MovieActorRelationFactory extends EdgeFactory {

	@Override
	public MovieActorRelation createEdge(String label, List<Vertex> vertices, double weight) {
		MovieActorRelation e = new MovieActorRelation(label, weight);
		e.addVertices(vertices);
		return e;
	}

}
