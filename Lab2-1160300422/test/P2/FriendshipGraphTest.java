
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import graph.Graph;

//import P2.FriendshipGraph;
//import P2.Person;

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
		try {
				graph.addVertex(kramer);
	      } catch ( Exception ex) {
	          	assertEquals("Wrong!Each person has a unique name",ex.getMessage());
	      }
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
		
		assertEquals("vertices:Rachel,Ben,Ross,Kramer\n" + 
				"edges:",graph.toString());
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
		assertEquals("vertices:Rachel,Ben,Ross,Kramer\n" + 
				"edges:(Rachel,Ross,1)\n" + 
				"(Ross,Rachel,1)\n" + 
				"(Ross,Ben,1)\n" + 
				"(Ben,Ross,1)\n" + 
				"",graph.toString());	
	}
	
}
