package factory.GraphFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import edge.Edge;
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

public class SocialNetworkFactory extends GraphFactory {

	Logger log = Logger.getLogger("errorlog");
	private static final List<String> edgetype = Arrays.asList("FriendTie", "ForwardTie", "CommentTie");

	@Override
	public SocialNetwork createGraph(String filePath) throws graphExceptions {

		
		SocialNetwork g = new SocialNetwork();
		BufferedReader reader = null;
		List<String> EdgeType = new ArrayList<String>();
		Pattern p = Pattern.compile("\"(.+?)\"");
		Pattern lGraphType = Pattern.compile("GraphType = \"([^\"]+)\"");
		Pattern lGraphName = Pattern.compile("GraphName = \"([^\"]+)\"");
		Pattern lVertexType = Pattern.compile("VertexType = \"[^\"]+\"(, \"[^\"]+\")*");
		Pattern lVertex = Pattern.compile("Vertex = <\"([^\"]+)\", \"([^\"]+)\"(, <(\"[^\"]+\"(, \"[^\"]+\")*)>)?>");
		Pattern lEdgeType = Pattern.compile("EdgeType = \"[^\"]+\"(, \"[^\"]+\")*");
		Pattern lEdge = Pattern
				.compile("Edge = <\"([^\"]+)\", \"([^\"]+)\", \"([^\"]+)\", \"([^\"]+)\", \"([^\"]+)\", \"(Yes|No)\">");
		Pattern label = Pattern.compile("\\w+");
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				Pattern str = Pattern.compile("^\\w+\\b");
				Matcher matcher;
				matcher = str.matcher(tempString);
				String op = null;
				if (matcher.find()) {
					op = matcher.group(0);
				} else {
					continue;
				}

				switch (op) {
				case "GraphType":
					matcher = lGraphType.matcher(tempString);
					if (matcher.matches()) {
						String type = matcher.group(1);
						if (!type.equals("SocialNetwork")) {
							// 2.txt
							log.error("/" + tempString + "/"  + "It is an invalid commander");
							throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
						}
					} else {
						// 3.txt
						log.error("/" + tempString + "/"  + "It is an invalid commander");
						throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
					}
					break;

				case "GraphName":
					matcher = lGraphName.matcher(tempString);
					if (matcher.matches()) {
						String gLabel = matcher.group(1);
						Matcher m = label.matcher(gLabel);
						if (!m.matches()) {
							// 4.txt
							log.error("/" + tempString + "/"  + gLabel + " is an invalid label");
							throw new InvalidLabel("/" + tempString + "/" + "\n" + gLabel + " is an invalid label");
						}
						g.modifyL(gLabel);
					} else {
						// 5.txt
						log.error("/" + tempString + "/"  + "It is an invalid commander");
						throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
					}
					break;

				case "VertexType":
					matcher = lVertexType.matcher(tempString);
					if (matcher.matches()) {
						matcher = p.matcher(tempString);
						while (matcher.find()) {
							if (!matcher.group(1).equals("Person")) {
								// 6.txt
								log.error("/" + tempString + "/"  + matcher.group(1)
										+ " is an invalid vertex type");
								throw new InvalidVertexType("/" + tempString + "/" + "\n" + matcher.group(1)
										+ " is an invalid vertex type");
							}
						}
					} else {
						// 7.txt
						log.error("/" + tempString + "/"  + "It is an invalid commander");
						throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
					}
					break;

				case "Vertex":
					matcher = lVertex.matcher(tempString);
					if (matcher.matches()) {
						String vLabel = matcher.group(1);
						String vType = matcher.group(2);
						String s = matcher.group(4);
						if (s == null) {
							// 25.txt
							log.error("/" + tempString + "/"  + "There are not enough attributes");
							throw new NoAppropriateAttributes(
									"/" + tempString + "/" + "\n" + "There are not enough attributes");
						}
						Matcher m = label.matcher(vLabel);
						if (!m.matches()) {
							// 8.txt
							log.error("/" + tempString + "/"  + vLabel + " is an invalid label");
							throw new InvalidLabel("/" + tempString + "/" + "\n" + vLabel + " is an invalid label");
						}
						if (!vType.equals("Person")) {
							// 9.txt
							log.error(
									"/" + tempString + "/"  + vType + " is an invalid vertex type in this graph");
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
							log.error("/" + tempString + "/"  + "the number of attributes is wrong");
							throw new NoAppropriateAttributes(
									"/" + tempString + "/" + "\n" + "the number of attributes is wrong");
						}
						String s1 = sl.get(0);
						String s2 = sl.get(1);
						if (!(s1.equals("M") || s1.equals("F"))) {
							// 11.txt
							log.error("/" + tempString + "/"  + s1 + " is an invalid attribute");
							throw new NoAppropriateAttributes(
									"/" + tempString + "/" + "\n" + s1 + " is an invalid attribute");
						}
						try {
							Integer.valueOf(s2);
						} catch (NumberFormatException e) {
							// 12.txt
							log.error("/" + tempString + "/"  + s2 + " is an invalid attribute");
							throw new NoAppropriateAttributes(
									"/" + tempString + "/" + "\n" + s2 + " is an invalid attribute");
						}
						String[] args = new String[sl.size()];
						sl.toArray(args);
						Vertex v = new PersonVertexFactory().createVertex(vLabel, args);
						g.addVertex(v);
					} else {
						// 13.txt
						log.error("/" + tempString + "/"  + "It is an invalid commander");
						throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
					}
					break;

				case "EdgeType":
					matcher = lEdgeType.matcher(tempString);
					if (matcher.matches()) {
						matcher = p.matcher(tempString);
						while (matcher.find()) {
							if (!edgetype.contains(matcher.group(1))) {
								// 14.txt
								log.error(
										"/" + tempString + "/"  + matcher.group(1) + " is an invalid edge type");
								throw new InvalidEdgeType(
										"/" + tempString + "/" + "\n" + matcher.group(1) + " is an invalid edge type");
							}
							EdgeType.add(matcher.group(1));
						}
					} else {
						// 15.txt
						log.error("/" + tempString + "/"  + "It is an invalid commander");
						throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
					}
					break;

				case "Edge":
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
							log.error("/" + tempString + "/"  + eType + " is an invalid edge type");
							throw new InvalidEdgeType(
									"/" + tempString + "/" + "\n" + eType + " is an invalid edge type");
						}
						try {
							if (Double.valueOf(weight) > 1 || Double.valueOf(weight) < 0) {
								// 18.txt
								log.error("/" + tempString + "/"  + weight + " is an invalid weight");
								throw new InvalidWeight(
										"/" + tempString + "/" + "\n" + weight + " is an invalid weight");
							}
						} catch (NumberFormatException e) {
							// 19.txt
							log.error("/" + tempString + "/" + weight + " is an invalid weight");
							throw new InvalidWeight("/" + tempString + "/" + "\n" + weight + " is an invalid weight");
						}
						if (start.equals(end)) {
							// 20.txt
							log.warn("/" + tempString + "/"  + "there are no loops in this graph");
							break;
						}
						if (YN.equals("No")) {
							// 21.txt
							log.error("/" + tempString + "/"  + "there are no undirected edges in this graph");
							throw new DirectededgeWithoutDirection(
									"/" + tempString + "/" + "\n" + "there are no undirected edges in this graph");
						}
						List<Vertex> se = new ArrayList<Vertex>();
						int num = 0;
						for (Vertex v : g.vertices()) {
							if (num == 2) {
								break;
							}
							if (v.getLabel().equals(start)) {
								se.add(0, v);
								num++;
							} else if (v.getLabel().equals(end)) {
								se.add(v);
								num++;
							}
						}
						if (num != 2) {
							// 24.txt
							log.error("/" + tempString + "/"  + "there are no enough vertices in this graph");
							throw new NotExistedVertices(
									"/" + tempString + "/" + "\n" + "there are no enough vertices in this graph");
						}
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
					} else {
						// 22.txt
						log.error("/" + tempString + "/"  + "It is an invalid commander");
						throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
					}
					break;
				case "HyperEdge":
					// 23.txt
					log.warn("/" + tempString + "/"  + "There isn't a hyperedge in this graph");
					break;

				default:
					// 1.txt
					InvalidCommander e = new InvalidCommander(
							"/" + tempString + "/" + "\n" + "It is an invalid commander");
					log.error("/" + tempString + "/"  + "It is an invalid commander");
					throw e;
				}

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return g;
	}

}
