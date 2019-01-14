package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.collections15.Transformer;

import app.GraphPoetApp.log;
import edge.Edge;
import edge.MovieActorRelation;
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
import factory.GraphFactory.IO1;
import factory.GraphFactory.IO2;
import factory.GraphFactory.IO3;
import factory.GraphFactory.IOStrategy;
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
import log.Log;
import log.creatLogFromText;
import myException.graphExceptions;
import vertex.Actor;
import vertex.Movie;
import vertex.Vertex;

public class MovieGraphApp {
	static MovieGraph G = new MovieGraph();
	IOStrategy strategy = new IO3();
	List<String> VertexType = Arrays.asList("Actor", "Director", "Movie");
	List<String> EdgeTypes = Arrays.asList("MovieActorRelation", "MovieActorRelation");

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
		JMenuItem log = new JMenuItem("log");
		JMenuItem save = new JMenuItem("save");
		menubar.add(menu);
		menu.add(generateGraph);
		menu.add(display);
		menu.add(addVertex);
		menu.add(addEdge);
		menu.add(deleteVertex);
		menu.add(deleteEdge);
		menu.add(modifyEdge);
		menu.add(log);
		menu.add(save);
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
					if (e instanceof UndirectedEdge) {
						g.addEdge(e, e.vertices(), EdgeType.UNDIRECTED);
					}
				}
				Layout<Vertex, Edge> layout = new CircleLayout<Vertex, Edge>(g);
				BasicVisualizationServer<Vertex, Edge> vv = new BasicVisualizationServer<Vertex, Edge>(layout);
				Transformer<Edge, String> ev = new Transformer<Edge, String>() {
					public String transform(Edge e) {
						if (e != null) {
							return "                       ------------" + e.getLabel() + ":" + e.getWeight();
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
						} else if (v instanceof Actor) {
							return Color.BLUE;
						} else {
							return Color.RED;
						}
					}
				};

				DefaultVertexLabelRenderer vlr = new DefaultVertexLabelRenderer(Color.BLUE) {
					public <Vertex> Component getVertexLabelRendererComponent(JComponent vv, Object value, Font font,
							boolean isSelected, Vertex vertex) {
						super.getVertexLabelRendererComponent(vv, value, font, isSelected, vertex);
						if (vertex instanceof Movie) {
							this.setBorder(BorderFactory.createEtchedBorder());
							List<String> hyper = new ArrayList<String>();
							for (vertex.Vertex v : G.sources((vertex.Vertex) vertex).keySet()) {
								if (v instanceof Actor) {
									hyper.add(v.getLabel());
								}
							}
							setText("<html>  " + vertex.toString() + "<br><b>" + "SameMovieHyperEdge" + hyper.toString()
									+ "</b></html>");
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

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(f.new save());
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
		log.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(f.new log());
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
						try {
							Instant t1 = Instant.now();
							G = new MovieGraphFactory().createGraph(strategy, path);
							Instant t2 = Instant.now();
							JOptionPane.showMessageDialog(null, t1.toString() + "-" + t2.toString());
						} catch (graphExceptions e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
							e1.printStackTrace();
						}
					}
				}
			});
			add(input);
			setVisible(true);
		}
	}

	class save extends JPanel {
		public save() {
			setLayout(null);
			JButton input = new JButton("Output Path");
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
						String s = G.toString();
						Instant t1 = Instant.now();
						strategy.OGraph(path, s);
						Instant t2 = Instant.now();
						System.out.println(t1.toString() + "-" + t2.toString());
						JOptionPane.showMessageDialog(null, t1.toString() + "-" + t2.toString());
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
							G.modifyEdge(e);
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

	class log extends JPanel {
		public log() {
			setLayout(null);
			JTextArea ta = new JTextArea();
			ta.setEditable(false);
			List<Log> logs = creatLogFromText.Logs("log/debug.log");
			StringBuffer sb = new StringBuffer();
			for (Log l : logs) {
				sb.append(l.message + "\n");
			}
			ta.setText(sb.toString());
			JScrollPane sp = new JScrollPane(ta);
			sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			sp.setBounds(50, 180, 800, 550);

			JComboBox<String> year = new JComboBox<String>();
			year.setBounds(50, 30, 100, 50);
			year.setFont(new Font("宋体", Font.PLAIN, 20));
			for (int i = 2018; i <= 2020; i++) {
				String s = null;
				if (i < 10) {
					s = new String("0" + i);
				} else {
					s = String.valueOf(i);
				}
				year.addItem(s);
			}
			JComboBox<String> month = new JComboBox<String>();
			month.setBounds(160, 30, 100, 50);
			month.setFont(new Font("宋体", Font.PLAIN, 20));
			for (int i = 1; i <= 12; i++) {
				String s = null;
				if (i < 10) {
					s = new String("0" + i);
				} else {
					s = String.valueOf(i);
				}
				month.addItem(s);
			}
			JComboBox<String> day = new JComboBox<String>();
			day.setBounds(270, 30, 100, 50);
			day.setFont(new Font("宋体", Font.PLAIN, 20));
			for (int i = 1; i <= 31; i++) {
				String s = null;
				if (i < 10) {
					s = new String("0" + i);
				} else {
					s = String.valueOf(i);
				}
				day.addItem(s);
			}
			JComboBox<String> hour = new JComboBox<String>();
			hour.setBounds(380, 30, 100, 50);
			hour.setFont(new Font("宋体", Font.PLAIN, 20));
			for (int i = 0; i <= 23; i++) {
				String s = null;
				if (i < 10) {
					s = new String("0" + i);
				} else {
					s = String.valueOf(i);
				}
				hour.addItem(s);
			}
			JComboBox<String> min = new JComboBox<String>();
			min.setBounds(490, 30, 100, 50);
			min.setFont(new Font("宋体", Font.PLAIN, 20));
			for (int i = 0; i <= 59; i++) {
				String s = null;
				if (i < 10) {
					s = new String("0" + i);
				} else {
					s = String.valueOf(i);
				}
				min.addItem(s);
			}
			JComboBox<String> sec = new JComboBox<String>();
			sec.setBounds(600, 30, 100, 50);
			sec.setFont(new Font("宋体", Font.PLAIN, 20));
			for (int i = 0; i <= 59; i++) {
				String s = null;
				if (i < 10) {
					s = new String("0" + i);
				} else {
					s = String.valueOf(i);
				}
				sec.addItem(s);
			}

			JComboBox<String> year1 = new JComboBox<String>();
			year1.setBounds(50, 100, 100, 50);
			year1.setFont(new Font("宋体", Font.PLAIN, 20));
			for (int i = 2018; i <= 2020; i++) {
				String s = null;
				if (i < 10) {
					s = new String("0" + i);
				} else {
					s = String.valueOf(i);
				}
				year1.addItem(s);
			}
			JComboBox<String> month1 = new JComboBox<String>();
			month1.setBounds(160, 100, 100, 50);
			month1.setFont(new Font("宋体", Font.PLAIN, 20));
			for (int i = 1; i <= 12; i++) {
				String s = null;
				if (i < 10) {
					s = new String("0" + i);
				} else {
					s = String.valueOf(i);
				}
				month1.addItem(s);
			}
			JComboBox<String> day1 = new JComboBox<String>();
			day1.setBounds(270, 100, 100, 50);
			day1.setFont(new Font("宋体", Font.PLAIN, 20));
			for (int i = 1; i <= 31; i++) {
				String s = null;
				if (i < 10) {
					s = new String("0" + i);
				} else {
					s = String.valueOf(i);
				}
				day1.addItem(s);
			}
			JComboBox<String> hour1 = new JComboBox<String>();
			hour1.setBounds(380, 100, 100, 50);
			hour1.setFont(new Font("宋体", Font.PLAIN, 20));
			for (int i = 0; i <= 23; i++) {
				String s = null;
				if (i < 10) {
					s = new String("0" + i);
				} else {
					s = String.valueOf(i);
				}
				hour1.addItem(s);
			}
			JComboBox<String> min1 = new JComboBox<String>();
			min1.setBounds(490, 100, 100, 50);
			min1.setFont(new Font("宋体", Font.PLAIN, 20));
			for (int i = 0; i <= 59; i++) {
				String s = null;
				if (i < 10) {
					s = new String("0" + i);
				} else {
					s = String.valueOf(i);
				}
				min1.addItem(s);
			}
			JComboBox<String> sec1 = new JComboBox<String>();
			sec1.setBounds(600, 100, 100, 50);
			sec1.setFont(new Font("宋体", Font.PLAIN, 20));
			for (int i = 0; i <= 59; i++) {
				String s = null;
				if (i < 10) {
					s = new String("0" + i);
				} else {
					s = String.valueOf(i);
				}
				sec1.addItem(s);
			}

			JButton search = new JButton("search");
			search.setBounds(720, 100, 100, 50);
			search.setFont(new Font("宋体", Font.PLAIN, 20));
			search.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					StringBuffer t1 = new StringBuffer();
					t1.append(year.getSelectedItem()).append("-");
					t1.append(month.getSelectedItem()).append("-");
					t1.append(day.getSelectedItem() + " ");
					t1.append(hour.getSelectedItem() + ":");
					t1.append(min.getSelectedItem() + ":");
					t1.append(sec.getSelectedItem());
					StringBuffer t11 = new StringBuffer();
					t11.append(year1.getSelectedItem()).append("-");
					t11.append(month1.getSelectedItem()).append("-");
					t11.append(day1.getSelectedItem() + " ");
					t11.append(hour1.getSelectedItem() + ":");
					t11.append(min1.getSelectedItem() + ":");
					t11.append(sec1.getSelectedItem());
					StringBuffer sb = new StringBuffer();
					for (Log l : logs) {
						if (l.after(t1.toString()) && l.before(t11.toString())) {
							sb.append(l.message + "\n");
						}

					}
					ta.setText(sb.toString());
				}
			});

			add(sp);
			add(year);
			add(month);
			add(day);
			add(hour);
			add(min);
			add(sec);
			add(year1);
			add(month1);
			add(day1);
			add(hour1);
			add(min1);
			add(sec1);
			add(search);
			setVisible(true);
		}
	}
}
