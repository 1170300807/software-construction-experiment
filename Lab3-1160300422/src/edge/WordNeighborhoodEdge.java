package edge;

import vertex.Vertex;

public class WordNeighborhoodEdge extends DirectedEdge {

	public WordNeighborhoodEdge(String label, double weight) {
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
		return "<\"" + super.getLabel() + "\", \"WordNeighborhoodEdge\", " + "\"" + super.getWeight() + "\", \""
				+ source.toString() + "\", \"" + target.toString() + "\", Yes>";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		WordNeighborhoodEdge v = (WordNeighborhoodEdge) o;
		return super.getLabel().equals(v.getLabel()) && super.sourceVertices().equals(v.sourceVertices())
				&& super.targetVertices().equals(v.targetVertices()) && super.getWeight() == v.getWeight();
	}

}
