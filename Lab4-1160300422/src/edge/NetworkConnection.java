package edge;

import vertex.Vertex;

public class NetworkConnection extends UndirectedEdge {
	// Abstraction function:
	// NetworkConnection is undirected
	//
	// Representation invariant:
    // weight must be a positive number
	//
	// Safety from rep exposure:
	// All fields are private
	public NetworkConnection(String label, double weight) {
		super(label, weight);
	}
	@Override
	public String toString() {
		StringBuffer source = new StringBuffer();
		for(Vertex v : super.vertices()) {
			if(source.length()!=0) {
				source.append(","+v.getLabel());
			}
			else source.append(v.getLabel());
		}
		
		return "<\"" + super.getLabel() + "\", \"NetworkConnection\", " + "\"" + super.getWeight() + "\", \""
				+ source.toString() + "\", No>";
	}
	
}
