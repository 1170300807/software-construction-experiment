package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.collections15.Transformer;

import edge.Edge;
import edge.UndirectedEdge;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import factory.EdgeFactory.CommentTieFactory;
import factory.EdgeFactory.ForwardTieFactory;
import factory.EdgeFactory.FriendTieFactory;
import factory.EdgeFactory.MovieActorRelationFactory;
import factory.EdgeFactory.MovieDirectorRelationFactory;
import factory.EdgeFactory.NetworkConnectionFactory;
import factory.GraphFactory.MovieGraphFactory;
import factory.GraphFactory.NetworkTopologyFactory;
import factory.VertexFactory.ActorVertexFactory;
import factory.VertexFactory.ComputerVertexFactory;
import factory.VertexFactory.DirectorVertexFactory;
import factory.VertexFactory.MovieVertexFactory;
import factory.VertexFactory.RouterVertexFactory;
import factory.VertexFactory.ServerVertexFactory;
import graph.Graph;
import graph.MovieGraph;
import graph.NetworkTopology;
import vertex.Actor;
import vertex.Movie;
import vertex.Vertex;

public class MovieGraphApp {
	static MovieGraph G = new MovieGraph();
	List<String> VertexType = Arrays.asList("Actor", "Director", "Movie");
	List<String> EdgeTypes = Arrays.asList("MovieActorRelation","MovieActorRelation");
	public static void main(String[] args) {
		MovieGraphApp f = new MovieGraphApp();
		JFrame frame = new JFrame("MovieGraphApp");
		frame.setBounds(100, 100, 900, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);
		JMenu menu = new JMenu("options");
		JMenuItem generateGraph = new JMenuItem("generateGraph");
		JMenuItem addVertex = new JMenuItem("addVertex");
		JMenuItem addEdge = new JMenuItem("addEdge");
		JMenuItem deleteVertex = new JMenuItem("deleteVertex");
		JMenuItem deleteEdge = new JMenuItem("deleteEdge");
		JMenuItem modifyEdge = new JMenuItem("modifyEdge");
		JMenuItem display = new JMenuItem("display");
		menubar.add(menu);
		menu.add(generateGraph);
		menu.add(display);
		menu.add(addVertex);
		menu.add(addEdge);
		menu.add(deleteVertex);
		menu.add(deleteEdge);
		menu.add(modifyEdge);
		display.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (G == null) {
					return;
				}
				SparseMultigraph<Vertex, Edge> g = new SparseMultigraph<Vertex, Edge>();
				Set<Vertex> vertices = G.vertices();
				Set<Edge> edges = G.edges();
				for (Vertex v : vertices) {
					g.addVertex(v);
				}
				for (Edge e : edges) {
					if(e instanceof UndirectedEdge) {
						g.addEdge(e, e.vertices(), EdgeType.UNDIRECTED);
					}
				}
				Layout<Vertex, Edge> layout = new CircleLayout<Vertex, Edge>(g);
				BasicVisualizationServer<Vertex, Edge> vv = new BasicVisualizationServer<Vertex, Edge>(layout);
				Transformer<Edge, String> ev = new Transformer<Edge, String>() {
					public String transform(Edge e) {
						if (e != null) {
							return "                       ------------" + e.getLabel() + ":"
									+ e.getWeight();
						} else
							return "";
					}
				};
				Transformer<Vertex, String> vt = new Transformer<Vertex, String>() {
					public String transform(Vertex v) {
						if (v != null) {
							return v.toString();
						} else
							return "";
					}
				};
				Transformer<Vertex, Paint> vpaint = new Transformer<Vertex, Paint>() {
					public Paint transform(Vertex v) {
						if (v instanceof Movie) {
							return Color.YELLOW;
						} else if(v instanceof Actor) {
							return Color.BLUE;
						}else {
							return Color.RED;
						}
					}
				};
				
				DefaultVertexLabelRenderer vlr = new DefaultVertexLabelRenderer(Color.BLUE){
					public <Vertex> Component getVertexLabelRendererComponent(JComponent vv, Object value,
				            Font font, boolean isSelected, Vertex vertex) {
						super.getVertexLabelRendererComponent(vv, value, font, isSelected, vertex);
						if(vertex instanceof Movie) {
							this.setBorder(BorderFactory.createEtchedBorder());
							List<String> hyper = new ArrayList<String>();
							for(vertex.Vertex v : G.sources((vertex.Vertex) vertex).keySet()) {
								if(v instanceof Actor) {
									hyper.add(v.getLabel());
								}
							}
							setText("<html>  "+vertex.toString()+"<br><b>"+"SameMovieHyperEdge"+hyper.toString()+"</b></html>");
						}
						
						return this;
					}
				};
				vv.getRenderContext().setVertexLabelRenderer(vlr);
				
				vv.getRenderContext().setVertexLabelTransformer(vt);
				vv.getRenderContext().setEdgeLabelTransformer(ev);
				vv.getRenderContext().setVertexFillPaintTransformer(vpaint);
				
				Container a = (Container) f.new generateGraph().add(vv);
				frame.setContentPane(a);
				frame.setVisible(true);
			}
		});
		generateGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(f.new generateGraph());
				frame.setVisible(true);
			}
		});

		addVertex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(f.new addVertex());
				frame.setVisible(true);
			}
		});

		addEdge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(f.new addEdge());
				frame.setVisible(true);
			}
		});

		deleteVertex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(f.new deleteVertex());
				frame.setVisible(true);
			}
		});

		deleteEdge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(f.new deleteEdge());
				frame.setVisible(true);
			}
		});
		modifyEdge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(f.new modifyEdge());
				frame.setVisible(true);
			}
		});
		frame.setVisible(true);

	}

	class display extends JPanel {
		public display() {
			setVisible(true);
		}
	}

	class generateGraph extends JPanel {
		public generateGraph() {
			setLayout(null);
			JButton input = new JButton("Input Path");
			input.setFont(new Font("宋体", Font.PLAIN, 28));
			input.setBounds(300, 300, 200, 120);
			input.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fc = new JFileChooser();
					fc.setDialogTitle("请选择要打开的文件...");
					fc.setApproveButtonText("确定");
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(new JFrame())) {
						String path = null;
						path = fc.getSelectedFile().getPath();
						G = new MovieGraphFactory().createGraph(path);
					}
				}
			});
			add(input);
			setVisible(true);
		}
	}

	class addVertex extends JPanel {
		public addVertex() {
			setLayout(null);
			JLabel label = new JLabel();
			label.setBounds(130, 100, 700, 100);
			label.setFont(new Font("宋体", Font.PLAIN, 28));
			label.setText("Pattern: Vertex = <Label1,type1,<attr1,...,attrk>>");
			JTextField text = new JTextField();
			text.setBounds(80, 300, 700, 80);
			text.setFont(new Font("宋体", Font.PLAIN, 24));
			JButton addVertex = new JButton("addVertex");
			addVertex.setBounds(790, 320, 100, 50);
			addVertex.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String input = text.getText();
					Pattern lVertex = Pattern.compile("Vertex = <\"(\\w+)\", \"([a-zA-Z]+)\", <(.+)>>");
					Pattern p = Pattern.compile("\"([\\S\\s]+?)\"");
					Matcher matcher;
					matcher = lVertex.matcher(input);
					if (matcher.matches()) {
						String vLabel = matcher.group(1);
						String vType = matcher.group(2);
						if (!VertexType.contains(vType)) {
							JOptionPane.showMessageDialog(null, "Input invalid");
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
						switch (vType) {
						case "Actor":
							v = new ActorVertexFactory().createVertex(vLabel, args);
							break;
						case "Director":
							v = new DirectorVertexFactory().createVertex(vLabel, args);
							break;
						case "Movie":
							v = new MovieVertexFactory().createVertex(vLabel, args);
							break;
						}
						G.addVertex(v);
						text.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "Input invalid");
					}
				}
			});
			add(text);
			add(label);
			add(addVertex);
			setVisible(true);
		}
	}

	class addEdge extends JPanel {
		public addEdge() {
			setLayout(null);
			JLabel label = new JLabel();
			label.setBounds(50, 100, 1000, 100);
			label.setFont(new Font("宋体", Font.PLAIN, 24));
			label.setText("Pattern: Edge = <Label, type, Weight, StartVertex, EndVertex, Yes|No>");
			JTextField text = new JTextField();
			text.setBounds(50, 300, 700, 80);
			text.setFont(new Font("宋体", Font.PLAIN, 24));
			JButton addEdge = new JButton("addEdge");
			addEdge.setBounds(790, 320, 100, 50);
			addEdge.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					String input = text.getText();
					Pattern lEdge = Pattern.compile(
							"Edge = <\"(\\w+)\", \"([a-zA-Z]+)\", \"(-?[0-9]+(.[0-9]+)?)\", \"(\\w+)\", \"(\\w+)\", \"(Yes|No)\">");
					Matcher matcher = lEdge.matcher(input);
					if (matcher.find()) {
						String eLabel = matcher.group(1);
						String eType = matcher.group(2);
						String weight = matcher.group(3);
						String start = matcher.group(5);
						String end = matcher.group(6);
						String YN = matcher.group(7);
						List<Vertex> se = new ArrayList<Vertex>();
						int num = 0;
						for (Vertex v : G.vertices()) {
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
						if (se.size() < 2) {
							JOptionPane.showMessageDialog(null, "No enough vertices");
						} else {
							Edge e = null;
							switch (eType) {
							case "MovieActorRelation":
								e = new MovieActorRelationFactory().createEdge(eLabel, se, Double.valueOf(weight));
								break;
							case "MovieDirectorRelation":
								e = new MovieDirectorRelationFactory().createEdge(eLabel, se, Double.valueOf(weight));
								break;
							}
							G.addEdge(e);
							text.setText("");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Input invalid");
					}
				}
			});

			add(text);
			add(label);
			add(addEdge);
			setVisible(true);
		}
	}

	class deleteVertex extends JPanel {
		public deleteVertex() {
			setLayout(null);
			JLabel label = new JLabel();
			label.setBounds(300, 200, 500, 50);
			label.setFont(new Font("宋体", Font.PLAIN, 28));
			label.setText("choose the vertex");
			JComboBox<Vertex> box = new JComboBox<Vertex>();
			box.setBounds(200, 280, 500, 50);
			box.setFont(new Font("宋体", Font.PLAIN, 20));
			for (Vertex v : G.vertices()) {
				box.addItem(v);
			}
			JButton delete = new JButton("delete");
			delete.setBounds(750, 280, 100, 60);
			delete.setFont(new Font("宋体", Font.PLAIN, 20));
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					Vertex v = (Vertex) box.getSelectedItem();
					G.removeVertex(v);
					box.removeItem(v);
				}
			});
			add(delete);
			add(box);
			add(label);
			setVisible(true);
		}
	}

	class deleteEdge extends JPanel {
		public deleteEdge() {
			setLayout(null);
			JLabel label = new JLabel();
			label.setBounds(300, 200, 500, 50);
			label.setFont(new Font("宋体", Font.PLAIN, 28));
			label.setText("choose the edge");
			JComboBox<Edge> box = new JComboBox<Edge>();
			box.setBounds(200, 280, 500, 50);
			box.setFont(new Font("宋体", Font.PLAIN, 20));
			for (Edge e : G.edges()) {
				box.addItem(e);
			}
			JButton delete = new JButton("delete");
			delete.setBounds(750, 280, 100, 60);
			delete.setFont(new Font("宋体", Font.PLAIN, 20));
			delete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					Edge e = (Edge) box.getSelectedItem();
					G.removeEdge(e);
					box.removeItem(e);
				}
			});
			add(delete);
			add(box);
			add(label);
			setVisible(true);
		}
	}

	class modifyEdge extends JPanel {
		public modifyEdge() {
			setLayout(null);
			JLabel label = new JLabel();
			label.setBounds(50, 100, 1000, 100);
			label.setFont(new Font("宋体", Font.PLAIN, 24));
			label.setText("Pattern: Edge = <Label, type, Weight, StartVertex, EndVertex, Yes|No>");
			JTextField text = new JTextField();
			text.setBounds(50, 300, 700, 80);
			text.setFont(new Font("宋体", Font.PLAIN, 24));
			JButton modifyEdge = new JButton("modifyEdge");
			modifyEdge.setBounds(790, 320, 100, 50);
			modifyEdge.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					String input = text.getText();
					Pattern lEdge = Pattern.compile(
							"Edge = <\"(\\w+)\", \"([a-zA-Z]+)\", \"(-?[0-9]+(.[0-9]+)?)\", \"(\\w+)\", \"(\\w+)\", \"(Yes|No)\">");
					Matcher matcher = lEdge.matcher(input);
					if (matcher.find()) {
						String eLabel = matcher.group(1);
						String eType = matcher.group(2);
						String weight = matcher.group(3);
						String start = matcher.group(5);
						String end = matcher.group(6);
						String YN = matcher.group(7);
						List<Vertex> se = new ArrayList<Vertex>();
						int num = 0;
						for (Vertex v : G.vertices()) {
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
						if (se.size() < 2) {
							JOptionPane.showMessageDialog(null, "No enough vertices");
						} else {
							Edge e = null;
							switch (eType) {
							case "MovieActorRelation":
								e = new MovieActorRelationFactory().createEdge(eLabel, se, Double.valueOf(weight));
								break;
							case "MovieDirectorRelation":
								e = new MovieDirectorRelationFactory().createEdge(eLabel, se, Double.valueOf(weight));
								break;
							}
							G.addEdge(e);
							text.setText("");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Input invalid");
					}
				}
			});
			add(text);
			add(label);
			add(modifyEdge);
			setVisible(true);
		}
	}
	
}
