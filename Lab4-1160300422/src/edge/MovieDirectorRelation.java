package edge;

import vertex.Vertex;

public class MovieDirectorRelation extends UndirectedEdge {
	// Abstraction function:
	// MovieDirectorRelation is undirected
	// it's made of a Movie and an Director
	//
	// Representation invariant:
	// weight is -1
	// it's made of a Movie and an Director
	//
	// Safety from rep exposure:
	// All fields are private
	public MovieDirectorRelation(String label, double weight) {
		super(label, -1);
	}
	@Override
	public String toString() {
		StringBuffer source = new StringBuffer();
		for(Vertex v : super.vertices()) {
			source.append(v.getLabel());
		}
		
		return "<\"" + super.getLabel() + "\", \"MovieDirectorRelation\", " + "\"" + super.getWeight() + "\", \""
				+ source.toString() + "\", No>";
	}
}
