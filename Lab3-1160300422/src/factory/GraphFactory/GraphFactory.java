package factory.GraphFactory;

import graph.Graph;

public abstract class GraphFactory {
	public abstract Graph createGraph(String filePath);
}
