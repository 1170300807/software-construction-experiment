package factory.GraphFactory;

import java.util.List;

import graph.Graph;

public interface IOStrategy {
	public abstract List<String> IGraph(String filePath);

	public abstract void OGraph(String filePath, String s);
}
