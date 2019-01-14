package edge;

import vertex.Vertex;

public class CommentTie extends DirectedEdge {

	public CommentTie(String label, double weight) {
		super(label, weight);
	}

	@Override
	public String toString() {
		StringBuffer source = new StringBuffer();
		for(Vertex v : super.sourceVertices()) {
			source.append(v.getLabel());
		}
		StringBuffer target = new StringBuffer();
		for(Vertex v : super.targetVertices()) {
			target.append(v.getLabel());
		}
		return "<\"" + super.getLabel() + "\", \"CommentTie\", " + "\"" + super.getWeight() + "\", \""
				+ source.toString() + "\", \"" + target.toString() + "\", Yes>";
	}
}
