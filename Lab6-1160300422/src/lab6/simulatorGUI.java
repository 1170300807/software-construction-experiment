package lab6;

import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class simulatorGUI {
	class log extends JPanel {
		public log() {
			setLayout(null);
			JTextArea ta = new JTextArea();
			ta.setEditable(false);
			StringBuffer sb = new StringBuffer();
			ta.setText(sb.toString());
			JScrollPane sp = new JScrollPane(ta);
			sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			sp.setBounds(50, 180, 800, 550);

			List<String> labels = Arrays.asList("n", "h", "t", "N", "k", "MV");
			JLabel[] label = new JLabel[6];
			JTextField[] text = new JTextField[6];
			for (int i = 0; i < 6; i++) {
				text[i] = new JTextField();
				text[i].setBounds(50 + 120 * i, 50, 100, 50);
				text[i].setFont(new Font("宋体", Font.PLAIN, 24));
				add(text[i]);
				label[i] = new JLabel();
				label[i].setBounds(90 + 120 * i, 0, 100, 50);
				label[i].setFont(new Font("宋体", Font.PLAIN, 18));
				label[i].setText(labels.get(i));
				add(label[i]);
			}
			JLabel Th = new JLabel();
			Th.setBounds(90, 110, 100, 50);
			Th.setFont(new Font("宋体", Font.PLAIN, 18));
			Th.setText("          Th:");
			JTextArea TThh = new JTextArea();
			TThh.setBounds(190, 110, 100, 50);
			TThh.setFont(new Font("宋体", Font.PLAIN, 18));
			add(Th);
			add(TThh);
			JLabel F = new JLabel();
			F.setBounds(400, 110, 100, 50);
			F.setFont(new Font("宋体", Font.PLAIN, 18));
			F.setText("          F:");
			JTextArea FF = new JTextArea();
			FF.setBounds(500, 110, 100, 50);
			FF.setFont(new Font("宋体", Font.PLAIN, 18));
			add(F);
			add(FF);
			Button simulate = new Button("simulate");
			simulate.setBounds(770, 100, 100, 50);
			simulate.setFont(new Font("宋体", Font.PLAIN, 18));
			add(simulate);

			simulate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					int[] init = new int[6];
					String input;
					boolean tag = true;
					for (int i = 0; i < 6; i++) {
						input = text[i].getText();
						try {
							init[i] = Integer.valueOf(input);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Input invalid");
							tag = false;
							break;
						}
					}
					if (tag) {
						simulator simu = new simulator();
						simu.simulate(init[0], init[1], init[2], init[3], init[4], init[5]);
						TThh.setText(String.format("%.2f", simu.Th()));
						FF.setText(String.format("%.2f", simu.F()));
						List<String> logs = creatLogFromText.Logs("log/monkeys.log");
						StringBuffer sb = new StringBuffer("time" + "\t" + "monkey" + "\t" + "ladder" + "\t"
								+ "direction" + "\t" + "position" + "\t" + "acrossTime" + "\n");
						
						for (String l : logs) {
							sb.append(l + "\n");
							//ta.setText(l+"\n");
						}
						ta.setText(sb.toString());
						
					}
				}
			});

			add(sp);
			setVisible(true);
		}
	}

	public static void main(String[] args) {
		simulatorGUI simu = new simulatorGUI();
		JFrame frame = new JFrame("monkeys simulator");
		frame.setBounds(100, 100, 900, 850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		log log = simu.new log();
		frame.add(log);

		frame.setVisible(true);
	}
}
