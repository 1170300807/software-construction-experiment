package graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Graph<L, E> {
	

	
	/**
	 * Add a vertex to this graph.
	 * 
	 * @param v
	 *            label for the new vertex
	 * @return true if this graph did not already include a vertex with the given
	 *         label; otherwise false (and this graph is not modified)
	 */
	public abstract boolean addVertex(L v);

	/**
	 * Remove a vertex from this graph; any edges to or from the vertex are also
	 * removed.
	 * 
	 * @param v
	 *            label of the vertex to remove
	 * @return true if this graph included a vertex with the given label; otherwise
	 *         false (and this graph is not modified)
	 */
	public abstract boolean removeVertex(L v);

	/**
	 * Get all the vertices in this graph.
	 * 
	 * @return the set of labels of vertices in this graph
	 */
	public abstract Set<L> vertices();

	/**
	 * Get the source vertices with directed edges to a target vertex and the
	 * weights of those edges.
	 * 
	 * @param target
	 *            a label
	 * @return a map where the key set is the set of labels of vertices such that
	 *         this graph includes an edge from that vertex to target, and the value
	 *         for each key is the (nonzero) weight of the edge from the key to
	 *         target
	 */
	public abstract Map<L, List<Double>> sources(L target);

	/**
	 * Get the target vertices with directed edges from a source vertex and the
	 * weights of those edges.
	 * 
	 * @param source
	 *            a label
	 * @return a map where the key set is the set of labels of vertices such that
	 *         this graph includes an edge from source to that vertex, and the value
	 *         for each key is the (nonzero) weight of the edge from source to the
	 *         key
	 */
	public abstract Map<L, List<Double>> targets(L source);

	/**
	 * Add an edge to this graph. If the new edge's label has existed,delete the old
	 * one then add the new one; else if their labels is different,but the sources
	 * and targets are equals,the old one's weight will be replaced by the new
	 * edge'weight
	 * 
	 * @param edge
	 *            label for the new edge
	 * @return true if this graph did not already include an edge with the given
	 *         label or an edge with existed source and target; otherwise false (and this graph is not modified)
	 */
	public abstract boolean addEdge(E edge);

	/**
	 * Remove an edge from this graph;
	 * 
	 * @param vertex
	 *            label of the vertex to remove
	 * @return true if this graph included a vertex with the given label; otherwise
	 *         false (and this graph is not modified)
	 */
	public abstract boolean removeEdge(E edge);

	/**
	 * Get all the edges in this graph.
	 * 
	 * @return the set of labels of edges in this graph
	 */
	public abstract Set<E> edges();
}
