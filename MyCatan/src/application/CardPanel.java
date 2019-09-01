package application;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

public class CardPanel {

	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private JTabbedPane cardpanel = new JTabbedPane();// panel of card that player has
	private JPanel resourcepanel = new JPanel();// resourcecardTab
	private JPanel devpanel = new JPanel();// developcardTab
	private JPanel facilitypanel = new JPanel();

	private JLabel[] resourcenum = { new JLabel("×" + 0), new JLabel("×" + 0), new JLabel("×" + 0), new JLabel("×" + 0),
			new JLabel("×" + 0) };
	private JLabel[] devnum = { new JLabel("×" + 0), new JLabel("×" + 0), new JLabel("×" + 0), new JLabel("×" + 0),
			new JLabel("×" + 0) };
	private JButton[] facilitybtn = { new JButton(" 街道  "), new JButton("開拓地"), new JButton("都市化"), new JButton(" 発展 "),
			new JButton("ターン終了") };
	private JButton[] devbtn = { new JButton(" 使う "), new JButton("使う"), new JButton("使う"), new JButton(" 使う "),
			new JButton("使う") };

	// playerの所持カード生成メソッド
	public void setCardPanel() {
		// panel of resourcecard
		this.resourcepanel.setBackground(Color.DARK_GRAY);
		SpringLayout splayout_r = new SpringLayout();
		resourcepanel.setLayout(splayout_r);

		// set resourcecard
		ImageIcon[] resourcecard = { new ImageIcon("./src/Resources/Images/Cards/tree.png"),
				new ImageIcon("./src/Resources/Images/Cards/brick.png"),
				new ImageIcon("./src/Resources/Images/Cards/wheat.png"),
				new ImageIcon("./src/Resources/Images/Cards/sheep.png"),
				new ImageIcon("./src/Resources/Images/Cards/iron.png") };

		JLabel[] resourcelabel = { new JLabel(resourcecard[0]), new JLabel(resourcecard[1]),
				new JLabel(resourcecard[2]), new JLabel(resourcecard[3]), new JLabel(resourcecard[4]) };

		// panel of developcard
		devpanel.setBackground(Color.DARK_GRAY);
		SpringLayout splayout_d = new SpringLayout();
		devpanel.setLayout(splayout_d);

		// set devcard
		ImageIcon[] devcard = { new ImageIcon("./src/Resources/Images/Cards/tree.png"),
				new ImageIcon("./src/Resources/Images/Cards/brick.png"),
				new ImageIcon("./src/Resources/Images/Cards/wheat.png"),
				new ImageIcon("./src/Resources/Images/Cards/sheep.png"),
				new ImageIcon("./src/Resources/Images/Cards/iron.png") };/* img完成後変更すること */

		JLabel[] devlabel = { new JLabel(devcard[0]), new JLabel(devcard[1]), new JLabel(devcard[2]),
				new JLabel(devcard[3]), new JLabel(devcard[4]) };

		facilitypanel.setBackground(Color.DARK_GRAY);
		SpringLayout splayout_f = new SpringLayout();
		facilitypanel.setLayout(splayout_f);

		ImageIcon[] facilityimg = { new ImageIcon("./src/Resources/Images/Cards/tree.png"),
				new ImageIcon("./src/Resources/Images/Cards/brick.png"),
				new ImageIcon("./src/Resources/Images/Cards/wheat.png"),
				new ImageIcon("./src/Resources/Images/Cards/sheep.png"),
				new ImageIcon("./src/Resources/Images/Cards/iron.png") };/* img完成後変更すること */

		JLabel[] facilitylabel = { new JLabel(facilityimg[1]), new JLabel(facilityimg[2]), new JLabel(facilityimg[3]),
				new JLabel(facilityimg[4]), new JLabel() };

		for (int i = 0; i < 5; i++) {
			// resourcelabel[i].setIcon(resourcecard[i]);
			splayout_r.putConstraint(SpringLayout.NORTH, resourcelabel[i], 50, SpringLayout.NORTH, resourcepanel);
			splayout_r.putConstraint(SpringLayout.WEST, resourcelabel[i], 60 + 140 * i, SpringLayout.WEST,
					resourcepanel);
			resourcepanel.add(resourcelabel[i]);

			devlabel[i].setIcon(devcard[i]);
			splayout_d.putConstraint(SpringLayout.NORTH, devlabel[i], 50, SpringLayout.NORTH, devpanel);
			splayout_d.putConstraint(SpringLayout.WEST, devlabel[i], 60 + 140 * i, SpringLayout.WEST, devpanel);
			devpanel.add(devlabel[i]);

			resourcenum[i].setFont(new Font(Font.SERIF, Font.ITALIC, 35));
			devnum[i].setFont(new Font(Font.SERIF, Font.ITALIC, 35));
			resourcenum[i].setForeground(Color.YELLOW);
			devnum[i].setForeground(Color.YELLOW);
			splayout_r.putConstraint(SpringLayout.NORTH, resourcenum[i], 240, SpringLayout.NORTH, resourcepanel);
			splayout_r.putConstraint(SpringLayout.WEST, resourcenum[i], 90 + 140 * i, SpringLayout.WEST, resourcepanel);
			splayout_d.putConstraint(SpringLayout.NORTH, devnum[i], 240, SpringLayout.NORTH, devpanel);
			splayout_d.putConstraint(SpringLayout.WEST, devnum[i], 90 + 140 * i, SpringLayout.WEST, devpanel);
			resourcepanel.add(resourcenum[i]);
			devpanel.add(devnum[i]);

			if (i != 4) {
				splayout_d.putConstraint(SpringLayout.NORTH, devbtn[i], 290, SpringLayout.NORTH, devpanel);
				splayout_d.putConstraint(SpringLayout.WEST, devbtn[i], 80 + 140 * i, SpringLayout.WEST, devpanel);
				devbtn[i].setEnabled(true);
				devpanel.add(devbtn[i]);
			}
			splayout_f.putConstraint(SpringLayout.NORTH, facilitylabel[i], 50, SpringLayout.NORTH, facilitypanel);
			splayout_f.putConstraint(SpringLayout.WEST, facilitylabel[i], 60 + 140 * i, SpringLayout.WEST,
					facilitypanel);
			facilitypanel.add(facilitylabel[i]);

			splayout_f.putConstraint(SpringLayout.NORTH, facilitybtn[i], 240, SpringLayout.NORTH, facilitypanel);
			splayout_f.putConstraint(SpringLayout.WEST, facilitybtn[i], 80 + 140 * i, SpringLayout.WEST, facilitypanel);
			facilitybtn[i].setEnabled(true);
			facilitypanel.add(facilitybtn[i]);
		}

		// Tabの設定
		cardpanel.addTab("      資 源      ", resourcepanel);
		cardpanel.addTab("      発 展      ", devpanel);
		cardpanel.addTab("      建設       ", facilitypanel);
		/* パネル完成後変更すること */
		cardpanel.setBackgroundAt(0, new Color(100, 155, 100));
		cardpanel.setBackgroundAt(1, new Color(205, 205, 100));
		cardpanel.setBackgroundAt(2, new Color(150, 200, 100));

		// TabbedPanel
		cardpanel.setBackground(new Color(100, 200, 255));
		cardpanel.setPreferredSize(new Dimension(screen.width * 800 / 1920, screen.height * 375 / 1080));
		cardpanel.setOpaque(true);
	}

	public JTabbedPane getTabPane() {
		return cardpanel;
	}

	public JPanel getResPanel() {
		return resourcepanel;
	}

	public JPanel getDevPanel() {
		return devpanel;
	}

	// reflesh number of cards
	public JLabel getResNum(int i) {
		return resourcenum[i];
	}

	public JLabel getDevNum(int i) {
		return devnum[i];
	}

	public JButton getDBtn(int num) {
		return devbtn[num];
	}

	public JButton getFBtn(int num) {
		return facilitybtn[num];
	}

	public void unableBtns() {
		for (int i = 0; i < 5; i++) {
			devbtn[i].setEnabled(false);
			facilitybtn[i].setEnabled(false);
		}
	}
	public void enableFBtns() {
		for (int i = 0; i < 5; i++) {
			facilitybtn[i].setEnabled(true);
		}
	}
	public void enableDBtns() {
		for (int i = 0; i < 5; i++) {
			devbtn[i].setEnabled(true);
		}
	}
	public void unableDBtns() {
		for (int i = 0; i < 5; i++) {
			devbtn[i].setEnabled(false);
		}
	}

}
