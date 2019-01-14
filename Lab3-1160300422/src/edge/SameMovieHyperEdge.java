package edge;

import vertex.Movie;
import vertex.Vertex;

public class SameMovieHyperEdge extends HyperEdge {
	
	
	public SameMovieHyperEdge(String label, double weight) {
		super(label, -1);
	}
	
	@Override
	public String toString() {
		StringBuffer source = new StringBuffer();
		for(Vertex v : super.sourceVertices()) {
			if(source.length()!=0) {
				source.append(","+v.getLabel());
			}
			else source.append(v.getLabel());
		}
		
		return "<\"" + super.getLabel() + "\", \"SameMovieHyperEdge\", " 
				+ source.toString() +">";
	}
}
