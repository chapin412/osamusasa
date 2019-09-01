package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import application.Player;

public class PlayerPanel {

	SpringLayout[] splayout_p = { new SpringLayout(), new SpringLayout(), new SpringLayout(), new SpringLayout() };
	int players;// from dbserver
	JPanel[] panel = { new JPanel(), new JPanel(), new JPanel(), new JPanel() };
	JLabel[] namelabel = { new JLabel(), new JLabel(), new JLabel(), new JLabel() };
	JLabel[] cardlabel = { new JLabel(), new JLabel(), new JLabel(), new JLabel() };
	JLabel[] knightlabel = { new JLabel(), new JLabel(), new JLabel(), new JLabel() };
	JLabel[] knightnumlabel = { new JLabel(), new JLabel(), new JLabel(), new JLabel() };
	JLabel[] roadlabel = { new JLabel(), new JLabel(), new JLabel(), new JLabel() };
	JLabel[] roadnumlabel = { new JLabel(), new JLabel(), new JLabel(), new JLabel() };
	JLabel[] devlabel = { new JLabel(), new JLabel(), new JLabel(), new JLabel() };
	JLabel[] devnumlabel = { new JLabel(), new JLabel(), new JLabel(), new JLabel() };
	String iconimg = "smallcards.png";
	ImageIcon[] knighticon = { new ImageIcon(), new ImageIcon() };
	ImageIcon[] roadicon = { new ImageIcon(), new ImageIcon() };
	ImageIcon devnumicon =  new ImageIcon();
	JLabel[][] cardnumlabel = { { new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel() },
			{ new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel() },
			{ new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel() },
			{ new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel() } };
	JButton[] btn = { new JButton("取引"), new JButton("取引"), new JButton("取引"), new JButton("取引") };
	Dimension screen;

	PlayerPanel(int num) {
		players = num;
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource(iconimg)));
		knighticon[0] = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(getClass().getResource("knightpower.png")));
		knighticon[1] = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource("maxknight.png")));
		roadicon[0] = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource("traderoad.png")));
		roadicon[1] = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource("longestroad.png")));
		devnumicon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource("devnum.png")));
		for (int i = 0; i < num; i++) {
			panel[i].setPreferredSize(new Dimension(300 * screen.width / 1920, 320 * screen.height / 1920));
			panel[i].setBackground(Color.WHITE);
			panel[i].setLayout(splayout_p[i]);
			panel[i].setOpaque(true);
			// 名前の表示
			splayout_p[i].putConstraint(SpringLayout.NORTH, namelabel[i], 0, SpringLayout.NORTH, panel[i]);
			splayout_p[i].putConstraint(SpringLayout.WEST, namelabel[i], 0, SpringLayout.WEST, panel[i]);
			namelabel[i].setFont(new Font(Font.SERIF, Font.ITALIC, 16));
			panel[i].add(namelabel[i]);
			// add取引ボタン
			splayout_p[i].putConstraint(SpringLayout.NORTH, btn[i], 1 * screen.height / 1080, SpringLayout.NORTH,
					panel[i]);
			splayout_p[i].putConstraint(SpringLayout.EAST, btn[i], -2 * screen.width / 1920, SpringLayout.EAST,
					panel[i]);
			btn[i].setFont(new Font(Font.SERIF, Font.ITALIC, 13));
			btn[i].setEnabled(true);
			panel[i].add(btn[i]);
			// add所持カードimg
			cardlabel[i].setIcon(this.resizeImage(icon));
			splayout_p[i].putConstraint(SpringLayout.NORTH, cardlabel[i], 40 * screen.height / 1080, SpringLayout.NORTH,
					panel[i]);
			splayout_p[i].putConstraint(SpringLayout.WEST, cardlabel[i], 5 * screen.width / 1920, SpringLayout.WEST,
					panel[i]);
			panel[i].add(cardlabel[i]);
			// addカード枚数
			for (int j = 0; j < 5; j++) {
				splayout_p[i].putConstraint(SpringLayout.NORTH, cardnumlabel[i][j], 90 * screen.height / 1080,
						SpringLayout.NORTH, panel[i]);
				splayout_p[i].putConstraint(SpringLayout.WEST, cardnumlabel[i][j], (20 + j * 60) * screen.width / 1920,
						SpringLayout.WEST, panel[i]);
				cardnumlabel[i][j].setFont(new Font(Font.SERIF, Font.ITALIC, 16));
				cardnumlabel[i][j].setForeground(Color.BLACK);
				panel[i].add(cardnumlabel[i][j]);
			}
			// 騎士力icon,
			knightlabel[i].setIcon(this.resizeImage(knighticon[0], 100, 24));
			splayout_p[i].putConstraint(SpringLayout.NORTH, knightlabel[i], 120 * screen.height / 1080, SpringLayout.NORTH,
					panel[i]);
			splayout_p[i].putConstraint(SpringLayout.WEST, knightlabel[i], 10 * screen.width / 1920, SpringLayout.WEST,
					panel[i]);
			panel[i].add(knightlabel[i]);
			//交易路icon
			roadlabel[i].setIcon(this.resizeImage(roadicon[0], 100, 24));
			splayout_p[i].putConstraint(SpringLayout.NORTH, roadlabel[i], 120 * screen.height / 1080, SpringLayout.NORTH,
					panel[i]);
			splayout_p[i].putConstraint(SpringLayout.WEST, roadlabel[i], 130 * screen.width / 1920, SpringLayout.WEST,
					panel[i]);
			panel[i].add(roadlabel[i]);
			//発展icon
			devlabel[i].setIcon(this.resizeImage(devnumicon, 50, 24));
			splayout_p[i].putConstraint(SpringLayout.NORTH, devlabel[i], 120 * screen.height / 1080, SpringLayout.NORTH,
					panel[i]);
			splayout_p[i].putConstraint(SpringLayout.WEST, devlabel[i], 245 * screen.width / 1920, SpringLayout.WEST,
					panel[i]);
			panel[i].add(devlabel[i]);
			//騎士num
			knightnumlabel[i].setText("×"+0);
			knightnumlabel[i].setFont(new Font(Font.SERIF, Font.ITALIC, 16));
			splayout_p[i].putConstraint(SpringLayout.NORTH, knightnumlabel[i], 145 * screen.height / 1080, SpringLayout.NORTH,
					panel[i]);
			splayout_p[i].putConstraint(SpringLayout.WEST, knightnumlabel[i], 50 * screen.width / 1920, SpringLayout.WEST,
					panel[i]);
			panel[i].add(knightnumlabel[i]);
			//交易路num
			roadnumlabel[i].setText("×"+0);
			roadnumlabel[i].setFont(new Font(Font.SERIF, Font.ITALIC, 16));
			splayout_p[i].putConstraint(SpringLayout.NORTH, roadnumlabel[i], 145 * screen.height / 1080, SpringLayout.NORTH,
					panel[i]);
			splayout_p[i].putConstraint(SpringLayout.WEST, roadnumlabel[i], 170 * screen.width / 1920, SpringLayout.WEST,
					panel[i]);
			panel[i].add(roadnumlabel[i]);
			//発展num
			devnumlabel[i].setText("×"+0);
			devnumlabel[i].setFont(new Font(Font.SERIF, Font.ITALIC, 16));
			splayout_p[i].putConstraint(SpringLayout.NORTH, devnumlabel[i], 145 * screen.height / 1080, SpringLayout.NORTH,
					panel[i]);
			splayout_p[i].putConstraint(SpringLayout.WEST, devnumlabel[i], 260 * screen.width / 1920, SpringLayout.WEST,
					panel[i]);
			panel[i].add(devnumlabel[i]);
		}
	}

	// get player's name and set on panel
	void setPlayerName(Player[] player) {
		for (int i = 0; i < players; i++) {
			namelabel[i].setText("　" + player[i].GetPlayerName());
			namelabel[i].setFont(new Font(Font.SERIF, Font.BOLD, 21));
			panel[i].repaint();
		}
	}

	// get player's res cards and set on panel
	void setPlayerRes(Player[] player) {
		int[] res = new int[5];
		for (int i = 0; i < players; i++) {
			res = player[i].getResCard();
			for (int j = 0; j < 5; j++) {
				cardnumlabel[i][j].setText("×" + res[j]);
			}
			panel[i].repaint();
		}
	}

	public void setDevNumLabel(Player player) {
		int sum = player.getSumDev();
		devnumlabel[player.getPlayerNum()-1].setText("×"+sum);
		panel[player.getPlayerNum()-1].repaint();
	}

	public JButton getNBtn(int index) {
		return btn[index];
	}

	public void enableBtns() {
		for (int i = 0; i < 4; i++) {
			btn[i].setEnabled(true);
		}
	}

	public void unableBtns() {
		for (int i = 0; i < 4; i++) {
			btn[i].setEnabled(false);
		}
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

	private ImageIcon resizeImage(ImageIcon imageicon, int width, int height) {
		Image image = imageicon.getImage();
		Image newimg = image.getScaledInstance(width * screen.width / 1920, height * screen.height / 1080,
				Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(newimg);
		return icon;
	}

	public void setKnightNumLabel(int[] knight) {
		for(int i=0;i<4;i++) {
			knightnumlabel[i].setText("×"+knight[i]);
			//roadのほうでrepaint
		}
	}

	public void setRoadNumLabel(int[] road) {
		for(int i=0;i<4;i++) {
			roadnumlabel[i].setText("×"+road[i]);
			panel[i].repaint();
		}
		
	}

	public void setMaxKnightlabel(int i, int j) {
		knightlabel[i].setIcon(resizeImage(knighticon[j],100,24));
		panel[i].repaint();
	}

	public void setLongestRoadLabel(int i, int j) {
		roadlabel[i].setIcon(resizeImage(roadicon[j],100,24));
		panel[i].repaint();
		
	}

	
}
