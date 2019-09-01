package application;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class Tile extends JLabel{
	private int kind;
	private int number = -1;
	private Dimension screen;
	//0:sea,1:tree,2:brick,3:wheat,4:sheep,5:iron,6:desert,7:leftup,8:left,9:leftd,10:right,11:tree2,12:brick2,13:wheet2,14:sheep2,15:iron2
	private String[] tileimg = {	"seatile.png","treetile.png","bricktile.png","wheattile.png","sheeptile.png","irontile.png","deserttile.png",
											"x3lu.png","x3l.png","x3ld.png","x3r.png","tx2.png","bx2.png","wx2.png","sx2.png","ix2.png"};

	private JLabel label = new JLabel();
	private String[] numimg = {"2.png","3.png","4.png","5.png","6.png","49.png","8.png","9.png","10.png","11.png","12.png","7.png"};
	Tile(int num){
		this.kind = num; //tileの種類　資源の供給時に使用
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource( tileimg[num])));
		this.setIcon(resizeImage(icon));
		label.setBounds(35*screen.width/1920,35*screen.height/1080,50*screen.width/1920,50*screen.height/1080);
		this.add(label);
	}
	void setTileKind(int num) {
		this.kind = num; //tileの種類　資源の供給時に使用
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource( tileimg[num])));
		this.setIcon(resizeImage(icon));
	}
	void setTileNum(int num) {
		this.number = num;
		if(num <= -1) {
			label.setIcon(new ImageIcon(""));
		}
		else if(num == 7){
			label.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource(numimg[5] ))));
		}
		else if (num >12) {
			label.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource(numimg[11] ))));
		}else {
			label.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource(numimg[num-2] ))));
		}
		this.repaint();
	}
	int getTileKind() {
		return kind;
	}
	int getTileNum() {
		return number;
	}
	public JLabel getNumLabel() {
		return label;
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