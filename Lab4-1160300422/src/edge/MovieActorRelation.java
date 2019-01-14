package edge;

import vertex.Vertex;

public class MovieActorRelation extends UndirectedEdge {
	// Abstraction function:
	// MovieActorRelation is undirected
	// it's made of a Movie and an Actor
	//
	// Representation invariant:
	// weight should be positive
	// it's made of a Movie and an Actor
	//
	// Safety from rep exposure:
	// All fields are private
	public MovieActorRelation(String label, double weight) {
		super(label, weight);
	}
	
	@Override
	public String toString() {
		StringBuffer source = new StringBuffer();
		for(Vertex v : super.vertices()) {
			source.append(v.getLabel());
		}
		
		return "<\"" + super.getLabel() + "\", \"MovieActorRelation\", " + "\"" + super.getWeight() + "\", \""
				+ source.toString() + "\", No>";
	}
}
