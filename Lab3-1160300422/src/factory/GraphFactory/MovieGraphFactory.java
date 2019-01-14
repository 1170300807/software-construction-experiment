package factory.GraphFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import graph.MovieGraph;
import graph.NetworkTopology;
import vertex.Vertex;

public class MovieGraphFactory extends GraphFactory {

	@Override
	public MovieGraph createGraph(String filePath) {
		MovieGraph g = new MovieGraph();
		BufferedReader reader = null;
		List<String> VertexType = new ArrayList<String>();
		List<String> EdgeType = new ArrayList<String>();
		List<Vertex> vertices = new ArrayList<Vertex>();
		Pattern p = Pattern.compile("\"([\\S\\s]+?)\"");
		Pattern lGraphType = Pattern.compile("GraphType = \"([a-zA-Z]+)\"");
		Pattern lGraphName = Pattern.compile("GraphName = \"(\\w+)\"");
		Pattern lVertexType = Pattern.compile("VertexType = \"[a-zA-Z]+\"(, \"[a-zA-Z]+\")*");
		Pattern lVertex = Pattern.compile("Vertex = <\"(\\w+)\", \"([a-zA-Z]+)\", <(.+)>>");
		Pattern lEdgeType = Pattern.compile("EdgeType = \"[a-zA-Z]+\"(, \"[a-zA-Z]+\")*");
		Pattern lEdge = Pattern.compile(
				"Edge = <\"(\\w+)\", \"([a-zA-Z]+)\", \"(-?[0-9]+(.[0-9]+)?)\", \"(\\w+)\", \"(\\w+)\", \"(Yes|No)\">");
		Pattern lHyperEdge = Pattern.compile("HyperEdge = <\"(\\w+)\", \"([a-zA-Z]+)\", \\{(.+)\\}>");
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				if (tempString.equals("")) {
					continue;
				}
				Pattern str = Pattern.compile("^\\w+\\b");
				Matcher matcher = str.matcher(tempString);
				String op = null;
				if (matcher.find()) {
					op = matcher.group(0);
				}
				switch (op) {
				case "GraphType":
					matcher = lGraphType.matcher(tempString);
					if (matcher.matches()) {

						String type = matcher.group(1);
						if (!type.equals("MovieGraph")) {
							System.out.println("invalid input1");
							System.exit(1);
						}
					} else {
						System.out.println("invalid input2");
						System.exit(1);
					}
					break;

				case "GraphName":
					matcher = lGraphName.matcher(tempString);
					if (matcher.matches()) {
						String gLabel = matcher.group(1);
						g.modifyL(gLabel);
					} else {
						System.out.println("invalid input3");
						System.exit(1);
					}
					break;

				case "VertexType":
					matcher = lVertexType.matcher(tempString);
					if (matcher.matches()) {
						matcher = p.matcher(tempString);
						while (matcher.find()) {
							VertexType.add(matcher.group(1));
						}
					} else {
						System.out.println("invalid input4");
						System.exit(1);
					}
					break;

				case "Vertex":
					matcher = lVertex.matcher(tempString);
					if (matcher.matches()) {
						String vLabel = matcher.group(1);
						String vType = matcher.group(2);
						if (!VertexType.contains(vType)) {
							System.out.println("invalid input5");
							System.exit(0);
						}
						String s = matcher.group(3);
						List<String> sl = new ArrayList<String>();
						matcher = p.matcher(s);
						while (matcher.find()) {
							sl.add(matcher.group(1));
						}
						String[] args = new String[sl.size()];
						sl.toArray(args);
						Vertex v = null;
						switch (vType){
						case "Director":
							v = new DirectorVertexFactory().createVertex(vLabel, args);
							break;
						case "Actor":
							v = new ActorVertexFactory().createVertex(vLabel, args);
							break;
						case "Movie":
							v = new MovieVertexFactory().createVertex(vLabel, args);
							break;
						}
						vertices.add(v);
					} else {
						System.out.println("invalid input6");
						System.exit(1);
					}
					break;

				case "EdgeType":
					matcher = lEdgeType.matcher(tempString);
					if (matcher.matches()) {
						matcher = p.matcher(tempString);
						while (matcher.find()) {
							EdgeType.add(matcher.group(1));
						}
					} else {
						System.out.println("invalid input7");
						System.exit(1);
					}
					break;

				case "Edge":
					matcher = lEdge.matcher(tempString);
					if (matcher.matches()) {
						String eLabel = matcher.group(1);
						String eType = matcher.group(2);
						String weight = matcher.group(3);
						String start = matcher.group(5);
						String end = matcher.group(6);
						String YN = matcher.group(7);
						if (!EdgeType.contains(eType)||!YN.equals("No")) {
							System.out.println("invalid input5");
							System.exit(0);
						}
						List<Vertex> se = new ArrayList<Vertex>();
						int num = 0;
						for (Vertex v : vertices) {
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
						Edge e = null;
						switch (eType) {
						case "MovieActorRelation":
							e = new MovieActorRelationFactory().createEdge(eLabel, se, Double.valueOf(weight));
							break;
						case "MovieDirectorRelation":
							e = new MovieDirectorRelationFactory().createEdge(eLabel, se, Double.valueOf(weight));
							break;
						}
						g.addEdge(e);
					} else {
						System.out.println("invalid input9");
						System.exit(1);
					}
					break;
				case "HyperEdge":
					matcher = lHyperEdge.matcher(tempString);
					if (matcher.matches()) {
						String heLabel = matcher.group(1);
						String heType = matcher.group(2);
						String vv = matcher.group(3);
						matcher = p.matcher(vv);
						List<String> he = new ArrayList<String>();
						while(matcher.find()) {
							he.add(matcher.group(1));
						}
						List<Vertex> se = new ArrayList<Vertex>();
						for(Vertex v : vertices) {
							if(he.contains(v.getLabel())) {
								se.add(v);
								he.remove(v.getLabel());
							}
						}
						Edge e = new SameMovieHyperEdgeFactory().createEdge(heLabel, se, -1);
						g.addEdge(e);
					}
					else {
						System.out.println("invalid input10");
						System.exit(1);
					}
				}

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return g;
	}

}
