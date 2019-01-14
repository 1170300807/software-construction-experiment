package edge;

import vertex.Vertex;

public class MovieDirectorRelation extends UndirectedEdge {

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
