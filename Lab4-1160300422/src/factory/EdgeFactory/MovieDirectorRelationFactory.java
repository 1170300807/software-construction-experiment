package factory.EdgeFactory;

import java.util.List;

import edge.Edge;
import edge.MovieDirectorRelation;
import vertex.Vertex;

public class MovieDirectorRelationFactory extends EdgeFactory {

	@Override
	public MovieDirectorRelation createEdge(String label, List<Vertex> vertices, double weight) {
		MovieDirectorRelation e = new MovieDirectorRelation(label,weight);
		e.addVertices(vertices);
		return e;
	}

}
