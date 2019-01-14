
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import graph.Graph;

/**
 * FriendshipGraph provides methods that how to construct the friendshipgraph.
 * 
 */
public class FriendshipGraph {

	// Abstraction function:
	// TODO
	// represent a graph extends from Graph<L>,whose vertices is Person
	// Representation invariant:
	// TODO
	// Every person must be diffrent
	// Safety from rep exposure:
	// TODO
	// all fields are private

	private final Graph<Person> graph = Graph.empty();

	@Override
	public String toString() {
		return graph.toString();
	}

	/**
	 * Add the person to the friendshipgraph.
	 * 
	 * @param Vertex
	 *            A instantiation of class Person
	 */
	public void addVertex(Person Vertex) throws Exception {

		if (graph.vertices().contains(Vertex)) {
			throw new Exception("Wrong!Each person has a unique name");
		} else {
			graph.add(Vertex);
		}
	}

	/**
	 * Add the connection between people to the friendshipgraph.
	 * 
	 * @param m1,m2
	 *            A instantiation of class Person
	 */
	public void addEdge(Person m1, Person m2) {
		graph.set(m1, m2, 1);
	}

	/**
	 * calculate the distance between m1 and m2.
	 * 
	 * @param m1,m2
	 *            A instantiation of class Person
	 */
	public int getDistance(Person m1, Person m2) {
		Person now = m1;
		int lev = 0;
		Queue<Person> queue = new LinkedList<Person>();
		List<Person> crowed = new ArrayList<Person>();
		if (m2 == m1) {
			return lev;
		}
		queue.offer(now);
		crowed.add(now);
		while (!queue.isEmpty()) {
			now = queue.poll();
			lev++;
			for (Person fu : graph.targets(now).keySet()) {
				if (fu == m2)
					return lev;
				if (!crowed.contains(fu)) {
					queue.offer(fu);
					crowed.add(fu);
				}
			}

		}
		return -1;
	}

	
}
