package graph;

import java.util.Collection;
import java.util.Iterator;

import edge.Edge;
import vertex.Vertex;

public class SocialNetwork extends ConcreteGraph {
	// Abstraction function:
	// two people has three kinds of Edge,FriendTie,ForwardTie,CommentTie
	//
	// Representation invariant:
	// the sum of all weight is 1
	// no loop
	//
	// Safety from rep exposure:
	// All fields are private

	private void checkRep() {
		double sum = 0;
		for (Edge e : super.edges()) {
			assert !e.sourceVertices().equals(e.targetVertices());
			sum += e.getWeight();
		}
		assert (sum - 1)<0.001 || (1-sum)<0.001;
	}

	/*
	 * modify weight
	 */
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
					removeEdge(now);
				}
			}
			
			checkRep();
			
			super.modifyV(vertices);
			log.info(v.toString()+" has been removed");
			return true;
		}
		checkRep();
		
		super.modifyV(vertices);
		log.info(v.toString()+" has been removed");
		return false;
	}

	@Override
	public boolean addEdge(Edge edge) {

		for (Vertex v : edge.vertices()) {
			addVertex(v);
		}
		Collection<Edge> edges = super.edges();
		
		if (super.edges().isEmpty()) {
			edge.modifyW(1);
			edges.add(edge);
			super.modifyE(edges);
			log.info(edge.toString()+" has been added");
			return true;
		}

		Iterator<Edge> edgeIter = edges.iterator();
		while (edgeIter.hasNext()) {
			Edge e = edgeIter.next();
			if (e.getLabel().equals(edge.getLabel())) {
				double wa = e.getWeight();
				double wb = edge.getWeight();
				edgeIter.remove();

				for (Edge now : edges) {
					double wc = now.getWeight();
					wc = wc * (1 - wb) / (1 - wa);
					now.modifyW(wc);
				}
				edges.add(edge);
				super.modifyE(edges);
				checkRep();
				log.info(edge.toString()+" has been added");
				return false;
			}
			if (e.getClass()==edge.getClass()) {
				if (e.sourceVertices().equals(edge.sourceVertices())
						&& e.targetVertices().equals(edge.targetVertices())) {
					double wa = e.getWeight();
					double wb = edge.getWeight();
					edges.remove(e);
					e.modifyW(wb);
					for (Edge now : edges) {
						double wc = now.getWeight();
						wc = wc * (1 - wb) / (1 - wa);
						now.modifyW(wc);
					}
					edges.add(e);
					super.modifyE(edges);
					checkRep();
					log.info(edge.toString()+" has been added");
					return false;
				}
			} 
		}

		double wa = edge.getWeight();
		for (Edge e : edges) {
			double wb = e.getWeight();
			wb = wb * (1 - wa);
			e.modifyW(wb);
		}
		edges.add(edge);
		super.modifyE(edges);
		checkRep();
		log.info(edge.toString()+" has been added");
		return false;
	}

	@Override
	public boolean removeEdge(Edge edge) {
		Collection<Edge> edges = super.edges();
		Iterator<Edge> edgeIter = edges.iterator();
		double wa = edge.getWeight();
		while (edgeIter.hasNext()) {
			Edge now = edgeIter.next();
			if (now.equals(edge)) {
				edgeIter.remove();
				for (Edge e : edges) {
					double wb = e.getWeight();
					wb = wb / (1 - wa);
					e.modifyW(wb);
					super.modifyE(edges);
				}
				super.removeEdge(edge);
				checkRep();
				log.info(edge.toString()+" has been removed");
				return true;
			}

		}
		checkRep();
		
		return false;
	}

}
