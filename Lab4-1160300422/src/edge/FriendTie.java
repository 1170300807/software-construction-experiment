package edge;

import vertex.Vertex;

public class FriendTie extends DirectedEdge {
	// Abstraction function:
	// a FriendTie is directed
	// so class FriendTie extends Class DirectedEdge	
	//
	// Representation invariant:
	// two words should has only one CommentTie
	// no loops
	//
	// Safety from rep exposure:
	// All fields are private
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
