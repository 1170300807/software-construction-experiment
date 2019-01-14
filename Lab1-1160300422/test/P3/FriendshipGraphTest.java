

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import P3.FriendshipGraph;
import P3.Person;

public class FriendshipGraphTest {

	/**
	 * Tests getDistance.
	 * 
	 * @throws Exception
	 */
	@Test
	public void getDistanceTest() throws Exception {
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
		assertEquals(1, graph.getDistance(rachel, ross));
		assertEquals(2, graph.getDistance(rachel, ben));
		assertEquals(0, graph.getDistance(rachel, rachel));
		assertEquals(-1, graph.getDistance(rachel, kramer));
	}

	/**
	 * Tests addVertex.
	 * 
	 * @throws Exception
	 */
	@Test
	public void addVertexTest() throws Exception {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		List<Person> member = new ArrayList<Person>();
		member.add(rachel);
		member.add(ross);
		member.add(ben);
		member.add(kramer);
		assertTrue(graph.map.size() == 4);
		assertTrue(graph.map.containsKey(rachel));
		assertTrue(graph.map.containsKey(ross));
		assertTrue(graph.map.containsKey(ben));
		assertTrue(graph.map.containsKey(kramer));
	}

	/**
	 * Tests addEdge.
	 * 
	 * @throws Exception
	 */
	@Test
	public void addEdgeTest() throws Exception {
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
		List<Person> Rachel = new ArrayList<Person>();
		List<Person> Ross = new ArrayList<Person>();
		List<Person> Ben = new ArrayList<Person>();
		List<Person> Kramer = new ArrayList<Person>();
		Rachel.add(ross);
		Ross.add(rachel);
		Ross.add(ben);
		Ben.add(ross);
		assertEquals(Rachel, graph.map.get(rachel));
		assertEquals(Ross, graph.map.get(ross));
		assertEquals(Ben, graph.map.get(ben));
		assertEquals(Kramer, graph.map.get(kramer));
	}
}
