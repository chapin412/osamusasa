package application;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

public class DialogFrame extends JFrame {
	private Dimension screen;
	private SpringLayout splayout = new SpringLayout();
	private JPanel panel = new JPanel(splayout);
	private final JLabel letterlabel1 = new JLabel("獲得したい資源を '１つ' 選んでください");
	private final JLabel letterlabel2 = new JLabel("獲得したい資源を '２つ' 選んでください");
	private JLabel letterlabel3 = new JLabel();
	private JLabel reslabel = new JLabel();
	private final JButton acceptbtn = new JButton("　 決定 　");
	private final JButton cancelbtn = new JButton("キャンセル");
	private JRadioButton[] radio = { new JRadioButton(""), new JRadioButton(""), new JRadioButton(""),
			new JRadioButton(""), new JRadioButton("") };
	private JSpinner[] spin = new JSpinner[5];
	private SpinnerNumberModel[] model = new SpinnerNumberModel[5];
	private SpinnerNumberModel[] negomodel = new SpinnerNumberModel[2];
	private JSpinner[][] negospin = new JSpinner[2][5];
	private int drop = 0;

	DialogFrame(String mode, String game, String name) {
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon icon = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(getClass().getResource("smallcards.png")));
		reslabel.setIcon(resizeImage(icon));
		Container base = this.getContentPane();
		if (mode.equals("dis")) {
			showFrame("Discover");
			setPanel(mode);
			base.add(panel);
		} else if (mode.equals("mono")) {
			showFrame("Monopoly");
			setPanel(mode);
			base.add(panel);
		}
	}

	DialogFrame(int[] cards, String game, String name, int num) {
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setTitle("所得税");
		this.setSize(new Dimension(450 * screen.width / 1920, 250 * screen.height / 1080));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageIcon icon = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(getClass().getResource("smallcards.png")));
		reslabel.setIcon(resizeImage(icon));
		int sum = 0;
		for (int i = 0; i < 5; i++) {
			sum = sum + cards[i];
			model[i] = new SpinnerNumberModel(0, 0, cards[i], 1);
			spin[i] = new JSpinner(model[i]);
			spin[i].setPreferredSize(new Dimension(30, 20));
		}
		drop = sum / 2 + sum % 2;
		letterlabel3.setText(String.valueOf(drop) + "枚捨てる資源を選んでください");
		letterlabel3.setFont((new Font(Font.SERIF, Font.BOLD, 20)));
		splayout.putConstraint(SpringLayout.NORTH, letterlabel3, 5 * screen.height / 1080, SpringLayout.NORTH, panel);
		splayout.putConstraint(SpringLayout.WEST, letterlabel3, 20 * screen.width / 1920, SpringLayout.WEST, panel);
		panel.add(letterlabel3);
		for (int i = 0; i < 5; i++) {
			splayout.putConstraint(SpringLayout.NORTH, spin[i], 110 * screen.height / 1080, SpringLayout.NORTH, panel);
			splayout.putConstraint(SpringLayout.WEST, spin[i], 85 + i * 60 * screen.width / 1920, SpringLayout.WEST,
					panel);
			panel.add(spin[i]);
		}
		splayout.putConstraint(SpringLayout.SOUTH, acceptbtn, -20 * screen.height / 1080, SpringLayout.SOUTH, panel);
		splayout.putConstraint(SpringLayout.WEST, acceptbtn, 185 * screen.width / 1920, SpringLayout.WEST, panel);
		panel.add(acceptbtn);
		this.getContentPane().add(panel);
	}

	DialogFrame(String target, String from, int[] restarget, int[] resfrom) {
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setTitle("取引");
		JLabel[] namelabel = { new JLabel(target), new JLabel(from) };
		JLabel label = new JLabel();
		ImageIcon icon = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(getClass().getResource("smallcards.png")));
		label.setIcon(resizeImage(icon));
		this.setSize(new Dimension(450 * screen.width / 1920, 250 * screen.height / 1080));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		for (int i = 0; i < 2; i++) {
			splayout.putConstraint(SpringLayout.NORTH, namelabel[i], 20 + i * 80, SpringLayout.NORTH, panel);
			splayout.putConstraint(SpringLayout.WEST, namelabel[i], 360, SpringLayout.WEST, panel);
			panel.add(namelabel[i]);
			for (int j = 0; j < 5; j++) {
				if (i == 0) {
					negomodel[i] = new SpinnerNumberModel(0, 0, restarget[j], 1);
				} else {
					negomodel[i] = new SpinnerNumberModel(0, 0, resfrom[j], 1);
				}
				negospin[i][j] = new JSpinner(negomodel[i]);
				splayout.putConstraint(SpringLayout.NORTH, negospin[i][j], 15 + i * 80, SpringLayout.NORTH, panel);
				splayout.putConstraint(SpringLayout.WEST, negospin[i][j], 30 + j * 60, SpringLayout.WEST, panel);
				panel.add(negospin[i][j]);
			}
		}
		splayout.putConstraint(SpringLayout.NORTH, label, 40, SpringLayout.NORTH, panel);
		splayout.putConstraint(SpringLayout.WEST, label, 20, SpringLayout.WEST, panel);
		panel.add(label);
		
		splayout.putConstraint(SpringLayout.NORTH, acceptbtn, 140, SpringLayout.NORTH, panel);
		splayout.putConstraint(SpringLayout.WEST, acceptbtn, 60, SpringLayout.WEST, panel);
		panel.add(acceptbtn);
		splayout.putConstraint(SpringLayout.NORTH, cancelbtn, 140, SpringLayout.NORTH, panel);
		splayout.putConstraint(SpringLayout.WEST, cancelbtn, 150, SpringLayout.WEST, panel);
		panel.add(cancelbtn);
		this.getContentPane().add(panel);
	}
	DialogFrame(String from, int[] restarget, int[] resfrom) {
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setTitle("取引");
		JLabel fromlabel = new JLabel(from+" からの取引です") ;
		JLabel[] namelabel = {new JLabel(from) , new JLabel("あなた")};
		JLabel label = new JLabel();
		JLabel[][] numlabel = {{new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel()},{new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel()}};
		ImageIcon icon = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(getClass().getResource("smallcards.png")));
		label.setIcon(resizeImage(icon));
		this.setSize(new Dimension(450 * screen.width / 1920, 250 * screen.height / 1080));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		for (int i = 0; i < 2; i++) {
			splayout.putConstraint(SpringLayout.NORTH, namelabel[i], 20 + i * 80, SpringLayout.NORTH, panel);
			splayout.putConstraint(SpringLayout.WEST, namelabel[i], 360, SpringLayout.WEST, panel);
			panel.add(namelabel[i]);
			for (int j = 0; j < 5; j++) {
				if(i==0) {
					numlabel[i][j].setText("×"+resfrom[j]);
				}else {
					numlabel[i][j].setText("×"+restarget[j]);
				}
				splayout.putConstraint(SpringLayout.NORTH, numlabel[i][j], 20 + i * 80, SpringLayout.NORTH, panel);
				splayout.putConstraint(SpringLayout.WEST, numlabel[i][j], 35 + j * 60, SpringLayout.WEST, panel);
				panel.add(numlabel[i][j]);
			}
		}
		splayout.putConstraint(SpringLayout.NORTH, fromlabel, 0, SpringLayout.NORTH, panel);
		splayout.putConstraint(SpringLayout.WEST, fromlabel, 20, SpringLayout.WEST, panel);
		panel.add(fromlabel);
		splayout.putConstraint(SpringLayout.NORTH, label, 40, SpringLayout.NORTH, panel);
		splayout.putConstraint(SpringLayout.WEST, label, 20, SpringLayout.WEST, panel);
		panel.add(label);
		acceptbtn.setText("承諾");
		splayout.putConstraint(SpringLayout.NORTH, acceptbtn, 140, SpringLayout.NORTH, panel);
		splayout.putConstraint(SpringLayout.WEST, acceptbtn, 60, SpringLayout.WEST, panel);
		panel.add(acceptbtn);
		cancelbtn.setText("拒否");
		splayout.putConstraint(SpringLayout.NORTH, cancelbtn, 140, SpringLayout.NORTH, panel);
		splayout.putConstraint(SpringLayout.WEST, cancelbtn, 150, SpringLayout.WEST, panel);
		panel.add(cancelbtn);
		this.getContentPane().add(panel);
	}

	public void showFrame(String mode) {
		this.setTitle(mode);
		this.setSize(new Dimension(450 * screen.width / 1920, 250 * screen.height / 1080));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void setPanel(String mode) {

		if (mode.equals("mono")) {
			letterlabel1.setFont((new Font(Font.SERIF, Font.BOLD, 20)));
			splayout.putConstraint(SpringLayout.NORTH, letterlabel1, 5 * screen.height / 1080, SpringLayout.NORTH,
					panel);
			splayout.putConstraint(SpringLayout.WEST, letterlabel1, 20 * screen.width / 1920, SpringLayout.WEST, panel);
			panel.add(letterlabel1);
		} else if (mode.equals("dis")) {
			letterlabel2.setFont((new Font(Font.SERIF, Font.BOLD, 20)));
			splayout.putConstraint(SpringLayout.NORTH, letterlabel2, 5 * screen.height / 1080, SpringLayout.NORTH,
					panel);
			splayout.putConstraint(SpringLayout.WEST, letterlabel2, 20 * screen.width / 1920, SpringLayout.WEST, panel);
			panel.add(letterlabel2);
		}
		splayout.putConstraint(SpringLayout.NORTH, reslabel, 50 * screen.height / 1080, SpringLayout.NORTH, panel);
		splayout.putConstraint(SpringLayout.WEST, reslabel, 70 * screen.width / 1920, SpringLayout.WEST, panel);
		panel.add(reslabel);
		splayout.putConstraint(SpringLayout.SOUTH, acceptbtn, -20 * screen.height / 1080, SpringLayout.SOUTH, panel);
		splayout.putConstraint(SpringLayout.WEST, acceptbtn, 85 * screen.width / 1920, SpringLayout.WEST, panel);
		panel.add(acceptbtn);
		splayout.putConstraint(SpringLayout.SOUTH, cancelbtn, -20 * screen.height / 1080, SpringLayout.SOUTH, panel);
		splayout.putConstraint(SpringLayout.WEST, cancelbtn, 245 * screen.width / 1920, SpringLayout.WEST, panel);
		panel.add(cancelbtn);
		for (int i = 0; i < 5; i++) {
			splayout.putConstraint(SpringLayout.NORTH, radio[i], 110 * screen.height / 1080, SpringLayout.NORTH, panel);
			splayout.putConstraint(SpringLayout.WEST, radio[i], 85 + i * 60 * screen.width / 1920, SpringLayout.WEST,
					panel);
			panel.add(radio[i]);
		}
	}

	public int getSelectedDis() {
		int count = 0;
		for (int i = 0; i < 5; i++) {
			if (radio[i].isSelected()) {
				count += 1;
			}
		}
		return count;
	}

	public int[] getDropped() {
		int[] cards = { 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < 5; i++) {
			cards[i] = Integer.parseInt(spin[i].getValue().toString());
			cards[5] = cards[5] + cards[i];
		}

		return cards;
	}

	public String getSelectedMono() {
		String result = null;
		int kind = -1;
		int count = 0;
		for (int i = 0; i < 5; i++) {
			if (radio[i].isSelected()) {
				count += 1;
				kind = i;
			}
		}
		if (count != 1) {
			result = "error";
		} else {
			switch (kind) {
			case 0:
				result = "tree";
				break;
			case 1:
				result = "brick";
				break;
			case 2:
				result = "wheat";
				break;
			case 3:
				result = "sheep";
				break;
			case 4:
				result = "iron";
				break;
			}
		}
		return result;
	}

	public JButton getAcceptBtn() {
		return acceptbtn;
	}

	public JButton getCancelBtn() {
		return cancelbtn;
	}

	public JRadioButton getRadioBtn(int index) {
		return radio[index];
	}

	public JLabel getLetter1() {
		return letterlabel1;
	}

	public JLabel getLetter2() {
		return letterlabel2;
	}

	public JLabel getLetter3() {
		return letterlabel3;
	}

	public JPanel getPanel() {
		return panel;
	}

	public JSpinner getNegoSpin(int index1,int index2) {
		return negospin[index1][index2];
	}
	public int getDropNum() {
		return drop;
	}

	private ImageIcon resizeImage(ImageIcon imageicon) {
		int width = imageicon.getIconWidth();
		int height = imageicon.getIconHeight();
		Image image = imageicon.getImage();
		Image newimg = image.getScaledInstance(width * screen.width / 1920, height * screen.height / 1080,
				Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(newimg);
		return icon;
	}
}
