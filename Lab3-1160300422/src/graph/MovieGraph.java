package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edge.DirectedEdge;
import edge.Edge;
import edge.UndirectedEdge;
import vertex.Vertex;

public class MovieGraph extends ConcreteGraph {

	private void checkRep() {
		Collection<Edge> edges = super.edges();
		for (Edge e : edges) {
			List<Vertex> vv = new ArrayList<Vertex>();
			for (Vertex v : e.vertices()) {
				
				
				assert !vv.contains(v);
				vv.add(v);
			}
		}
	}

	@Override
	public boolean removeVertex(Vertex v) {
		Collection<Vertex> vertices = super.vertices();
		Collection<Edge> edges = super.edges();
		if (vertices.contains(v)) {
			vertices.remove(v);
			Iterator<Edge> edgeIter = edges.iterator();
			while (edgeIter.hasNext()) {
				Edge now = edgeIter.next();
				if (now.vertices().contains(v)) {
					boolean op = now.removeVertex(v);
					if (!op) {
						edgeIter.remove();
					}
				}
			}
			super.modifyV(vertices);
			super.modifyE(edges);
			checkRep();
			return true;
		}
		super.modifyV(vertices);
		super.modifyE(edges);
		checkRep();
		return false;
	}

}
