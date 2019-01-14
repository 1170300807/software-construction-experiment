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
import edge.WordNeighborhoodEdge;
import factory.EdgeFactory.CommentTieFactory;
import factory.EdgeFactory.ForwardTieFactory;
import factory.EdgeFactory.FriendTieFactory;
import factory.EdgeFactory.WordNeighborhoodEdgeFactory;
import factory.VertexFactory.PersonVertexFactory;
import factory.VertexFactory.WordVertexFactory;
import graph.Graph;
import graph.GraphPoet;
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

public class GraphPoetFactory extends GraphFactory {

	Logger log = Logger.getLogger("errorlog");
	Map<String, Vertex> vs = new HashMap<String, Vertex>(1800);
	
	
	GraphPoet g = new GraphPoet();
	List<String> EdgeType = new ArrayList<String>();
	Pattern lGraphType = Pattern.compile("GraphType\\s*=\\s*\"([^\"]+)\"");
	Pattern lGraphName = Pattern.compile("GraphName\\s*=\\s*\"([^\"]+)\"");
	Pattern lVertexType = Pattern.compile("VertexType\\s*=\\s*\"([^\"]+)\"");
	Pattern lVertex = Pattern.compile("Vertex\\s*=\\s*<\"([^\"]+)\",\\s*\"([^\"]+)\">");
	Pattern lEdgeType = Pattern.compile("EdgeType\\s*=\\s*\"([^\"]+)\"");
	Pattern lEdge = Pattern
			.compile("Edge\\s*=\\s*<\"([^\"]+)\",\\s*\"([^\"]+)\",\\s*\"([^\"]+)\",\\s*\"([^\"]+)\",\\s*\"([^\"]+)\",\\s*\"(Yes|No)\">");
	Pattern label = Pattern.compile("\\w+");
	
	
	
	@Override
	public GraphPoet createGraph(IOStrategy strategy, String filePath) throws graphExceptions {
		Instant t1 = Instant.now();
		List<String> str = strategy.IGraph(filePath);
		Instant t2 = Instant.now();
		System.out.println(t1.toString() + "-" + t2.toString());
		for(String tempString : str) {
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
			if(!edge(tempString)) {
				return false;
			}
			break;
		case "HyperEdge":
			// 20.txt
			log.warn("/" + tempString + "/"  + "There isn't a hyperedge in this graph");
			return false;

		default:
			// 1.txt
			InvalidCommander e = new InvalidCommander(
					"/" + tempString + "/" + "\n" + "It is an invalid commander");
			log.error("/" + tempString + "/"  + "It is an invalid commander");
			throw e;
		}
		
		return true;
	}
	
	private void graphtype(String tempString) throws graphExceptions {
		Matcher matcher ;
		matcher = lGraphType.matcher(tempString);
		if (matcher.matches()) {
			String type = matcher.group(1);
			if (!type.equals("GraphPoet")) {
				// 2.txt
				log.error("/" + tempString + "/"  + "It is an invalid commander");
				throw new InvalidCommander("/" + tempString + "\n"+ "/"  + "It is an invalid commander");
			}
		} else {
			// 3.txt
			log.error("/" + tempString + "/"  + "It is an invalid commander");
			throw new InvalidCommander("/" + tempString + "\n"+ "/"  + "It is an invalid commander");
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
				throw new InvalidLabel("/" + tempString + "\n"+ "/"  + gLabel + " is an invalid label");
			}
			g.modifyL(gLabel);
		} else {
			// 5.txt
			log.error("/" + tempString + "/"  + "It is an invalid commander");
			throw new InvalidCommander("/" + tempString + "\n"+ "/"  + "It is an invalid commander");
		}
	}
	private void vertextype(String tempString) throws graphExceptions {
		Matcher matcher;
		matcher = lVertexType.matcher(tempString);
		if (matcher.matches()) {
			if (!matcher.group(1).equals("Word")) {
				// 6.txt
				log.error(
						"/" + tempString + "/"  + matcher.group(1) + " is an invalid vertex type");
				throw new InvalidVertexType(
						"/" + tempString + "/"  + "\n"+ matcher.group(1) + " is an invalid vertex type");
			}

		} else {
			// 7.txt
			log.error("/" + tempString + "/"  + "It is an invalid commander");
			throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
		}
	}
	private void vertex (String tempString) throws graphExceptions {
		Matcher matcher;
		matcher = lVertex.matcher(tempString);
		if (matcher.matches()) {
			String vLabel = matcher.group(1);
			String vType = matcher.group(2);
			Matcher m = label.matcher(vLabel);
			if (!m.matches()) {
				// 8.txt
				log.error("/" + tempString + "/"  + vLabel + " is an invalid label");
				throw new InvalidLabel("/" + tempString + "/" + "\n" + vLabel + " is an invalid label");
			}
			if (!vType.equals("Word")) {
				// 9.txt
				log.error(
						"/" + tempString + "/"  + vType + " is an invalid vertex type in this graph");
				throw new InvalidVertexType(
						"/" + tempString + "/" + "\n" + vType + " is an invalid vertex type in this graph");
			}

			String[] args = null;
			Vertex v = new WordVertexFactory().createVertex(vLabel, args);
			g.addVertex(v);
		} else {
			// 10.txt
			log.error("/" + tempString + "/"  + "It is an invalid commander");
			throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
		}
	}
	private void edgetype(String tempString) throws graphExceptions {
		Matcher matcher;
		matcher = lEdgeType.matcher(tempString);
		if (matcher.matches()) {

			if (!matcher.group(1).equals("WordNeighborhood")) {
				// 11.txt
				log.error("/" + tempString + "/"  + matcher.group(1) + " is an invalid edge type");
				throw new InvalidEdgeType(
						"/" + tempString + "/" + "\n" + matcher.group(1) + " is an invalid edge type");
			}
			EdgeType.add(matcher.group(1));

		} else {
			// 12.txt
			log.error("/" + tempString + "/"  + "It is an invalid commander");
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
				// 13.txt
				log.error("/" + tempString + "/"  + eLabel + " is an invalid label");
				throw new InvalidLabel("/" + tempString + "/" + "\n" + eLabel + " is an invalid label");
			}
			if (!eType.equals("WordNeighborhood")) {
				// 14.txt
				log.error("/" + tempString + "/"  + eType + " is an invalid edge type");
				throw new InvalidEdgeType(
						"/" + tempString + "/" + "\n" + eType + " is an invalid edge type");
			}
			try {
				if (Integer.valueOf(weight) <= 0) {
					// 15.txt
					log.error("/" + tempString + "/"  + weight + " is an invalid weight");
					throw new InvalidWeight(
							"/" + tempString + "/" + "\n" + weight + " is an invalid weight");
				}
			} catch (NumberFormatException e) {
				// 16.txt
				log.error("/" + tempString + "/"  + weight + " is an invalid weight");
				throw new InvalidWeight("/" + tempString + "/" + "\n" + weight + " is an invalid weight");
			}
			if (YN.equals("No")) {
				// 17.txt
				log.error("/" + tempString + "/"  + "there are no undirected edges in this graph");
				throw new DirectededgeWithoutDirection(
						"/" + tempString + "/" + "\n" + "there are no undirected edges in this graph");
			}
			String sande = null;
			if(start.compareTo(end)<0) {
				sande = start + end;
			}else {
				sande = end + start;
			}
			if(g.isSE(sande)) {
				return false;
			}
			g.addSE(sande);
			List<Vertex> se = new ArrayList<Vertex>();
			Vertex v1 = null;
			Vertex v2 = null;
			if(start.equals(end)) {
				v1 = g.getVertex(start);
				se.add(v1);
			}else {
				v1 = g.getVertex(start);
				v2 = g.getVertex(end);
				if (v1 == null || v2 == null) {
					// 18.txt
					log.error("/" + tempString + "/"  + "not enough vertices");
					throw new NotExistedVertices("/" + tempString + "/" + "\n" + "not enough vertices");
				}
				se.add(v1);
				se.add(v2);
			}
			Edge e = new WordNeighborhoodEdgeFactory().createEdge(eLabel, se, Integer.valueOf(weight));
			g.addEdge(e);
			return true;
		} else {
			// 19.txt
			log.error("/" + tempString + "/"  + "It is an invalid commander");
			throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
		}
	}
}
