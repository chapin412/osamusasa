package application;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Settlement extends JLabel {
	private Dimension screen;
	private int poss;
	private int north, west;
	private String[][] iconimg = {
			{ "settlement_b.png", "settlement_bavailable.png", "settlement_bcity.png",
					"settlement_bcityavailable.png" },
			{ "settlement_g.png", "settlement_gavailable.png", "settlement_gcity.png",
					"settlement_gcityavailable.png" },
			{ "settlement_r.png", "settlement_ravailable.png", "settlement_rcity.png",
					"settlement_rcityavailable.png" },
			{ "settlement_y.png", "settlement_yavailable.png", "settlement_ycity.png",
					"settlement_ycityavailable.png" } };

	Settlement(int x, int y, int z) {
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		// -空の開拓地のiconをセット
		setIcon(new ImageIcon(""));
		// labelの位置
		north = (250 + x * 30 + y * 30 + z * 60) * screen.height / 1080;
		west = (400 + x * 60 + y * (-60)) * screen.width / 1920;
	}

	int getNorth() {
		return north;
	}

	int getWest() {
		return west;
	}

	public void setStatus(int num, int type, boolean repaint) {
		poss = num;
		if (num != 5) {
			ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource( iconimg[num - 1][type])));
			setIcon(resizeImage(icon));
		} else {
			setIcon(new ImageIcon(""));
		}
		if (repaint) {
			repaint();
		}
	}
	public int getPossesion() {
		return poss;
	}
	ImageIcon resizeImage(ImageIcon imageicon) {
		int width = imageicon.getIconWidth();
		int height = imageicon.getIconHeight();
		Image image = imageicon.getImage();
		Image newimg = image.getScaledInstance(width*screen.width/1920, height*screen.height/1080,  java.awt.Image.SCALE_SMOOTH);
		ImageIcon  icon = new ImageIcon(newimg);
		return icon;
	}
}
