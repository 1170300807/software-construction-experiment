package edge;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public abstract class Edge {
	private Collection<Vertex> vertices = new HashSet<Vertex>();
	private String label;
	private double weight;

	// Abstraction function:
	// an edge can be described by its label ,vertices and weight
	//
	// Representation invariant:
	// if it has weight ,the fields weight is a nonnegative number
	// else this.weight == -1
	//
	// Safety from rep exposure:
	// All fields are private
	
	/**
	 * constructor
	 * 
	 * @param label
	 * @param weight
	 */
	Edge(String label, double weight) {
		this.label = label;
		this.weight = weight;
	}

	/**
	 * observer
	 * 
	 * @return
	 */

	public String getLabel() {
		return this.label;
	}
	
	public double getWeight() {
		return this.weight;
	}

	/**
	 * mutator
	 * @param vertices
	 * @return
	 */
	public void modifyW(double weight) {
		this.weight = weight;
	}
	
	public void setVertices(Collection<Vertex> vertice){
		this.vertices.clear();
		this.vertices.addAll(vertice);
	}

	/**
	 * add all vertices of the edge. if it's a hyperedge, vertices.size >= 2 if it's
	 * a loop ,vertices.size = 1 else vertices.size = 2,the first element is source
	 * ,the second is target
	 * 
	 * @param vertices
	 *            collection consist of all vertices of the edge
	 * @return return true when addVertices successfully,otherwise return false
	 */
	public abstract boolean addVertices(List<Vertex> vertices);

	/**
	 * make sure whether the edge contains the vertex v
	 * 
	 * @param v
	 * @return return true if the edge contains the vertex v else return false
	 */
	public boolean containVertex(Vertex v) {
		return vertices.contains(v);
	}

	/**
	 * 
	 * @return a set containing all vertices
	 */
	public Set<Vertex> vertices() {
		Set<Vertex> set = new HashSet<Vertex>();
		for (Vertex v : vertices) {
			set.add(v);
		}
		return set;
	}

	/**
	 * return the sources of the edge
	 * 
	 * @return
	 */
	public abstract Set<Vertex> sourceVertices();

	/**
	 * return the targets of the edge
	 * 
	 * @return
	 */
	public abstract Set<Vertex> targetVertices();
	
	public abstract boolean removeVertex(Vertex v) ;

	@Override
	public String toString() {
		return this.label;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		Edge v = (Edge) o;
		return this.label == v.getLabel() && this.weight == v.getWeight() && this.vertices().equals(v.vertices());
	}
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	
}
