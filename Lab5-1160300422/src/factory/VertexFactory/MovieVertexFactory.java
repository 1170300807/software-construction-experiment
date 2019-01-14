package factory.VertexFactory;

import vertex.Movie;
import vertex.Vertex;

public class MovieVertexFactory extends VertexFactory {

	@Override
	public Movie createVertex(String label, String[] args) {
		Movie movie = new Movie(label);
		movie.fillVertexInfo(args);
		return movie;
	}

}
