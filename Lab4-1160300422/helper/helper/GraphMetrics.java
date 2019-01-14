package helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import edge.Edge;
import graph.Graph;
import vertex.Vertex;

public class GraphMetrics {
	private final static double maxnum = 100000;

	public static double degreeCentrality(Graph<Vertex, Edge> g, Vertex v) {
		if (!g.vertices().contains(v)) {
			return 0;
		}
		Collection<Edge> edges = g.edges();
		double degree = 0;
		for (Edge e : edges) {
			if (e.vertices().contains(v)) {
				degree++;
				if (e.vertices().size() == 1) {
					degree++;
				}
			}
		}
		return degree;
	}

	public static double degreeCentrality(Graph<Vertex, Edge> g) {
		Collection<Vertex> vertices = g.vertices();
		double V = vertices.size();
		double dm = 0;
		List<Double> d = new ArrayList<Double>();
		for (Vertex v : vertices) {
			double now = degreeCentrality(g, v);
			d.add(now);
			if (now > dm) {
				dm = now;
			}
		}
		double sum = 0;
		for (int i = 0; i < d.size(); i++) {
			sum += (dm - d.get(i));
		}
		return sum / (V * V - 3 * V + 2);
	}

	public static double closenessCentrality(Graph<Vertex, Edge> g, Vertex v) {
		Collection<Vertex> vertices = g.vertices();
		double sum = 0;
		double d;
		double dd;

		for (Vertex v2 : vertices) {
			if (v2.equals(v))
				continue;
			d = distance(g, v, v2);
			if (d == maxnum) {
				dd = 0;
			} else {
				dd = 1 / d;
			}
			sum += dd;
		}

		return sum;
	}

	public static double betweennessCentrality(Graph<Vertex, Edge> g, Vertex vv) {
		Collection<Vertex> vertices = g.vertices();
		
		double result = 0;
		for (Vertex start : vertices) {
			if (start.equals(vv)) {
				continue;
			}
			Map<Vertex, List<Vertex>> pre = new HashMap<Vertex, List<Vertex>>();
			Map<Vertex, Double> dis = new HashMap<Vertex, Double>();
			Map<Vertex, Boolean> finish = new HashMap<Vertex, Boolean>();
			for (Vertex v : vertices) {
				finish.put(v, false);
				double a = maxnum;
				if (g.targets(start).containsKey(v)) {
					List<Vertex> list = new ArrayList<Vertex>();
					list.add(start);
					pre.put(v, list);
					List<Double> l = g.targets(start).get(v);
					for (double now : l) {
						if (now < a)
							a = now;
					}
				}
				dis.put(v, a);
			}
			pre.put(start, new ArrayList<Vertex>());
			finish.put(start, true);
			dis.put(start, 0.0);
			for (int i = 2; i <= vertices.size(); i++) {
				Vertex u = start;
				double min = maxnum;
				for (Vertex v : vertices) {
					if (!finish.get(v) && dis.get(v) < min) {
						u = v;
						min = dis.get(v);
					}
				}
				finish.put(u, true);

				for (Vertex v2 : vertices) {
					double uv2 = maxnum;
					if (g.targets(u).containsKey(v2)) {

						List<Double> l = g.targets(u).get(v2);

						for (Double b : l) {
							if (b < uv2)
								uv2 = b;
						}
					}

					if (!finish.get(v2) && uv2 < maxnum) {
						if (dis.get(u) + uv2 < dis.get(v2)) {
							dis.put(v2, dis.get(u) + uv2);
							List<Vertex> list = new ArrayList<Vertex>();
							list.add(u);
							pre.put(v2, list);
						} else if (dis.get(u) + uv2 == dis.get(v2)) {
							List<Vertex> list = pre.get(v2);
							list.add(u);
							pre.put(v2, list);
						}
					}
				}
			}

			for (Vertex end : vertices) {
				double stv = 0;
				double st = 0;
				if (end.equals(vv) || end.equals(start) || dis.get(end) == maxnum) {
					continue;
				}
				boolean hasv = false;
				Map<Vertex, Integer> map = new HashMap<Vertex, Integer>();
				Stack<Vertex> s = new Stack<Vertex>();
				s.push(end);
				while (!s.empty()) {
					Vertex now = s.peek();
					List<Vertex> list = pre.get(now);
					if (!map.keySet().contains(now)) {
						map.put(now, list.size());
					}
					int i = map.get(now) - 1;
					if (i == -1) {
						Vertex o = s.pop();
						if (o.equals(start)) {
							st++;
							if (hasv) {
								stv++;
							}
						} else if (o.equals(vv)) {
							hasv = false;
						}
					} else {
						Vertex v = list.get(i);
						s.push(v);
						if (v.getLabel().equals(vv.getLabel())) {
							hasv = true;
						}
						map.put(now, i);
					}
				}
				if(st==0) {
					result+=0;
				}
				else{
					result+=stv/st;
				}
			}

		}
		
		return result;
	}

	public static double inDegreeCentrality(Graph<Vertex, Edge> g, Vertex v) {
		if (!g.vertices().contains(v)) {
			return 0;
		}
		Collection<Edge> edges = g.edges();
		double degree = 0;
		for (Edge e : edges) {
			if (e.targetVertices().contains(v)) {
				degree++;
			}
		}
		return degree;
	}

	public static double outDegreeCentrality(Graph<Vertex, Edge> g, Vertex v) {
		if (!g.vertices().contains(v)) {
			return 0;
		}
		Collection<Edge> edges = g.edges();
		double degree = 0;
		for (Edge e : edges) {
			if (e.sourceVertices().contains(v)) {
				degree++;
			}
		}
		return degree;
	}

	public static double distance(Graph<Vertex, Edge> g, Vertex start, Vertex end) {
		Collection<Vertex> vertices = g.vertices();
		Map<Vertex, Double> dis = new HashMap<Vertex, Double>();
		Map<Vertex, Vertex> pre = new HashMap<Vertex, Vertex>();
		Map<Vertex, Boolean> finish = new HashMap<Vertex, Boolean>();

		for (Vertex v : vertices) {
			finish.put(v, false);
			double a = maxnum;
			if (g.targets(start).containsKey(v)) {
				pre.put(v, start);
				List<Double> l = g.targets(start).get(v);
				for (double now : l) {
					if (now < a)
						a = now;
				}
			}
			dis.put(v, a);
		}
		finish.put(start, true);
		dis.put(start, 0.0);
		for (int i = 2; i <= vertices.size(); i++) {
			Vertex u = start;
			double min = maxnum;
			for (Vertex v : vertices) {
				if (!finish.get(v) && dis.get(v) < min) {
					u = v;
					min = dis.get(v);
				}
			}
			finish.put(u, true);

			for (Vertex v2 : vertices) {
				double uv2 = maxnum;
				if (g.targets(u).containsKey(v2)) {

					List<Double> l = g.targets(u).get(v2);

					for (Double b : l) {
						if (b < uv2)
							uv2 = b;
					}
				}
				if (!finish.get(v2) && uv2 < maxnum) {
					if (dis.get(u) + uv2 < dis.get(v2)) {
						dis.put(v2, dis.get(u) + uv2);
						pre.put(v2, u);
					}
				}
			}
		}
		return dis.get(end);
	}

}
