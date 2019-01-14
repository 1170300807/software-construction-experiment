package factory.GraphFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private static String name = GraphPoetFactory.class.getName();
	private static Logger log = Logger.getLogger(name);

	@Override
	public GraphPoet createGraph(String filePath) throws graphExceptions {

		log.setLevel(Level.FINEST);
		log.setUseParentHandlers(false);
		Handler handler = new ConsoleHandler();
		handler.setLevel(Level.FINEST);
		log.addHandler(handler);
		GraphPoet g = new GraphPoet();
		BufferedReader reader = null;
		List<String> EdgeType = new ArrayList<String>();
		Pattern lGraphType = Pattern.compile("GraphType = \"([^\"]+)\"");
		Pattern lGraphName = Pattern.compile("GraphName = \"([^\"]+)\"");
		Pattern lVertexType = Pattern.compile("VertexType = \"([^\"]+)\"");
		Pattern lVertex = Pattern.compile("Vertex = <\"([^\"]+)\", \"([^\"]+)\">");
		Pattern lEdgeType = Pattern.compile("EdgeType = \"([^\"]+)\"");
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
						if (!type.equals("GraphPoet")) {
							// 2.txt
							log.warning("/" + tempString + "/" + "\n" + "It is an invalid commander");
							throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
						}
					} else {
						// 3.txt
						log.warning("/" + tempString + "/" + "\n" + "It is an invalid commander");
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
							log.warning("/" + tempString + "/" + "\n" + gLabel + " is an invalid label");
							throw new InvalidLabel("/" + tempString + "/" + "\n" + gLabel + " is an invalid label");
						}
						g.modifyL(gLabel);
					} else {
						// 5.txt
						log.warning("/" + tempString + "/" + "\n" + "It is an invalid commander");
						throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
					}
					break;

				case "VertexType":
					matcher = lVertexType.matcher(tempString);
					if (matcher.matches()) {
						if (!matcher.group(1).equals("Word")) {
							// 6.txt
							log.warning(
									"/" + tempString + "/" + "\n" + matcher.group(1) + " is an invalid vertex type");
							throw new InvalidVertexType(
									"/" + tempString + "/" + "\n" + matcher.group(1) + " is an invalid vertex type");
						}

					} else {
						// 7.txt
						log.warning("/" + tempString + "/" + "\n" + "It is an invalid commander");
						throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
					}
					break;

				case "Vertex":
					matcher = lVertex.matcher(tempString);
					if (matcher.matches()) {
						String vLabel = matcher.group(1);
						String vType = matcher.group(2);
						Matcher m = label.matcher(vLabel);
						if (!m.matches()) {
							// 8.txt
							log.warning("/" + tempString + "/" + "\n" + vLabel + " is an invalid label");
							throw new InvalidLabel("/" + tempString + "/" + "\n" + vLabel + " is an invalid label");
						}
						if (!vType.equals("Word")) {
							// 9.txt
							log.warning(
									"/" + tempString + "/" + "\n" + vType + " is an invalid vertex type in this graph");
							throw new InvalidVertexType(
									"/" + tempString + "/" + "\n" + vType + " is an invalid vertex type in this graph");
						}

						String[] args = null;
						Vertex v = new WordVertexFactory().createVertex(vLabel, args);
						g.addVertex(v);
					} else {
						// 10.txt
						log.warning("/" + tempString + "/" + "\n" + "It is an invalid commander");
						throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
					}
					break;

				case "EdgeType":
					matcher = lEdgeType.matcher(tempString);
					if (matcher.matches()) {

						if (!matcher.group(1).equals("WordNeighborhoodEdge")) {
							// 11.txt
							log.warning("/" + tempString + "/" + "\n" + matcher.group(1) + " is an invalid edge type");
							throw new InvalidEdgeType(
									"/" + tempString + "/" + "\n" + matcher.group(1) + " is an invalid edge type");
						}
						EdgeType.add(matcher.group(1));

					} else {
						// 12.txt
						log.warning("/" + tempString + "/" + "\n" + "It is an invalid commander");
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
							// 13.txt
							log.warning("/" + tempString + "/" + "\n" + eLabel + " is an invalid label");
							throw new InvalidLabel("/" + tempString + "/" + "\n" + eLabel + " is an invalid label");
						}
						if (!eType.equals("WordNeighborhoodEdge")) {
							// 14.txt
							log.warning("/" + tempString + "/" + "\n" + eType + " is an invalid edge type");
							throw new InvalidEdgeType(
									"/" + tempString + "/" + "\n" + eType + " is an invalid edge type");
						}
						try {
							if (Integer.valueOf(weight) <= 0) {
								// 15.txt
								log.warning("/" + tempString + "/" + "\n" + weight + " is an invalid weight");
								throw new InvalidWeight(
										"/" + tempString + "/" + "\n" + weight + " is an invalid weight");
							}
						} catch (NumberFormatException e) {
							System.out.println(198);
							// 16.txt
							log.warning("/" + tempString + "/" + "\n" + weight + " is an invalid weight");
							throw new InvalidWeight("/" + tempString + "/" + "\n" + weight + " is an invalid weight");
						}
						if (YN.equals("No")) {
							// 17.txt
							log.warning("/" + tempString + "/" + "\n" + "there are no undirected edges in this graph");
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
							// 18.txt
							log.warning("/" + tempString + "/" + "\n" + "not enough vertices");
							throw new NotExistedVertices("/" + tempString + "/" + "\n" + "not enough vertices");
						}
						Edge e = new WordNeighborhoodEdgeFactory().createEdge(eLabel, se, Integer.valueOf(weight));
						g.addEdge(e);
					} else {
						// 19.txt
						log.warning("/" + tempString + "/" + "\n" + "It is an invalid commander");
						throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
					}
					break;
				case "HyperEdge":
					// 20.txt
					log.info("/" + tempString + "/" + "\n" + "There isn't a hyperedge in this graph");
					continue;

				default:
					// 1.txt
					InvalidCommander e = new InvalidCommander(
							"/" + tempString + "/" + "\n" + "It is an invalid commander");
					log.warning("/" + tempString + "/" + "\n" + "It is an invalid commander");
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
