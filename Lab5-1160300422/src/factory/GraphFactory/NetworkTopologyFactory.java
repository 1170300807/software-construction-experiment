package factory.GraphFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
import factory.EdgeFactory.NetworkConnectionFactory;
import factory.VertexFactory.ComputerVertexFactory;
import factory.VertexFactory.RouterVertexFactory;
import factory.VertexFactory.ServerVertexFactory;
import factory.VertexFactory.WordVertexFactory;
import graph.Graph;
import graph.GraphPoet;
import graph.NetworkTopology;
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
import vertex.Computer;
import vertex.Router;
import vertex.Server;
import vertex.Vertex;

public class NetworkTopologyFactory extends GraphFactory {

	Logger log = Logger.getLogger("errorlog");
	private static final List<String> vertextype = Arrays.asList("Computer", "Router", "Server");
	Map<String, Vertex> vs = new HashMap<String, Vertex>(3000);

	NetworkTopology g = new NetworkTopology();
	List<String> VertexType = new ArrayList<String>();
	Pattern p = Pattern.compile("\"(.+?)\"");
	Pattern lGraphType = Pattern.compile("GraphType\\s*=\\s*\"([^\"]+)\"");
	Pattern lGraphName = Pattern.compile("GraphName\\s*=\\s*\"([^\"]+)\"");
	Pattern lVertexType = Pattern.compile("VertexType\\s*=\\s*\"[^\"]+\"(, \"[^\"]+\")*");
	Pattern lVertex = Pattern
			.compile("Vertex\\s*=\\s*<\"([^\"]+)\",\\s*\"([^\"]+)\"(,\\s*<(\"[^\"]+\"(,\\s*\"[^\"]+\")*)>)?>");
	Pattern lEdgeType = Pattern.compile("EdgeType\\s*=\\s*\"[^\"]+\"");
	Pattern lEdge = Pattern.compile(
			"Edge\\s*=\\s*<\"([^\"]+)\",\\s*\"([^\"]+)\",\\s*\"([^\"]+)\",\\s*\"([^\"]+)\",\\s*\"([^\"]+)\",\\s*\"(Yes|No)\">");
	Pattern label = Pattern.compile("\\w+");

	long a = 0;

	@Override
	public NetworkTopology createGraph(IOStrategy strategy, String filePath) throws graphExceptions {
		Instant t1 = Instant.now();
		List<String> str = strategy.IGraph(filePath);
		Instant t2 = Instant.now();
		System.out.println(t1.toString() + "-" + t2.toString());
		for (String tempString : str) {
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
			// 24.txt
			log.warn("/" + tempString + "/" + "There isn't a hyperedge in this graph");
			return false;

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
			if (!type.equals("NetworkTopology")) {
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
				if (!vertextype.contains(matcher.group(1))) {
					// 6.txt
					log.error("/" + tempString + "/" + matcher.group(1) + " is an invalid vertex type");
					throw new InvalidVertexType(
							"/" + tempString + "/" + "\n" + matcher.group(1) + " is an invalid vertex type");
				}
				VertexType.add(matcher.group(1));
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
			if (!VertexType.contains(vType)) {
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
			if (sl.size() != 1) {
				// 10.txt
				log.error("/" + tempString + "/" + "the number of attributes is wrong");
				throw new NoAppropriateAttributes("/" + tempString + "/" + "\n" + "the number of attributes is wrong");
			}
			String s1 = sl.get(0);
			boolean tag = true;
			String[] splitIP = s1.split("\\.");
			if (splitIP.length != 4) {
				tag = false;
			}
			for (String ss : splitIP) {
				try {
					int now = Integer.valueOf(ss);
					if (!(now >= 0 && now <= 255)) {
						tag = false;
					}
				} catch (NumberFormatException e) {
					tag = false;
				}
			}
			if (!tag) {
				// 11.txt
				log.error("/" + tempString + "/" + s1 + " is an invalid attribute");
				throw new NoAppropriateAttributes("/" + tempString + "/" + "\n" + s1 + " is an invalid attribute");
			}
			String[] args = new String[sl.size()];
			sl.toArray(args);
			Vertex v = null;
			switch (vType) {
			case "Computer":
				v = new ComputerVertexFactory().createVertex(vLabel, args);
				break;
			case "Router":
				v = new RouterVertexFactory().createVertex(vLabel, args);
				break;
			case "Server":
				v = new ServerVertexFactory().createVertex(vLabel, args);
				break;
			}
			g.addVertex(v);
		} else {
			// 12.txt
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
				if (!matcher.group(1).equals("NetworkConnection")) {
					// 13.txt
					log.error("/" + tempString + "/" + matcher.group(1) + " is an invalid edge type");
					throw new InvalidEdgeType(
							"/" + tempString + "/" + "\n" + matcher.group(1) + " is an invalid edge type");
				}
			}
		} else {
			// 14.txt
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
				// 15.txt
				log.error("/" + tempString + "/" + eLabel + " is an invalid label");
				throw new InvalidLabel("/" + tempString + "/" + "\n" + eLabel + " is an invalid label");
			}
			if (!eType.equals("NetworkConnection")) {
				// 16.txt
				log.error("/" + tempString + "/" + eType + " is an invalid edge type");
				throw new InvalidEdgeType("/" + tempString + "/" + "\n" + eType + " is an invalid edge type");
			}
			try {
				if (Double.valueOf(weight) <= 0) {
					// 17.txt
					log.error("/" + tempString + "/" + weight + " is an invalid weight");
					throw new InvalidWeight("/" + tempString + "/" + "\n" + weight + " is an invalid weight");
				}
			} catch (NumberFormatException e) {
				// 18.txt
				log.error("/" + tempString + "/" + weight + " is an invalid weight");
				throw new InvalidWeight("/" + tempString + "/" + "\n" + weight + " is an invalid weight");
			}
			if (start.equals(end)) {
				// 19.txt
				log.warn("/" + tempString + "/" + "there are no loops in this graph");
				return false;
			}
			if (YN.equals("Yes")) {
				// 20.txt
				log.warn("/" + tempString + "/" + "there are no undirected edges in this graph");
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
			Vertex v1 = null;
			Vertex v2 = null;
			List<Vertex> se = new ArrayList<Vertex>();
			v1 = g.getVertex(start);
			v2 = g.getVertex(end);
			se.add(v1);
			se.add(v2);
			if (v1 == null || v2 == null) {
				// 21.txt
				log.error("/" + tempString + "/" + "there are no enough vertices in this graph");
				throw new NotExistedVertices(
						"/" + tempString + "/" + "\n" + "there are no enough vertices in this graph");
			} else if (v1.getClass() == v2.getClass()) {
				if (!(v1 instanceof Router)) {
					// 22.txt
					log.error("/" + tempString + "/" + "It's an invalid edge");
					return false;
				}
			}
			Edge e = new NetworkConnectionFactory().createEdge(eLabel, se, Double.valueOf(weight));
			g.addEdge(e);
			return true;
		} else {
			// 23.txt
			log.error("/" + tempString + "/" + "It is an invalid commander");
			throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
		}
	}

}
