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
import factory.EdgeFactory.MovieActorRelationFactory;
import factory.EdgeFactory.MovieDirectorRelationFactory;
import factory.EdgeFactory.NetworkConnectionFactory;
import factory.EdgeFactory.SameMovieHyperEdgeFactory;
import factory.VertexFactory.ActorVertexFactory;
import factory.VertexFactory.ComputerVertexFactory;
import factory.VertexFactory.DirectorVertexFactory;
import factory.VertexFactory.MovieVertexFactory;
import factory.VertexFactory.RouterVertexFactory;
import factory.VertexFactory.ServerVertexFactory;
import factory.VertexFactory.WordVertexFactory;
import graph.Graph;
import graph.GraphPoet;
import graph.MovieGraph;
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
import myException.NotEnoughVerticesForHyperedge;
import myException.graphExceptions;
import vertex.Actor;
import vertex.Computer;
import vertex.Director;
import vertex.Movie;
import vertex.Router;
import vertex.Server;
import vertex.Vertex;

public class MovieGraphFactory extends GraphFactory {

	Logger log = Logger.getLogger("errorlog");
	
	private static final List<String> vertextype = Arrays.asList("Movie", "Director", "Actor");
	private static final List<String> edgetype = Arrays.asList("MovieDirectorRelation", "MovieActorRelation",
			"SameMovieHyperEdge");

	@Override
	public MovieGraph createGraph(String filePath) throws graphExceptions {

		
		MovieGraph g = new MovieGraph();
		BufferedReader reader = null;
		List<String> VertexType = new ArrayList<String>();
		List<String> EdgeType = new ArrayList<String>();
		Pattern p = Pattern.compile("\"([^\"]+?)\"");
		Pattern lGraphType = Pattern.compile("GraphType = \"([^\"]+)\"");
		Pattern lGraphName = Pattern.compile("GraphName = \"([^\"]+)\"");
		Pattern lVertexType = Pattern.compile("VertexType = \"[^\"]+\"(, \"[^\"]+\")*");
		Pattern lVertex = Pattern.compile("Vertex = <\"([^\"]+)\", \"([^\"]+)\"(, <(\"[^\"]+\"(, \"[^\"]+\")*)>)?>");
		Pattern lEdgeType = Pattern.compile("EdgeType = \"[^\"]+\"(, \"[^\"]+\")*");
		Pattern lEdge = Pattern
				.compile("Edge = <\"([^\"]+)\", \"([^\"]+)\", \"([^\"]+)\", \"([^\"]+)\", \"([^\"]+)\", \"(Yes|No)\">");
		Pattern lHyperEdge = Pattern
				.compile("HyperEdge = <\"([^\"]+)\", \"([^\"]+)\", \\{(\"[^\"]+\"(, \"[^\"]+\")*)\\}>");
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
						if (!type.equals("MovieGraph")) {
							// 2.txt
							log.error("/" + tempString + "/"  + "It is an invalid commander");
							throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
						}
					} else {
						// 3.txt
						log.error("/" + tempString + "/" + "It is an invalid commander");
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
							if (!vertextype.contains(matcher.group(1))) {
								// 6.txt
								log.error("/" + tempString + "/"  + matcher.group(1)
										+ " is an invalid vertex type");
								throw new InvalidVertexType("/" + tempString + "/" + "\n" + matcher.group(1)
										+ " is an invalid vertex type");
							}
							VertexType.add(matcher.group(1));
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
						if(s == null) {
							//32.txt
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
						if (!VertexType.contains(vType)) {
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

						String[] args = new String[sl.size()];
						sl.toArray(args);
						int s1 = 0;
						try {
							s1 = Integer.valueOf(args[0]);
						} catch (NumberFormatException ex) {
							// 10.txt
							log.error("/" + tempString + "/"  + s1 + " is an invalid attribute");
							throw new NoAppropriateAttributes(
									"/" + tempString + "/" + "\n" + s1 + " is an invalid attribute");
						}
						boolean tag = true;
						Vertex v = null;
						switch (vType) {
						case "Movie":
							if(args.length != 3) {
								//12+4.txt
								log.error("/" + tempString + "/"  + "They are invalid attributes");
								throw new NoAppropriateAttributes(
										"/" + tempString + "/" + "\n" + "They are invalid attributes");
							}
							double s3 = 0;
							try {
								s3 = Double.valueOf(args[2]);
							} catch (NumberFormatException ex) {
								// 11.txt
								log.error("/" + tempString + "/"  + s3 + " is an invalid attribute");
								throw new NoAppropriateAttributes(
										"/" + tempString + "/" + "\n" + s3 + " is an invalid attribute");
							}
							if ( s1 < 1900 || s1 > 2018 || s3 < 0 || s3 > 10) {
								tag = false;
							}
							String imdb = String.valueOf(args[2]);
							Pattern p1 = Pattern.compile("[0-9]+(.[0-9]{1,2})?\\b");
							if (!p1.matcher(imdb).matches()) {
								tag = false;
							}
							if (!tag) {
								// 12.txt
								log.error("/" + tempString + "/" + "They are invalid attributes");
								throw new NoAppropriateAttributes(
										"/" + tempString + "/" + "\n" + "They are invalid attributes");
							} else {
								v = new MovieVertexFactory().createVertex(vLabel, args);
							}
							break;
						case "Director":
							if(args.length != 2) {
								//13+2.txt
								log.error("/" + tempString + "/"  + "They are invalid attributes");
								throw new NoAppropriateAttributes(
										"/" + tempString + "/" + "\n" + "They are invalid attributes");
							}
							if (!(s1 > 0 && (args[1].equals("M") || args[1].equals("F")))) {
								// 13.txt
								log.error("/" + tempString + "/"  + "They are invalid attributes");
								throw new NoAppropriateAttributes(
										"/" + tempString + "/" + "\n" + "They are invalid attributes");
							}
							v = new DirectorVertexFactory().createVertex(vLabel, args);
							break;
						case "Actor":
							if(args.length != 2) {
								//14+2.txt
								log.error("/" + tempString + "/"  + "They are invalid attributes");
								throw new NoAppropriateAttributes(
										"/" + tempString + "/" + "\n" + "They are invalid attributes");
							}
							if (!(s1 > 0 && (args[1].equals("M") || args[1].equals("F")))) {
								// 14.txt
								log.error("/" + tempString + "/"  + "They are invalid attributes");
								throw new NoAppropriateAttributes(
										"/" + tempString + "/" + "\n" + "They are invalid attributes");
							}
							v = new ActorVertexFactory().createVertex(vLabel, args);
							break;
						}
						g.addVertex(v);
					} else {
						// 15.txt
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
								// 16.txt
								log.error(
										"/" + tempString + "/"  + matcher.group(1) + " is an invalid edge type");
								throw new InvalidEdgeType(
										"/" + tempString + "/" + "\n" + matcher.group(1) + " is an invalid edge type");
							}
							EdgeType.add(matcher.group(1));
						}
					} else {
						// 17.txt
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
							// 18.txt
							log.error("/" + tempString + "/"  + eLabel + " is an invalid label");
							throw new InvalidLabel("/" + tempString + "/" + "\n" + eLabel + " is an invalid label");
						}
						if (!EdgeType.contains(eType)) {
							// 19.txt
							log.error("/" + tempString + "/"  + eType + " is an invalid edge type");
							throw new InvalidEdgeType(
									"/" + tempString + "/"  + eType + " is an invalid edge type");
						}
						double w;
						try {
							w = Double.valueOf(weight);
						} catch (NumberFormatException e) {
							// 20.txt
							log.error("/" + tempString + "/"  + weight + " is an invalid weight");
							throw new InvalidWeight("/" + tempString + "/" + "\n" + weight + " is an invalid weight");
						}
						if (start.equals(end)) {
							// 21.txt
							log.warn("/" + tempString + "/"  + "there are no loops in this graph");
							continue;
						}
						if (YN.equals("Yes")) {
							// 22.txt
							log.warn("/" + tempString + "/"  + "there are no undirected edges in this graph");
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
							// 23.txt
							log.error("/" + tempString + "/"  + "there are no enough vertices in this graph");
							throw new NotExistedVertices(
									"/" + tempString + "/" + "\n" + "there are no enough vertices in this graph");
						}
						Vertex v1 = se.get(0);
						Vertex v2 = se.get(1);

						Edge e = null;
						switch (eType) {
						case "MovieActorRelation":
							if (!((v1 instanceof Movie && v2 instanceof Actor)
									|| (v1 instanceof Actor && v2 instanceof Movie))) {
								// 24.txt
								log.warn("/" + tempString + "/"  + "MovieActorRelation can't consist of " + start
										+ " and " + end);
								continue;
							}
							e = new MovieActorRelationFactory().createEdge(eLabel, se, w);
							g.addEdge(e);
							break;
						case "MovieDirectorRelation":
							if (!((v1 instanceof Movie && v2 instanceof Director)
									|| (v1 instanceof Director && v2 instanceof Movie))) {
								// 25.txt
								log.warn("/" + tempString + "/"  + "MovieDirectorRelation can't consist of "
										+ start + " and " + end);
								continue;
							}
							e = new MovieDirectorRelationFactory().createEdge(eLabel, se, w);
							g.addEdge(e);
							break;
						}
					} else {
						// 26.txt
						log.error("/" + tempString + "/"  + "It is an invalid commander");
						throw new InvalidCommander("/" + tempString + "/" + "\n" + "It is an invalid commander");
					}
					break;
				case "HyperEdge":
					matcher = lHyperEdge.matcher(tempString);
					if (matcher.matches()) {
						String eLabel = matcher.group(1);
						String eType = matcher.group(2);
						String vs = matcher.group(3);
						Matcher m = label.matcher(eLabel);
						if (!m.matches()) {
							// 27.txt
							log.error("/" + tempString + "/"  + eLabel + " is an invalid label");
							throw new InvalidLabel("/" + tempString + "/" + "\n" + eLabel + " is an invalid label");
						}
						if (!eType.equals("SameMovieHyperEdge")) {
							// 28.txt
							log.error("/" + tempString + "/"  + eType + " is an invalid edge type");
							throw new InvalidEdgeType(
									"/" + tempString + "/" + "\n" + eType + " is an invalid edge type");
						}
						List<String> vertexLabels = new ArrayList<String>();
						matcher = p.matcher(vs);
						while (matcher.find()) {
							vertexLabels.add(matcher.group(1));
						}
						if (vertexLabels.size() < 2) {
							// 29.txt
							log.error("/" + tempString + "/"  + "Not enough vertices to make a hyperedge");
							throw new NotEnoughVerticesForHyperedge(
									"/" + tempString + "/" + "\n" + "Not enough vertices to make a hyperedge");
						}
						List<Vertex> vertices = new ArrayList<Vertex>();
						for (Vertex v : g.vertices()) {
							if (vertexLabels.contains(v.getLabel())) {
								vertices.add(v);
								if (!(v instanceof Actor)) {
									// 30.txt
									log.error("/" + tempString + "/"  + "Each vertex should be an actor");
									throw new InvalidVertexType(
											"/" + tempString + "/" + "\n" + "Each vertex should be an actor");
								}
							}
						}
						Edge e = new SameMovieHyperEdgeFactory().createEdge(eLabel, vertices, -1);
						g.addEdge(e);
					} else {
						InvalidCommander e = new InvalidCommander(
								"/" + tempString + "/" + "\n" + "It is an invalid commander");
						// 31.txt
						log.error("/" + tempString + "/"  + "It is an invalid commander");
						throw e;
					}
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
