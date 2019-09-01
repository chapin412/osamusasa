package application;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.SpringLayout;
import java.util.Random;

public class FieldPanel {

	private SpringLayout springlayout_f = new SpringLayout();
	private SpringLayout springlayout_s = new SpringLayout();
	private SpringLayout springlayout_n = new SpringLayout();
	private SpringLayout springlayout_r = new SpringLayout();
	private JPanel fieldpanel = new JPanel(springlayout_f);
	private JPanel stlmpanel = new JPanel(springlayout_s);
	private JPanel roadpanel = new JPanel(springlayout_r);
	private JPanel numberpanel = new JPanel(springlayout_n);
	private JLayeredPane pane = new JLayeredPane();
	private int w = 900, h = 700;
	private Dimension screen,dimension;
	private Settlement[] settlements = new Settlement[54];
	private Road[] roads = new Road[72];
	// tree,wheat,sheep4枚 brick,iron3枚 desert1枚
	private int[] kinds = { 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 6 };
	private int[] numbers = { 2, 3, 3, 4, 4, 5, 5, 6, 6, 49, 8, 8, 9, 9, 10, 10, 11, 11, 12 };
	private final int[][] xyz = { { -2, 0, -2 }, { -1, -1, -2 }, { 0, -2, -2 }, // 0-2
			{ -2, 1, -2 }, { -1, 0, -2 }, { 0, -1, -2 }, { 1, -2, -2 }, // 3-6
			{ -2, 1, -1 }, { -1, 0, -1 }, { 0, -1, -1 }, { 1, -2, -1 }, // 7-10
			{ -2, 2, -1 }, { -1, 1, -1 }, { 0, 0, -1 }, { 1, -1, -1 }, { 2, -2, -1 }, // 11-15
			{ -2, 2, 0 }, { -1, 1, 0 }, { 0, 0, 0 }, { 1, -1, 0 }, { 2, -2, 0 }, // 16-20
			{ -2, 3, 0 }, { -1, 2, 0 }, { 0, 1, 0 }, { 1, 0, 0 }, { 2, -1, 0 }, { 3, -2, 0 }, // 21-26
			{ -2, 3, 1 }, { -1, 2, 1 }, { 0, 1, 1 }, { 1, 0, 1 }, { 2, -1, 1 }, { 3, -2, 1 }, // 27-32
			{ -1, 3, 1 }, { 0, 2, 1 }, { 1, 1, 1 }, { 2, 0, 1 }, { 3, -1, 1 }, // 33-37
			{ -1, 3, 2 }, { 0, 2, 2 }, { 1, 1, 2 }, { 2, 0, 2 }, { 3, -1, 2 }, // 38-42
			{ 0, 3, 2 }, { 1, 2, 2 }, { 2, 1, 2 }, { 3, 0, 2 }, // 43-46
			{ 0, 3, 3 }, { 1, 2, 3 }, { 2, 1, 3 }, { 3, 0, 3 }, // 47-50
			{ 1, 3, 3 }, { 2, 2, 3 }, { 3, 1, 3 } };// 51-53
	private final int[][] vector = { { 3, 0 }, { 0, 4 }, { 4, 1 }, { 1, 5 }, { 5, 2 }, { 2, 6 }, { 3, 7 }, { 4, 8 },
			{ 5, 9 }, { 6, 10 }, { 11, 7 }, { 7, 12 }, { 12, 8 }, { 8, 13 }, { 13, 9 }, { 9, 14 }, { 14, 10 },
			{ 10, 15 }, { 11, 16 }, { 12, 17 }, { 13, 18 }, { 14, 19 }, { 15, 20 }, { 21, 16 }, { 16, 22 }, { 22, 17 },
			{ 17, 23 }, { 23, 18 }, { 18, 24 }, { 24, 19 }, { 19, 25 }, { 25, 20 }, { 20, 26 }, { 21, 27 }, { 22, 28 },
			{ 23, 29 }, { 24, 30 }, { 25, 31 }, { 26, 32 }, { 27, 33 }, { 33, 28 }, { 28, 34 }, { 34, 29 }, { 29, 35 },
			{ 35, 30 }, { 30, 36 }, { 36, 31 }, { 31, 37 }, { 37, 32 }, { 33, 38 }, { 34, 39 }, { 35, 40 }, { 36, 41 },
			{ 37, 42 }, { 38, 43 }, { 43, 39 }, { 39, 44 }, { 44, 40 }, { 40, 45 }, { 45, 41 }, { 41, 46 }, { 46, 42 },
			{ 43, 47 }, { 44, 48 }, { 45, 49 }, { 46, 50 }, { 47, 51 }, { 51, 48 }, { 48, 52 }, { 52, 49 }, { 49, 53 },
			{ 53, 50 } };
	private final int[][] roadxy = { { 90, 240 }, { 90, 300 }, { 90, 360 }, { 90, 420 }, { 90, 480 }, { 90, 540 },
			{ 120, 225 }, { 120, 345 }, { 120, 465 }, { 120, 585 }, { 180, 180 }, { 180, 240 }, { 180, 300 },
			{ 180, 360 }, { 180, 420 }, { 180, 480 }, { 180, 540 }, { 180, 600 }, { 210, 165 }, { 210, 285 },
			{ 210, 405 }, { 210, 525 }, { 210, 645 }, { 270, 120 }, { 270, 180 }, { 270, 240 }, { 270, 300 },
			{ 270, 360 }, { 270, 420 }, { 270, 480 }, { 270, 540 }, { 270, 600 }, { 270, 660 }, { 300, 105 },
			{ 300, 225 }, { 300, 345 }, { 300, 465 }, { 300, 585 }, { 300, 705 }, { 360, 120 }, { 360, 180 },
			{ 360, 240 }, { 360, 300 }, { 360, 360 }, { 360, 420 }, { 360, 480 }, { 360, 540 }, { 360, 600 },
			{ 360, 660 }, { 390, 165 }, { 390, 285 }, { 390, 405 }, { 390, 525 }, { 390, 645 }, { 450, 180 },
			{ 450, 240 }, { 450, 300 }, { 450, 360 }, { 450, 420 }, { 450, 480 }, { 450, 540 }, { 450, 600 },
			{ 480, 225 }, { 480, 345 }, { 480, 465 }, { 480, 585 }, { 540, 240 }, { 540, 300 }, { 540, 360 },
			{ 540, 420 }, { 540, 480 }, { 540, 540 } };
	private final int[] locate = { 0, 1, 0, 1, 0, 1, 2, 2, 2, 2, 0, 1, 0, 1, 0, 1, 0, 1, 2, 2, 2, 2, 2, 0, 1, 0, 1, 0,
			1, 0, 1, 0, 1, 2, 2, 2, 2, 2, 2, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 2, 2, 2, 2, 1, 0, 1, 0, 1, 0, 1, 0, 2, 2,
			2, 2, 1, 0, 1, 0, 1, 0 };
	// default map
	Tile[] tile = { new Tile(0), new Tile(15), new Tile(0), new Tile(9), new Tile(14), new Tile(1), new Tile(4),
			new Tile(4), new Tile(0), new Tile(0), new Tile(3), new Tile(5), new Tile(3), new Tile(1), new Tile(13),
			new Tile(10), new Tile(1), new Tile(2), new Tile(6), new Tile(5), new Tile(3), new Tile(0), new Tile(0),
			new Tile(3), new Tile(5), new Tile(1), new Tile(4), new Tile(8), new Tile(11), new Tile(2), new Tile(4),
			new Tile(2), new Tile(0), new Tile(0), new Tile(12), new Tile(0), new Tile(7) };
	Communication conn = new Communication();
	FieldPanel() {
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		dimension = new Dimension(screen.width*w/1920,screen.height*h/1080);
		pane.setPreferredSize(dimension);
		pane.setOpaque(false);
	}

	public void arrangeTiles(boolean random) {
		// randomにtileを並べ替える
		if (random == true) {
			int index1, index2, num1, num2;
			Random rnd = new Random();

			// shuffling numbers array
			for (int i = 0; i < kinds.length; i++) {
				index1 = rnd.nextInt(i + 1);
				num1 = kinds[i];
				kinds[i] = kinds[index1];
				kinds[index1] = num1;

				index2 = rnd.nextInt(i + 1);
				num2 = numbers[i];
				numbers[i] = numbers[index2];
				numbers[index2] = num2;
			}
			int index = 0;
			for (int i = 5; i < 8; i++) {
				tile[i].setTileKind(kinds[index]);
				tile[i].setTileNum(numbers[index]);
				tile[36 - i].setTileKind(kinds[18 - index]);
				tile[36 - i].setTileNum(numbers[18 - index]);
				index++;
			}
			for (int i = 10; i < 14; i++) {
				tile[i].setTileKind(kinds[index]);
				tile[i].setTileNum(numbers[index]);
				tile[36 - i].setTileKind(kinds[18 - index]);
				tile[36 - i].setTileNum(numbers[18 - index]);
				index++;
			}
			for (int i = 16; i < 21; i++) {
				tile[i].setTileKind(kinds[index]);
				tile[i].setTileNum(numbers[index]);
				index++;
			}

			int a = -1;
			int b = -1;

			for (int i = 0; i < 37; i++) {
				if (tile[i].getTileNum() == 49) {
					a = i;
				}
				if ((tile[i].getTileKind() == 6) && (tile[i].getTileNum() != 49)) {
					b = i;
				}
			}
			if (b != -1) {
				tile[a].setTileNum(tile[b].getTileNum());
				tile[b].setTileNum(49);
			}
		}
	}
	public void fetchTiles(String game) {
		String json = conn.fetchTiles(game);
		for(int i=0;i<37;i++) {
			tile[i].setTileKind(conn.readJsonToInt(json, 3*i+1));
			tile[i].setTileNum(conn.readJsonToInt(json, 3*i+2));
		}
	}
	public void setTiles() {
		// tileをpanelに配置
		for (int i = 0; i < 4; i++) {
			springlayout_f.putConstraint(SpringLayout.NORTH, tile[i], 0*screen.height/1080, SpringLayout.NORTH, fieldpanel);
			springlayout_f.putConstraint(SpringLayout.WEST, tile[i], (180 + i * 120)*screen.width/1920, SpringLayout.WEST, fieldpanel);
			fieldpanel.add(tile[i]);
			springlayout_f.putConstraint(SpringLayout.NORTH, tile[33 + i], 540*screen.height/1080, SpringLayout.NORTH, fieldpanel);
			springlayout_f.putConstraint(SpringLayout.WEST, tile[33 + i], (180 + i * 120)*screen.width/1920, SpringLayout.WEST, fieldpanel);
			fieldpanel.add(tile[33 + i]);
		}
		for (int i = 4; i < 9; i++) {
			springlayout_f.putConstraint(SpringLayout.NORTH, tile[i], 90*screen.height/1080, SpringLayout.NORTH, fieldpanel);
			springlayout_f.putConstraint(SpringLayout.WEST, tile[i], (120 + (i - 4) * 120)*screen.width/1920, SpringLayout.WEST,
					fieldpanel);
			fieldpanel.add(tile[i]);
			springlayout_f.putConstraint(SpringLayout.NORTH, tile[28 + i - 4], 450*screen.height/1080, SpringLayout.NORTH, fieldpanel);
			springlayout_f.putConstraint(SpringLayout.WEST, tile[28 + i - 4], (120 + (i - 4) * 120)*screen.width/1920, SpringLayout.WEST,
					fieldpanel);
			fieldpanel.add(tile[28 + i - 4]);
		}
		for (int i = 9; i < 15; i++) {
			springlayout_f.putConstraint(SpringLayout.NORTH, tile[i], 180*screen.height/1080, SpringLayout.NORTH, fieldpanel);
			springlayout_f.putConstraint(SpringLayout.WEST, tile[i], (60 + (i - 9) * 120)*screen.width/1920, SpringLayout.WEST, fieldpanel);
			fieldpanel.add(tile[i]);
			springlayout_f.putConstraint(SpringLayout.NORTH, tile[22 + i - 9], 360*screen.height/1080, SpringLayout.NORTH, fieldpanel);
			springlayout_f.putConstraint(SpringLayout.WEST, tile[22 + i - 9], (60 + (i - 9) * 120)*screen.width/1920, SpringLayout.WEST,
					fieldpanel);
			fieldpanel.add(tile[22 + i - 9]);
		}
		for (int i = 15; i < 22; i++) {
			springlayout_f.putConstraint(SpringLayout.NORTH, tile[i], 270*screen.height/1080, SpringLayout.NORTH, fieldpanel);
			springlayout_f.putConstraint(SpringLayout.WEST, tile[i], ((i - 15) * 120)*screen.width/1920, SpringLayout.WEST, fieldpanel);
			fieldpanel.add(tile[i]);
		}
		// panelの設定
		fieldpanel.setSize(dimension);
		fieldpanel.setOpaque(false);
		pane.add(fieldpanel);
		pane.setLayer(fieldpanel, 0);
		numberpanel.setSize(dimension);
		numberpanel.setOpaque(false);
		pane.add(numberpanel);
		pane.setLayer(numberpanel, 1);
	}

	public void setSettlements() {
		for (int i = 0; i < 54; i++) {
			settlements[i] = new Settlement(xyz[i][0], xyz[i][1], xyz[i][2]);
			springlayout_s.putConstraint(SpringLayout.NORTH, settlements[i], settlements[i].getNorth(),
					SpringLayout.NORTH, stlmpanel);
			springlayout_s.putConstraint(SpringLayout.WEST, settlements[i], settlements[i].getWest(), SpringLayout.WEST,
					stlmpanel);
			stlmpanel.add(settlements[i]);
		}
		stlmpanel.setSize(dimension);
		stlmpanel.setOpaque(false);
		pane.add(stlmpanel);
		pane.setLayer(stlmpanel, 2);
	}

	public void setRoads() {
		for (int i = 0; i < 72; i++) {
			roads[i] = new Road();
			springlayout_r.putConstraint(SpringLayout.NORTH, roads[i], roadxy[i][0]*screen.height/1080, SpringLayout.NORTH, roadpanel);
			springlayout_r.putConstraint(SpringLayout.WEST, roads[i], roadxy[i][1]*screen.width/1920, SpringLayout.WEST, roadpanel);
			roads[i].setStatus(6, locate[i], false);
			roadpanel.add(roads[i]);
		}
		roadpanel.setSize(dimension);
		roadpanel.setOpaque(false);
		roadpanel.repaint();
		pane.add(roadpanel);
		pane.setLayer(roadpanel, 1);
	}

	public JLayeredPane getPane() {
		return pane;
	}

	public JPanel getTilePanel() {
		return fieldpanel;
	}
	public JPanel getStlmPanel() {
		return stlmpanel;
	}
	
	public Tile getTile(int index) {
		return tile[index];
	}
	public Settlement getStlm(int index) {
		return settlements[index];
	}

	public Road getRoad(int index) {
		return roads[index];
	}

	public int getXYZ(int index1, int index2) {
		return xyz[index1][index2];
	}

	public int getVector(int index1, int index2) {
		return vector[index1][index2];
	}

	public JPanel getRoadPanel() {
		return roadpanel;
	}

	public int getRoadLocate(int index) {
		return locate[index];
	}

	ImageIcon resizeImage(ImageIcon imageicon) {
		int width = imageicon.getIconWidth();
		int height = imageicon.getIconHeight();
		Image image = imageicon.getImage();
		Image newimg = image.getScaledInstance(width*screen.width/1920, height*screen.height/1080,  Image.SCALE_SMOOTH);
		ImageIcon  icon = new ImageIcon(newimg);
		return icon;
	}
}