package app;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.apache.commons.collections15.Transformer;

import app.NetworkTopologyApp.graphMetrics;
import edge.CommentTie;
import edge.Edge;
import edge.ForwardTie;
import edge.FriendTie;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import factory.EdgeFactory.CommentTieFactory;
import factory.EdgeFactory.ForwardTieFactory;
import factory.EdgeFactory.FriendTieFactory;
import factory.GraphFactory.SocialNetworkFactory;
import factory.VertexFactory.PersonVertexFactory;
import graph.SocialNetwork;
import helper.GraphMetrics;
import vertex.Vertex;

public class SocialNetworkApp {
	static SocialNetwork G = new SocialNetwork();
	List<String> EdgeTypes = Arrays.asList("FriendTie", "ForwardTie", "CommentTie");

	public static void main(String[] args) {
		SocialNetworkApp f = new SocialNetworkApp();
		JFrame frame = new JFrame("SocialNetworkApp");
		frame.setBounds(100, 100, 1150, 850);
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
		JMenuItem graphMetrics = new JMenuItem("graphMetrics");
		menubar.add(menu);
		menu.add(generateGraph);
		menu.add(display);
		menu.add(addVertex);
		menu.add(addEdge);
		menu.add(deleteVertex);
		menu.add(deleteEdge);
		menu.add(modifyEdge);
		menu.add(graphMetrics);
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
					Vertex source = null;
					Vertex target = null;
					for (Vertex v : e.sourceVertices()) {
						source = v;
					}
					for (Vertex v : e.targetVertices()) {
						target = v;
					}
					g.addEdge(e, source, target, EdgeType.DIRECTED);
				}
				Layout<Vertex, Edge> layout = new CircleLayout<Vertex, Edge>(g);
				BasicVisualizationServer<Vertex, Edge> vv = new BasicVisualizationServer<Vertex, Edge>(layout);
				Transformer<Edge, String> ev = new Transformer<Edge, String>() {
					public String transform(Edge e) {
						return "                                            ------------" + e.getLabel() + ":"
								+ e.getWeight();
					}
				};
				Transformer<Edge, Paint> edgePaint = new Transformer<Edge, Paint>() {
					public Paint transform(Edge e) {
						if (e instanceof FriendTie)
							return Color.GREEN;
						else if (e instanceof ForwardTie)
							return Color.YELLOW;
						else if (e instanceof CommentTie) {
							return Color.BLUE;
						}
						return Color.BLACK;
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
				vv.getRenderContext().setVertexLabelTransformer(vt);
				vv.getRenderContext().setEdgeLabelTransformer(ev);
				vv.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
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
		graphMetrics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setContentPane(f.new graphMetrics());
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
						G = new SocialNetworkFactory().createGraph(path);
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
						if (!vType.equals("Person")) {
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
						Vertex v = new PersonVertexFactory().createVertex(vLabel, args);
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
						if (!EdgeTypes.contains(eType) || !YN.equals("Yes")) {
							JOptionPane.showMessageDialog(null, "Input invalid");
						}
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
							case "FriendTie":
								e = new FriendTieFactory().createEdge(eLabel, se, Double.valueOf(weight));
								break;
							case "ForwardTie":
								e = new ForwardTieFactory().createEdge(eLabel, se, Double.valueOf(weight));
								break;
							case "CommentTie":
								e = new CommentTieFactory().createEdge(eLabel, se, Double.valueOf(weight));
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
						if (!EdgeTypes.contains(eType) || !YN.equals("Yes")) {
							JOptionPane.showMessageDialog(null, "Input invalid");
						}
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
							case "FriendTie":
								e = new FriendTieFactory().createEdge(eLabel, se, Double.valueOf(weight));
								break;
							case "ForwardTie":
								e = new ForwardTieFactory().createEdge(eLabel, se, Double.valueOf(weight));
								break;
							case "CommentTie":
								e = new CommentTieFactory().createEdge(eLabel, se, Double.valueOf(weight));
								break;
							}
							G.addEdge(e);
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
	class graphMetrics extends JPanel {
		public graphMetrics() {
			setLayout(null);
			JLabel degreeCentrality1 = new JLabel();
			degreeCentrality1.setBounds(50, 0, 300, 80);
			degreeCentrality1.setFont(new Font("宋体", Font.PLAIN, 18));
			degreeCentrality1.setText("degreeCentrality:");
			JTextArea degreeCentrality1out = new JTextArea();
			degreeCentrality1out.setBounds(210, 20, 80, 40);
			degreeCentrality1out.setFont(new Font("宋体", Font.PLAIN, 18));
			double d = GraphMetrics.degreeCentrality(G);
			String str = null;
			if (d == 100000) {
				str = new String("无法到达");
			} else
				str = String.format("%.2f", d);
			degreeCentrality1out.setText(str);

			JLabel degreeCentrality2 = new JLabel();
			degreeCentrality2.setBounds(50, 80, 300, 80);
			degreeCentrality2.setFont(new Font("宋体", Font.PLAIN, 18));
			degreeCentrality2.setText("degreeCentrality of");
			JComboBox<Vertex> degreeCentrality2input = new JComboBox<Vertex>();
			degreeCentrality2input.setBounds(230, 100, 400, 40);
			degreeCentrality2input.setFont(new Font("宋体", Font.PLAIN, 18));
			for (Vertex v : G.vertices()) {
				degreeCentrality2input.addItem(v);
			}
			JTextArea degreeCentrality2out = new JTextArea();
			degreeCentrality2out.setBounds(650, 100, 80, 40);
			degreeCentrality2out.setFont(new Font("宋体", Font.PLAIN, 18));
			Vertex v = (Vertex) degreeCentrality2input.getSelectedItem();
			double d1 = GraphMetrics.degreeCentrality(G, v);
			String str1 = null;
			if (d1 == 100000) {
				str1 = new String("无法到达");
			} else
				str1 = String.format("%.2f", d1);
			degreeCentrality2out.setText(str1);
			degreeCentrality2input.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					Vertex v = (Vertex) degreeCentrality2input.getSelectedItem();
					double d1 = GraphMetrics.degreeCentrality(G, v);
					String str1 = null;
					if (d1 == 100000) {
						str1 = new String("无法到达");
					} else
						str1 = String.format("%.2f", d1);
					degreeCentrality2out.setText(str1);
				}
			});

			JLabel closenessCentrality = new JLabel();
			closenessCentrality.setBounds(50, 160, 300, 80);
			closenessCentrality.setFont(new Font("宋体", Font.PLAIN, 18));
			closenessCentrality.setText("closenessCentrality of");
			JComboBox<Vertex> closenessCentralityinput = new JComboBox<Vertex>();
			closenessCentralityinput.setBounds(250, 180, 400, 40);
			closenessCentralityinput.setFont(new Font("宋体", Font.PLAIN, 18));
			for (Vertex v1 : G.vertices()) {
				closenessCentralityinput.addItem(v1);
			}
			JTextArea closenessCentralityout = new JTextArea();
			closenessCentralityout.setBounds(670, 180, 80, 40);
			closenessCentralityout.setFont(new Font("宋体", Font.PLAIN, 18));
			Vertex v1 = (Vertex) closenessCentralityinput.getSelectedItem();
			double d11 = GraphMetrics.closenessCentrality(G, v1);
			String str11 = null;
			if (d11 == 100000) {
				str11 = new String("无法到达");
			} else
				str11 = String.format("%.2f", d11);
			closenessCentralityout.setText(str11);
			closenessCentralityinput.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					Vertex v1 = (Vertex) closenessCentralityinput.getSelectedItem();
					double d11 = GraphMetrics.closenessCentrality(G, v1);
					String str11 = null;
					if (d11 == 100000) {
						str11 = new String("无法到达");
					} else
						str11 = String.format("%.2f", d11);
					closenessCentralityout.setText(str11);

				}
			});

			JLabel betweennessCentrality = new JLabel();
			betweennessCentrality.setBounds(50, 240, 300, 80);
			betweennessCentrality.setFont(new Font("宋体", Font.PLAIN, 18));
			betweennessCentrality.setText("betweennessCentrality of");
			JComboBox<Vertex> betweennessCentralityinput = new JComboBox<Vertex>();
			betweennessCentralityinput.setBounds(280, 260, 400, 40);
			betweennessCentralityinput.setFont(new Font("宋体", Font.PLAIN, 18));
			for (Vertex v11 : G.vertices()) {
				betweennessCentralityinput.addItem(v11);
			}
			JTextArea betweennessCentralityout = new JTextArea();
			betweennessCentralityout.setBounds(700, 260, 80, 40);
			betweennessCentralityout.setFont(new Font("宋体", Font.PLAIN, 18));
			Vertex v11 = (Vertex) betweennessCentralityinput.getSelectedItem();
			double d111 = GraphMetrics.betweennessCentrality(G, v11);
			String str111 = null;
			if (d111 == 100000) {
				str111 = new String("无法到达");
			} else
				str111 = String.format("%.2f", d111);
			betweennessCentralityout.setText(str111);
			betweennessCentralityinput.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					Vertex v11 = (Vertex) betweennessCentralityinput.getSelectedItem();
					double d111 = GraphMetrics.betweennessCentrality(G, v11);
					String str111 = null;
					if (d111 == 100000) {
						str111 = new String("无法到达");
					} else
						str111 = String.format("%.2f", d111);
					betweennessCentralityout.setText(str111);
				}
			});

			JLabel inDegreeCentrality = new JLabel();
			inDegreeCentrality.setBounds(50, 320, 300, 80);
			inDegreeCentrality.setFont(new Font("宋体", Font.PLAIN, 18));
			inDegreeCentrality.setText("inDegreeCentrality of");
			JComboBox<Vertex> inDegreeCentralityinput = new JComboBox<Vertex>();
			inDegreeCentralityinput.setBounds(250, 340, 400, 40);
			inDegreeCentralityinput.setFont(new Font("宋体", Font.PLAIN, 18));
			for (Vertex v111 : G.vertices()) {
				inDegreeCentralityinput.addItem(v111);
			}
			JTextArea inDegreeCentralityout = new JTextArea();
			inDegreeCentralityout.setBounds(670, 340, 80, 40);
			inDegreeCentralityout.setFont(new Font("宋体", Font.PLAIN, 18));
			Vertex v111 = (Vertex) inDegreeCentralityinput.getSelectedItem();
			double d1111 = GraphMetrics.inDegreeCentrality(G, v111);
			String str1111 = null;
			if (d1111 == 100000) {
				str1111 = new String("无法到达");
			} else
				str1111 = String.format("%.2f", d1111);
			inDegreeCentralityout.setText(str1111);
			inDegreeCentralityinput.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					Vertex v111 = (Vertex) inDegreeCentralityinput.getSelectedItem();
					double d1111 = GraphMetrics.inDegreeCentrality(G, v111);
					String str1111 = null;
					if (d1111 == 100000) {
						str1111 = new String("无法到达");
					} else
						str1111 = String.format("%.2f", d1111);
					inDegreeCentralityout.setText(str1111);
				}
			});

			JLabel outDegreeCentrality = new JLabel();
			outDegreeCentrality.setBounds(50, 400, 300, 80);
			outDegreeCentrality.setFont(new Font("宋体", Font.PLAIN, 18));
			outDegreeCentrality.setText("outDegreeCentrality of");
			JComboBox<Vertex> outDegreeCentralityinput = new JComboBox<Vertex>();
			outDegreeCentralityinput.setBounds(260, 420, 400, 40);
			outDegreeCentralityinput.setFont(new Font("宋体", Font.PLAIN, 18));
			for (Vertex v1111 : G.vertices()) {
				outDegreeCentralityinput.addItem(v1111);
			}
			JTextArea outDegreeCentralityout = new JTextArea();
			outDegreeCentralityout.setBounds(680, 420, 80, 40);
			outDegreeCentralityout.setFont(new Font("宋体", Font.PLAIN, 18));
			Vertex v1111 = (Vertex) outDegreeCentralityinput.getSelectedItem();
			double d11111 = GraphMetrics.outDegreeCentrality(G, v1111);
			String str11111 = null;
			if (d11111 == 100000) {
				str11111 = new String("无法到达");
			} else
				str11111 = String.format("%.2f", d11111);
			outDegreeCentralityout.setText(str11111);
			outDegreeCentralityinput.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					Vertex v1111 = (Vertex) outDegreeCentralityinput.getSelectedItem();
					double d11111 = GraphMetrics.outDegreeCentrality(G, v1111);
					String str11111 = null;
					if (d11111 == 100000) {
						str11111 = new String("无法到达");
					} else
						str11111 = String.format("%.2f", d11111);
					outDegreeCentralityout.setText(str11111);
				}
			});

			JLabel distance = new JLabel();
			distance.setBounds(50, 480, 300, 80);
			distance.setFont(new Font("宋体", Font.PLAIN, 18));
			distance.setText("distance from");
			JComboBox<Vertex> distanceinput1 = new JComboBox<Vertex>();
			distanceinput1.setBounds(180, 500, 400, 40);
			distanceinput1.setFont(new Font("宋体", Font.PLAIN, 18));
			for (Vertex v11111 : G.vertices()) {
				distanceinput1.addItem(v11111);
			}
			JLabel to = new JLabel();
			to.setBounds(590, 480, 300, 80);
			to.setFont(new Font("宋体", Font.PLAIN, 18));
			to.setText("to");
			JComboBox<Vertex> distanceinput2 = new JComboBox<Vertex>();
			distanceinput2.setBounds(620, 500, 400, 40);
			distanceinput2.setFont(new Font("宋体", Font.PLAIN, 18));
			for (Vertex v11111 : G.vertices()) {
				distanceinput2.addItem(v11111);
			}

			JTextArea distanceout = new JTextArea();
			distanceout.setBounds(1040, 500, 80, 40);
			distanceout.setFont(new Font("宋体", Font.PLAIN, 18));
			Vertex v11111 = (Vertex) distanceinput1.getSelectedItem();
			Vertex v111111 = (Vertex) distanceinput2.getSelectedItem();
			double d111111 = GraphMetrics.distance(G, v11111, v111111);
			String str111111 = null;
			if (d111111 == 100000) {
				str111111 = new String("无法到达");
			} else
				str111111 = String.format("%.2f", d111111);
			distanceout.setText(str111111);
			distanceinput1.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					Vertex v11111 = (Vertex) distanceinput1.getSelectedItem();
					Vertex v111111 = (Vertex) distanceinput2.getSelectedItem();
					double d111111 = GraphMetrics.distance(G, v11111, v111111);
					String str111111 = null;
					if (d111111 == 100000) {
						str111111 = new String("无法到达");
					} else
						str111111 = String.format("%.2f", d111111);
					distanceout.setText(str111111);
				}
			});
			distanceinput2.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					Vertex v11111 = (Vertex) distanceinput1.getSelectedItem();
					Vertex v111111 = (Vertex) distanceinput2.getSelectedItem();
					double d111111 = GraphMetrics.distance(G, v11111, v111111);
					String str111111 = null;
					if (d111111 == 100000) {
						str111111 = new String("无法到达");
					} else
						str111111 = String.format("%.2f", d111111);
					distanceout.setText(str111111);
				}
			});

			JLabel eccentricity = new JLabel();
			eccentricity.setBounds(50, 560, 300, 80);
			eccentricity.setFont(new Font("宋体", Font.PLAIN, 18));
			eccentricity.setText("eccentricity:");
			JTextArea eccentricityout = new JTextArea();
			eccentricityout.setBounds(180, 580, 80, 40);
			eccentricityout.setFont(new Font("宋体", Font.PLAIN, 18));

			JLabel radius = new JLabel();
			radius.setBounds(50, 640, 300, 80);
			radius.setFont(new Font("宋体", Font.PLAIN, 18));
			radius.setText("radius:");
			JTextArea radiusout = new JTextArea();
			radiusout.setBounds(130, 660, 80, 40);
			radiusout.setFont(new Font("宋体", Font.PLAIN, 18));

			JLabel diameter = new JLabel();
			diameter.setBounds(50, 720, 300, 80);
			diameter.setFont(new Font("宋体", Font.PLAIN, 18));
			diameter.setText("diameter:");
			JTextArea diameterout = new JTextArea();
			diameterout.setBounds(150, 740, 80, 40);
			diameterout.setFont(new Font("宋体", Font.PLAIN, 18));

			add(degreeCentrality1);
			add(degreeCentrality2);
			add(closenessCentrality);
			add(betweennessCentrality);
			add(inDegreeCentrality);
			add(outDegreeCentrality);
			add(distance);
			add(eccentricity);
			add(radius);
			add(diameter);
			add(degreeCentrality2input);
			add(degreeCentrality2out);
			add(closenessCentralityinput);
			add(closenessCentralityout);
			add(betweennessCentralityinput);
			add(betweennessCentralityout);
			add(inDegreeCentralityinput);
			add(inDegreeCentralityout);
			add(outDegreeCentralityinput);
			add(outDegreeCentralityout);
			add(distanceinput1);
			add(to);
			add(distanceinput2);
			add(distanceout);
			add(eccentricityout);
			add(radiusout);
			add(diameterout);
			add(degreeCentrality1out);
			setVisible(true);
		}
	}
}
