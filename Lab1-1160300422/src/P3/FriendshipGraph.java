package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


/**
 * FriendshipGraph provides methods that how to construct the friendshipgraph.
 * 
 */
public class FriendshipGraph {
	public Map<Person, List<Person>> map = new HashMap<Person, List<Person>>();

	/**
	 * Add the person to the friendshipgraph.
	 * 
	 * @param Vertex
	 *            A instantiation of class Person
	 */
	public void addVertex(Person Vertex) throws Exception {

		if (this.map.containsKey(Vertex)) {
			throw new Exception("Wrong!Each person has a unique name");
		} else {
			List<Person> friend = new ArrayList<Person>();
			map.put(Vertex, friend);
		}
	}

	/**
	 * Add the connection between people to the friendshipgraph.
	 * 
	 * @param m1,m2
	 *            A instantiation of class Person
	 */
	public void addEdge(Person m1, Person m2) {
		int n = map.get(m1).size(), i = 0;
		boolean choice = true;
		while (i < n) {
			if (map.get(m1).get(i) == m2) {
				choice = false;
				break;
			}
			i++;
		}
		if (choice) {
			map.get(m1).add(m2);
		}
	}

	/**
	 * calculate the distance between m1 and m2.
	 * 
	 * @param m1,m2
	 *            A instantiation of class Person
	 */
	public int getDistance(Person m1, Person m2) {
		Person now = m1;
		Person f = m1;
		int i = 0;
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
			int n = map.get(now).size();
			while (i < n) {
				f = map.get(now).get(i);
				if (f == m2)
					return lev;
				if (!crowed.contains(f)) {
					queue.offer(f);
					crowed.add(f);
				}
				i++;
			}
			i = 0;
		}
		return -1;
	}

	public static void main(String args[]) throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross));
		System.out.println(graph.getDistance(rachel, ben));
		System.out.println(graph.getDistance(rachel, rachel));
		System.out.println(graph.getDistance(rachel, kramer));
	}
}
