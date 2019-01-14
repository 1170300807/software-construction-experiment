package edge;

import vertex.Vertex;

public class FriendTie extends DirectedEdge {

	public FriendTie(String label, double weight) {
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
		return "<\"" + super.getLabel() + "\", \"FriendTie\", " + "\"" + super.getWeight() + "\", \""
				+ source.toString() + "\", \"" + target.toString() + "\", Yes>";
	}

}
