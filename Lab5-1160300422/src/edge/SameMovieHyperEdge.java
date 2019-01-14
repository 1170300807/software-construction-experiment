package edge;

import java.util.ArrayList;
import java.util.List;

import vertex.Movie;
import vertex.Vertex;

public class SameMovieHyperEdge extends HyperEdge {
	// Abstraction function:
	// SameMovieHyperEdge is hyper edge
	// it consist of all actors from a movie
	//
	// Representation invariant:
	// the number of vertices must be over two
	//
	// Safety from rep exposure:
	// All fields are private

	public SameMovieHyperEdge(String label, double weight) {
		super(label, -1);
	}

	@Override
	public String toString() {
		StringBuffer source = new StringBuffer();
		for (Vertex v : super.sourceVertices()) {
			if (source.length() != 0) {
				source.append("," + " \"" + v.getLabel() + "\"");
			} else
				source.append("\"" + v.getLabel() + "\"");
		}

		return "<\"" + super.getLabel() + "\", \"SameMovieHyperEdge\", " + "{" + source.toString() + "}" + ">";
	}

	@Override
	public String getSE() {
		StringBuffer sande = new StringBuffer();
		List<String> ls = new ArrayList<String>();
		for (Vertex v : vertices) {
			ls.add(v.getLabel());
		}
		ls.sort(null);
		for (String s : ls) {
			sande.append(s);
		}
		return sande.toString();
	}
}
