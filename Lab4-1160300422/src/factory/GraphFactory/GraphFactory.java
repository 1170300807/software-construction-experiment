package factory.GraphFactory;

import graph.Graph;
import myException.graphExceptions;

public abstract class GraphFactory {
	public abstract Graph createGraph(String filePath) throws graphExceptions;
}
