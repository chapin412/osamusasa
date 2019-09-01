package application;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Road extends JLabel {
	private Dimension screen;	
	final int[] locate = { 0, 1, 0, 1, 0, 1, 2, 2, 2, 2, 0, 1, 0, 1, 0, 1, 0, 1, 2, 2, 2, 2, 2, 0, 1, 0, 1, 0, 1, 0, 1,
			0, 1, 2, 2, 2, 2, 2, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 2, 2, 2, 2, 2, 1, 0, 1, 0, 1, 0, 1, 0, 2, 2, 2, 2,
			1, 0, 1, 0, 1, 0 };
	private String[][] iconimg = {{"road_bup.png","road_bdown.png","road_bver.png"},
												   {"road_gup.png","road_gdown.png","road_gver.png"},
												   {"road_rup.png","road_rdown.png","road_rver.png"},
												   {"road_yup.png","road_ydown.png","road_yver.png"},
												   {"road_aup.png","road_adown.png","road_aver.png"}};

Road(){
	screen = Toolkit.getDefaultToolkit().getScreenSize();
}
	public void setStatus(int num, int type, boolean repaint) {
		if(num==6) {
			setIcon(new ImageIcon(""));
		}else {
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource(iconimg[num-1][type])));
		setIcon(resizeImage(icon));
		}
		if (repaint) {
			repaint();
		}
	}

	public int getLacate(int index) {
		return locate[index];
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
