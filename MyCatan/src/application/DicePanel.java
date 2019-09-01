package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

class DicePanel{
	private SpringLayout sp = new SpringLayout();
	private JPanel panel = new JPanel(sp); 
	private int[] dice = new int[2];
	private JLabel[] label = { new JLabel(), new JLabel()};
	private JLabel turnlabel = new JLabel("あなたのターン");
	private JButton btn = new JButton("ROLL"); 
	private String[] path = { "Ldice1.png", "Ldice2.png", "Ldice3.png", "Ldice4.png", "Ldice5.png", "Ldice6.png" };
	private ImageIcon[] icon = new ImageIcon[6]; 
	private Dimension screen;

	DicePanel() {
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		panel.setPreferredSize(new Dimension(300 * screen.width / 1920, 220 * screen.height / 1080));
		for(int i=0;i<6;i++) {
		icon[i] = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource(path[i])));
		}
		for(int i=0;i<2;i++) {
			sp.putConstraint(SpringLayout.NORTH, label[i], 0, SpringLayout.NORTH, panel);
			sp.putConstraint(SpringLayout.WEST, label[i], 40+120*i, SpringLayout.WEST, panel);
			panel.add(label[i]);
		}
		sp.putConstraint(SpringLayout.NORTH, btn, 100, SpringLayout.NORTH, panel);
		sp.putConstraint(SpringLayout.WEST, btn, 110, SpringLayout.WEST, panel);
		panel.add(btn);
		btn.setEnabled(false);
		turnlabel.setFont(new Font(Font.SERIF,Font.BOLD,24));
		turnlabel.setForeground(Color.RED);
		turnlabel.setVisible(false);
		sp.putConstraint(SpringLayout.NORTH, turnlabel, 135, SpringLayout.NORTH, panel);
		sp.putConstraint(SpringLayout.WEST, turnlabel, 55, SpringLayout.WEST, panel);
		panel.add(turnlabel);
		panel.setOpaque(false);
	}
	
	public void rollDice() {
		dice[0] = new Random().nextInt(6) + 1;
		dice[1] = new Random().nextInt(6) + 1;
	}
	public JPanel getPanel() {
		return panel;
	}
	public int getDice(int num) {
		return dice[num];
	}

	public void setDiceLabel(int d1,int d2) {
		label[0].setIcon(resizeImage(icon[d1-1]));
		label[1].setIcon(resizeImage(icon[d2-1]));
		panel.repaint();
	}

	public JLabel getLabel(int num) {
		return label[num];
	}
	public JLabel getTurnLabel() {
		return turnlabel;
	}
	public JButton getBtn() { 
		return btn;
	}
	ImageIcon resizeImage(ImageIcon imageicon) {
		Image image = imageicon.getImage();
		Image newimg = image.getScaledInstance(85* screen.width / 1920, 85 * screen.height / 1080,
				Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(newimg);
		return icon;
	}
}
