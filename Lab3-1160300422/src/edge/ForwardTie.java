package edge;

import vertex.Vertex;

public class ForwardTie extends DirectedEdge {

	public ForwardTie(String label, double weight) {
		super(label, weight);
		// TODO Auto-generated constructor stub
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
		return "<\"" + super.getLabel() + "\", \"ForwardTie\", " + "\"" + super.getWeight() + "\", \""
				+ source.toString() + "\", \"" + target.toString() + "\", Yes>";
	}

	
}
