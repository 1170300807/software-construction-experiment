/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>
 * GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph. Vertices in the graph are words. Words are defined as
 * non-empty case-insensitive strings of non-space non-newline characters. They
 * are delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     Hello, HELLO, hello, goodbye!
 * </pre>
 * <p>
 * the graph would contain two edges:
 * <ul>
 * <li>("hello,") -> ("hello,") with weight 2
 * <li>("hello,") -> ("goodbye!") with weight 1
 * </ul>
 * <p>
 * where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>
 * Given an input string, GraphPoet generates a poem by attempting to insert a
 * bridge word between every adjacent pair of words in the input. The bridge
 * word between input words "w1" and "w2" will be some "b" such that w1 -> b ->
 * w2 is a two-edge-long path with maximum-weight weight among all the
 * two-edge-long paths from w1 to w2 in the affinity graph. If there are no such
 * paths, no bridge word is inserted. In the output poem, input words retain
 * their original case, while bridge words are lower case. The whitespace
 * between every word in the poem is a single space.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     This is a test of the Mugar Omni Theater sound system.
 * </pre>
 * <p>
 * on this input:
 * 
 * <pre>
 *     Test the system.
 * </pre>
 * <p>
 * the output poem would be:
 * 
 * <pre>
 *     Test of the system.
 * </pre>
 * 
 * <p>
 * PS2 instructions: this is a required ADT class, and you MUST NOT weaken the
 * required specifications. However, you MAY strengthen the specifications and
 * you MAY add additional methods. You MUST use Graph in your rep, but otherwise
 * the implementation of this class is up to you.
 */
public class GraphPoet {

	private final Graph<String> graph = Graph.empty();

	// Abstraction function:
	// TODO
	// Representation invariant:
	// TODO
	// Safety from rep exposure:
	// TODO
	public Graph<String> getGraph() {
		return this.graph;
	}

	/**
	 * Create a new poet with the graph from corpus (as described above).
	 * 
	 * @param corpus
	 *            text file from which to derive the poet's affinity graph
	 * @throws IOException
	 *             if the corpus file cannot be found or read
	 */
	public GraphPoet(File corpus) throws IOException {

		BufferedReader reader = null;
		Map<String, Integer> edge = new HashMap<String, Integer>();
		String left = null;
		try {
			reader = new BufferedReader(new FileReader(corpus));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				String[] a = tempString.split(" ");
				if (left != null) {

					String now = new String(left.toLowerCase() + "," + a[0].toLowerCase());
					if (edge.containsKey(now)) {
						edge.put(now, edge.get(now) + 1);
					}
					else {
						edge.put(now, 1);
						
					}
					
				}
				for (int i = 0; i < a.length - 1; i++) {
					String now = new String(a[i].toLowerCase() + "," + a[i + 1].toLowerCase());
					if (edge.containsKey(now))
						edge.put(now, edge.get(now) + 1);
					else
						edge.put(now, 1);
				}
				left = new String(a[a.length - 1]);
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String s : edge.keySet()) {
			String[] a = s.split(",");
			if (a.length == 2)
				graph.set(a[0], a[1], edge.get(s));

		}
		checkRep();
	}

	// TODO checkRep
	private void checkRep() {
		assert true;
	}
	/**
	 * Generate a poem.
	 * 
	 * @param input
	 *            string from which to create the poem
	 * @return poem (as described above)
	 */
	public String poem(String input) {
		String[] section = input.split("\n");
		StringBuffer str = new StringBuffer();
		for (int j = 0; j < section.length; j++) {
			String[] a = section[j].split(" ");
			if (j != 0)
				str.append("\n");
			for (int i = 0; i < a.length - 1; i++) {
				String bridge = null;
				String w1 = a[i].toLowerCase();
				String w2 = a[i + 1].toLowerCase();
				int weight = 0;
				Map<String, Integer> map1 = graph.targets(w1);
				Map<String, Integer> map2 = graph.sources(w2);
				if (str.length() == 0)
					str.append(a[i] + " ");
				else
					str.append(a[i] + " ");
				if (!map1.isEmpty() && !map2.isEmpty()) {
					for (String n : map1.keySet()) {
						for (String m : map2.keySet()) {
							if (n.equalsIgnoreCase(m)) {
								int now = map1.get(n) + map2.get(m);
								if (now > weight) {
									bridge = n;
									weight = now;
								}
							}
						}
					}
				}
				if (bridge != null) {
					str.append(bridge + " ");
				}

			}

			str.append(a[a.length - 1]);
		}

		return str.toString();
	}

	// TODO toString()
	public String toString() {
		return this.graph.toString();
	}
}
