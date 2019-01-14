/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

	// Testing strategy
	// TODO
	// Contructer():
	// According to the text ,construct a graph
	// Poem():
	// return a string --the poet,it will use the graph to generate a poem by
	// inserting (where possible) a bridge word between each pair of input words:
	// the text graph can be complex including loop.
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	// TODO tests
	@Test
	public void testContructer() throws IOException {
		// This is a test of the Mugar Omni Theater sound system.
		GraphPoet graphPoet = new GraphPoet(new File("src/P1/poet/mugar-omni-theater.txt"));
		assertEquals(1, graphPoet.getGraph().sources("of").get("test").intValue());
		assertEquals(1, graphPoet.getGraph().sources("system.").get("sound").intValue());
		assertEquals(1, graphPoet.getGraph().targets("sound").get("system.").intValue());
		assertEquals(11, graphPoet.getGraph().vertices().size());
		assertEquals("vertices:the,a,test,theater,mugar,of,sound,this,is,omni,system.\n" + "edges:(of,the,1)\n"
				+ "(theater,sound,1)\n" + "(a,test,1)\n" + "(sound,system.,1)\n" + "(test,of,1)\n" + "(the,mugar,1)\n"
				+ "(omni,theater,1)\n" + "(is,a,1)\n" + "(this,is,1)\n" + "(mugar,omni,1)\n" + "",
				graphPoet.getGraph().toString());
		GraphPoet graphPoet2 = new GraphPoet(new File("src/P1/poet/supertest.txt"));
		assertEquals("vertices:a,b,c,d,e,f,g\n" + 
				"edges:(f,g,2)\n" + 
				"(g,f,1)\n" + 
				"(g,g,1)\n" + 
				"(a,b,1)\n" + 
				"(b,a,1)\n" + 
				"(b,c,1)\n" + 
				"(c,b,1)\n" + 
				"(a,d,1)\n" + 
				"(d,c,2)\n" + 
				"(c,d,2)\n" + 
				"(d,e,1)\n" + 
				"(d,d,2)\n" + 
				"(e,f,1)\n" + 
				"", graphPoet2.getGraph().toString());
		System.out.println();
	}

	@Test
	public void testPoem() throws IOException {
		
		GraphPoet graphPoet = new GraphPoet(new File("src/P1/poet/supertest.txt"));
		assertEquals("a d e", graphPoet.poem("a e"));
		assertEquals("d d e", graphPoet.poem("d e"));
		assertEquals("f g f", graphPoet.poem("f f"));
		assertEquals("a d c", graphPoet.poem("a c"));
		assertEquals("A d c", graphPoet.poem("A c"));
		assertEquals("a f", graphPoet.poem("a f"));
		assertEquals("c d d", graphPoet.poem("c d"));
		assertEquals("a d c d d d e f g", graphPoet.poem("a c d e g"));
		assertEquals("h i", graphPoet.poem("h i"));
		GraphPoet nimoy = new GraphPoet(new File("src/P1/poet/mugar-omni-theater.txt"));
		String input = "Test the system.";
		assertEquals("Test of the system.", nimoy.poem(input));
		assertEquals("Test of the system.\n" + "Test of the system.", nimoy.poem(input + "\n" + input));
	}
}
