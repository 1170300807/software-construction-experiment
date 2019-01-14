package factory.EdgeFactory;

import java.util.List;

import edge.Edge;
import edge.WordNeighborhoodEdge;
import vertex.Vertex;

public class WordNeighborhoodEdgeFactory extends EdgeFactory {

	@Override
	public WordNeighborhoodEdge createEdge(String label, List<Vertex> vertices, double weight) {
		WordNeighborhoodEdge wordedge = new WordNeighborhoodEdge(label,weight);
		wordedge.addVertices(vertices);
		return wordedge;
	}



}
