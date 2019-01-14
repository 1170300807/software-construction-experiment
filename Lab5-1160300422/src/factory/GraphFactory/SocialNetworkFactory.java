package factory.GraphFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import edge.Edge;
import factory.EdgeFactory.CommentTieFactory;
import factory.EdgeFactory.ForwardTieFactory;
import factory.EdgeFactory.FriendTieFactory;
import factory.VertexFactory.PersonVertexFactory;
import graph.SocialNetwork;
import myException.DirectededgeWithoutDirection;
import myException.InvalidCommander;
import myException.InvalidEdgeType;
import myException.InvalidLabel;
import myException.InvalidVertexType;
import myException.InvalidWeight;
import myException.NoAppropriateAttributes;
import myException.NotExistedVertices;
import myException.graphExceptions;
import vertex.Vertex;

public class SocialNetworkFactory extends GraphFactory {

	Logger log = Logger.getLogger("errorlog");
	private static final List<String> edgetype = Arrays.asList("FriendTie", "ForwardTie", "CommentTie");
	Map<String, Vertex> vs = new HashMap<String, Vertex>(1200);

	SocialNetwork g = new SocialNetwork();
	List<String> EdgeType = new ArrayList<String>();
	Pattern p = Pattern.compile("\"(.+?)\"");
	Pattern lGraphType = Pattern.compile("GraphType\\s*=\\s*\"([^\"]+)\"");
	Pattern lGraphName = Pattern.compile("GraphName\\s*=\\s*\"([^\"]+)\"");
	Pattern lVertexType = Pattern.compile("VertexType\\s*=\\s*\"[^\"]+\"(,\\s*\"[^\"]+\")*");
	Pattern lVertex = Pattern
			.compile("Vertex\\s*=\\s*<\"([^\"]+)\",\\s*\"([^\"]+)\"(,\\s*<(\"[^\"]+\"(,\\s*\"[^\"]+\")*)>)?>");
	Pattern lEdgeType = Pattern.compile("EdgeType\\s*=\\s*\"[^\"]+\"(,\\s*\"[^\"]+\")*");
	Pattern lEdge = Pattern.compile(
			"Edge\\s*=\\s*<\"([^\"]+)\",\\s*\"([^\"]+)\",\\s*\"([^\"]+)\",\\s*\"([^\"]+)\",\\s*\"([^\"]+)\",\\s*\"(Yes|No)\">");
	Pattern label = Pattern.compile("\\w+");

	@Override
	public SocialNetwork createGraph(IOStrategy strategy, String filePath) throws graphExceptions {
		long a = 0;
		Instant t1 = Instant.now();
		List<String> str = strategy.IGraph(filePath);		
		Instant t2 = Instant.now();
		System.out.println(t1.toString() + "-" + t2.toString());
		for (String tempString : str) {
			//System.out.println(++a);
			if (!this.Excute(tempString)) {
				continue;
			}
		}
		return g;
	}

	private boolean Excute(String tempString) throws graphExceptions {
		Pattern str = Pattern.compile("^\\w+\\b");
		Matcher matcher;
		matcher = str.matcher(tempString);
		String op = null;
		if (matcher.find()) {
			op = matcher.group(0);
		} else {
			return false;
		}

		switch (op) {
		case "GraphType":
			graphtype(tempString);
			break;

		case "GraphName":
			graphname(tempString);
			break;

		case "VertexType":
			vertextype(tempString);
			break;

		case "Vertex":
			vertex(tempString);
			break;

		case "EdgeType":
			edgetype(tempString);
			break;

		case "Edge":
			if (!edge(tempString)) {
				return false;
			}
			break;
		case "HyperEdge":
			// 23.txt
			log.warn("/" + tempString + "/" + "There isn't a hyperedge in this graph");
			break;

		default:
			// 1.txt
			InvalidCommander e = new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
			log.error("/" + tempString + "/" + "It is an invalid commander");
			throw e;
		}
		return true;
	}

	private void graphtype(String tempString) throws graphExceptions {
		Matcher matcher;
		matcher = lGraphType.matcher(tempString);
		if (matcher.matches()) {
			String type = matcher.group(1);
			if (!type.equals("SocialNetwork")) {
				// 2.txt
				log.error("/" + tempString + "/" + "It is an invalid commander");
				throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
			}
		} else {
			// 3.txt
			log.error("/" + tempString + "/" + "It is an invalid commander");
			throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
		}
	}

	private void graphname(String tempString) throws graphExceptions {
		Matcher matcher;
		matcher = lGraphName.matcher(tempString);
		if (matcher.matches()) {
			String gLabel = matcher.group(1);
			Matcher m = label.matcher(gLabel);
			if (!m.matches()) {
				// 4.txt
				log.error("/" + tempString + "/" + gLabel + " is an invalid label");
				throw new InvalidLabel("/" + tempString + "/" + "\n" + gLabel + " is an invalid label");
			}
			g.modifyL(gLabel);
		} else {
			// 5.txt
			log.error("/" + tempString + "/" + "It is an invalid commander");
			throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
		}
	}

	private void vertextype(String tempString) throws graphExceptions {
		Matcher matcher;
		matcher = lVertexType.matcher(tempString);
		if (matcher.matches()) {
			matcher = p.matcher(tempString);
			while (matcher.find()) {
				if (!matcher.group(1).equals("Person")) {
					// 6.txt
					log.error("/" + tempString + "/" + matcher.group(1) + " is an invalid vertex type");
					throw new InvalidVertexType(
							"/" + tempString + "/" + "\n" + matcher.group(1) + " is an invalid vertex type");
				}
			}
		} else {
			// 7.txt
			log.error("/" + tempString + "/" + "It is an invalid commander");
			throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
		}
	}

	private void vertex(String tempString) throws graphExceptions {
		Matcher matcher;
		matcher = lVertex.matcher(tempString);
		if (matcher.matches()) {
			String vLabel = matcher.group(1);
			String vType = matcher.group(2);
			String s = matcher.group(4);
			if (s == null) {
				// 25.txt
				log.error("/" + tempString + "/" + "There are not enough attributes");
				throw new NoAppropriateAttributes("/" + tempString + "/" + "\n" + "There are not enough attributes");
			}
			Matcher m = label.matcher(vLabel);
			if (!m.matches()) {
				// 8.txt
				log.error("/" + tempString + "/" + vLabel + " is an invalid label");
				throw new InvalidLabel("/" + tempString + "/" + "\n" + vLabel + " is an invalid label");
			}
			if (!vType.equals("Person")) {
				// 9.txt
				log.error("/" + tempString + "/" + vType + " is an invalid vertex type in this graph");
				throw new InvalidVertexType(
						"/" + tempString + "/" + "\n" + vType + " is an invalid vertex type in this graph");
			}
			List<String> sl = new ArrayList<String>();
			matcher = p.matcher(s);
			while (matcher.find()) {
				sl.add(matcher.group(1));
			}
			if (sl.size() != 2) {
				// 10.txt
				log.error("/" + tempString + "/" + "the number of attributes is wrong");
				throw new NoAppropriateAttributes("/" + tempString + "/" + "\n" + "the number of attributes is wrong");
			}
			String s1 = sl.get(0);
			String s2 = sl.get(1);
			if (!(s1.equals("M") || s1.equals("F"))) {
				// 11.txt
				log.error("/" + tempString + "/" + s1 + " is an invalid attribute");
				throw new NoAppropriateAttributes("/" + tempString + "/" + "\n" + s1 + " is an invalid attribute");
			}
			try {
				Integer.valueOf(s2);
			} catch (NumberFormatException e) {
				// 12.txt
				log.error("/" + tempString + "/" + s2 + " is an invalid attribute");
				throw new NoAppropriateAttributes("/" + tempString + "/" + "\n" + s2 + " is an invalid attribute");
			}
			String[] args = new String[sl.size()];
			sl.toArray(args);
			Vertex v = new PersonVertexFactory().createVertex(vLabel, args);
			g.addVertex(v);
		} else {
			// 13.txt
			log.error("/" + tempString + "/" + "It is an invalid commander");
			throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
		}
	}

	private void edgetype(String tempString) throws graphExceptions {
		Matcher matcher;
		matcher = lEdgeType.matcher(tempString);
		if (matcher.matches()) {
			matcher = p.matcher(tempString);
			while (matcher.find()) {
				if (!edgetype.contains(matcher.group(1))) {
					// 14.txt
					log.error("/" + tempString + "/" + matcher.group(1) + " is an invalid edge type");
					throw new InvalidEdgeType(
							"/" + tempString + "/" + "\n" + matcher.group(1) + " is an invalid edge type");
				}
				EdgeType.add(matcher.group(1));
			}
		} else {
			// 15.txt
			log.error("/" + tempString + "/" + "It is an invalid commander");
			throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
		}
	}

	private boolean edge(String tempString) throws graphExceptions {
		Matcher matcher;
		matcher = lEdge.matcher(tempString);
		if (matcher.matches()) {
			String eLabel = matcher.group(1);
			String eType = matcher.group(2);
			String weight = matcher.group(3);
			String start = matcher.group(4);
			String end = matcher.group(5);
			String YN = matcher.group(6);
			Matcher m = label.matcher(eLabel);
			if (!m.matches()) {
				// 16.txt
				log.error("/" + tempString + "/" + eLabel + " is an invalid label");
				throw new InvalidLabel("/" + tempString + "/" + "\n" + eLabel + " is an invalid label");
			}
			if (!EdgeType.contains(eType)) {
				// 17.txt
				log.error("/" + tempString + "/" + eType + " is an invalid edge type");
				throw new InvalidEdgeType("/" + tempString + "/" + "\n" + eType + " is an invalid edge type");
			}
			try {
				if (Double.valueOf(weight) > 1 || Double.valueOf(weight) < 0) {
					// 18.txt
					log.error("/" + tempString + "/" + weight + " is an invalid weight");
					throw new InvalidWeight("/" + tempString + "/" + "\n" + weight + " is an invalid weight");
				}
			} catch (NumberFormatException e) {
				// 19.txt
				log.error("/" + tempString + "/" + weight + " is an invalid weight");
				throw new InvalidWeight("/" + tempString + "/" + "\n" + weight + " is an invalid weight");
			}
			if (start.equals(end)) {
				// 20.txt
				log.warn("/" + tempString + "/" + "there are no loops in this graph");
				return false;
			}
			if (YN.equals("No")) {
				// 21.txt
				log.error("/" + tempString + "/" + "there are no undirected edges in this graph");
				throw new DirectededgeWithoutDirection(
						"/" + tempString + "/" + "\n" + "there are no undirected edges in this graph");
			}
			String sande = null;
			if (start.compareTo(end) < 0) {
				sande = start + end;
			} else {
				sande = end + start;
			}
			if (g.isSE(sande)) {
				return false;
			}
			g.addSE(sande);
			List<Vertex> se = new ArrayList<Vertex>();
			Vertex v1 = g.getVertex(start);
			Vertex v2 = g.getVertex(end);

			if (v1 == null || v2 == null) {
				// 24.txt
				log.error("/" + tempString + "/" + "there are no enough vertices in this graph");
				throw new NotExistedVertices(
						"/" + tempString + "/" + "\n" + "there are no enough vertices in this graph");
			}
			se.add(v1);
			se.add(v2);
			Edge e = null;
			switch (eType) {
			case "CommentTie":
				e = new CommentTieFactory().createEdge(eLabel, se, Double.valueOf(weight));
				break;
			case "ForwardTie":
				e = new ForwardTieFactory().createEdge(eLabel, se, Double.valueOf(weight));
				break;
			case "FriendTie":
				e = new FriendTieFactory().createEdge(eLabel, se, Double.valueOf(weight));
				break;
			}
			g.addEdge(e);
			return true;
		} else {
			// 22.txt
			log.error("/" + tempString + "/" + "It is an invalid commander");
			throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
		}
	}
}
