package edge;

import vertex.Vertex;

public class MovieActorRelation extends UndirectedEdge {

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
