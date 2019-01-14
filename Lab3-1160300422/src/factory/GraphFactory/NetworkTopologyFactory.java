package factory.GraphFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edge.Edge;
import factory.EdgeFactory.CommentTieFactory;
import factory.EdgeFactory.ForwardTieFactory;
import factory.EdgeFactory.FriendTieFactory;
import factory.EdgeFactory.NetworkConnectionFactory;
import factory.VertexFactory.ComputerVertexFactory;
import factory.VertexFactory.PersonVertexFactory;
import factory.VertexFactory.RouterVertexFactory;
import factory.VertexFactory.ServerVertexFactory;
import graph.Graph;
import graph.NetworkTopology;
import graph.SocialNetwork;
import vertex.Vertex;

public class NetworkTopologyFactory extends GraphFactory {

	@Override
	public NetworkTopology createGraph(String filePath) {
		NetworkTopology g = new NetworkTopology();
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
					if (matcher.find()) {

						String type = matcher.group(1);
						if (!type.equals("NetworkTopology")) {
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
					if (matcher.find()) {
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
						System.out.println("invalid input6");
						System.exit(1);
					}
					break;

				case "EdgeType":
					matcher = lEdgeType.matcher(tempString);
					if (matcher.find()) {
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
					if (matcher.find()) {
						String eLabel = matcher.group(1);
						String eType = matcher.group(2);
						String weight = matcher.group(3);
						String start = matcher.group(5);
						String end = matcher.group(6);
						String YN = matcher.group(7);
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
						Edge e = new NetworkConnectionFactory().createEdge(eLabel, se, Double.valueOf(weight));
						g.addEdge(e);
					} else {
						System.out.println("invalid input9");
						System.exit(1);
					}
					break;
				}

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return g;
	}

}
