package application;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import application.GameFrame;

public class LobbyFrame extends JFrame {

	private JPanel[] panel = { new JPanel(new BorderLayout()), new JPanel(new BorderLayout()),
			new JPanel(new BorderLayout()), new JPanel(new BorderLayout()), new JPanel(new BorderLayout()) };
	private CardLayout cardlayout = new CardLayout();
	private GridLayout gridlayout = new GridLayout(12, 2, 15, 5);
	private JPanel loginp = new JPanel();
	private JPanel registerp = new JPanel();
	private JScrollPane[] scrollp = new JScrollPane[2];
	private JPanel createPanel = new JPanel(gridlayout);
	private JSplitPane[] splitp = { new JSplitPane(), new JSplitPane(), new JSplitPane(), new JSplitPane(),
			new JSplitPane() };
	private JLabel[] label = { new JLabel("Login"), new JLabel("Register 'Player Name'"), new JLabel("Lobby"),
			new JLabel("CREATE"), new JLabel("Game Lobby") };
	private JButton[] btn = { new JButton("login"), new JButton("register"), new JButton("'" + "create game" + "'"),
			new JButton("done"), new JButton("GameStart") };
	private JButton[] bbtn = { new JButton("Quit"), new JButton("Cancel"), new JButton("Update"), new JButton("Back"),
			new JButton("Leave") };
	private JLabel[] loginlabel = { new JLabel("id  "), new JLabel("password  ") };
	private JTextField[] logintext = { new JTextField(), new JTextField() };
	private JLabel registerlabel = new JLabel("PlayerName  ");
	private JTextField registertext = new JTextField();
	private String[][] values1 = { { "", "", "", "" }, { "", "", "", "" }, { "", "", "", "" }, { "", "", "", "" },
			{ "", "", "", "" }, { "", "", "", "" }, { "", "", "", "" }, { "", "", "", "" } };
	private String[] columns1 = { "game_name", "host", "mode", "join" };
	private String[][] values2 = { { "" }, { "" }, { "" }, { "" } };
	private String[] columns2 = { "Player" };
	private DefaultTableModel[] tableModel = { new DefaultTableModel(values1, columns1),
			new DefaultTableModel(values2, columns2) };
	private JTable[] table = new JTable[2];
	private JLabel[] createLabel = { new JLabel("  game_name"), new JLabel("  password") };
	private JTextField[] txtbox = { new JTextField(), new JTextField() };
	private JRadioButton[] radio = { new JRadioButton("private game"), new JRadioButton("random map") };
	private String NAME = null;
	private String GAME = "new";
	private boolean started = false;
	private Communication conn = new Communication();

	LobbyFrame() {
		this.setSize(new Dimension(600, 450));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		Container base = this.getContentPane();
		base.setLayout(cardlayout);
		for (int i = 0; i < 2; i++) {
			table[i] = new JTable(tableModel[i]) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
		}
		btn[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = logintext[0].getText();
				String pass = logintext[1].getText();
				String ret = null;
				if (id == null || pass == null) {
					System.out.println("fail");
				} else {
					try {
						ret = conn.request("login", "true", "user_id", id, "pass", pass);
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					if (ret.equals("init")) {
						cardlayout.next(base);
					} else if (ret.equals("fail")) {
						System.out.println("idかpass違うよ");
					} else {
						NAME = ret;
						cardlayout.show(base, "p2");
						fetchGameList();
					}
				}
			}
		});
		btn[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = registertext.getText();
				String ret = null;
				try {
					ret = conn.request("register", "true", "user_id", logintext[0].getText(), "user_name", name);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (ret == null) {
					NAME = name;
					fetchGameList();
					cardlayout.next(base);

				} else {
					System.out.println("An error has been occured");
				}
			}
		});

		btn[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardlayout.next(base);
			}
		});

		btn[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] mode = { "false", "false" };
				if (radio[0].isSelected()) {
					mode[0] = "true";
				}
				if (radio[1].isSelected()) {
					mode[1] = "true";
				}
				String ret = conn.createGame(txtbox[0].getText(), NAME, mode[0], txtbox[1].getText(), mode[1]);
				if (ret == null) {
					panel[4].add(btn[4], BorderLayout.SOUTH);
					GAME = txtbox[0].getText();
					cardlayout.next(base);
					MultiThread mth = new MultiThread();
					mth.start();
				}

			}
		});
		btn[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(table[1].getValueAt(3, 0));
				if (!(table[1].getValueAt(3, 0).equals(""))) {
					started = true;
					String ret = conn.startGame(GAME);
					if (ret == null) {
						GameFrame gameframe = new GameFrame(GAME, NAME, true, true);
						gameframe.setVisible(true);
					}
				} else {
					System.out.println("4人いないよ");
				}
			}
		});
		bbtn[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fetchGameList();
			}
		});
		;
		bbtn[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardlayout.previous(base);
			}
		});
		bbtn[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ret = conn.deleteGame(GAME);
				if (ret == null) {
					cardlayout.previous(base);
				}
			}
		});

		for (int i = 0; i < 2; i++) {
			loginp.add(loginlabel[i]);
			logintext[i].setPreferredSize(new Dimension(150, 23));
			loginp.add(logintext[i]);
		}
		panel[0].add(loginp, BorderLayout.CENTER);

		registerp.add(registerlabel);
		registertext.setPreferredSize(new Dimension(200, 25));
		registerp.add(registertext);
		panel[1].add(registerp, BorderLayout.CENTER);

		scrollp[0] = new JScrollPane(table[0]);
		for (int i = 0; i < 5; i++) {
			label[i].setFont(new Font("MS ゴシック", Font.BOLD, 32));
			splitp[i].setLeftComponent(label[i]);
			splitp[i].setRightComponent(bbtn[i]);
			splitp[i].setDividerLocation(495);
			panel[i].add(splitp[i], BorderLayout.NORTH);
			if (i != 4) {
				panel[i].add(btn[i], BorderLayout.SOUTH);
			}
		}
		panel[2].add(scrollp[0], BorderLayout.CENTER);
		for (int i = 0; i < 2; i++) {
			createPanel.add(createLabel[i]);
			createPanel.add(txtbox[i]);
			createPanel.add(radio[i]);
		}
		panel[3].add(createPanel, BorderLayout.CENTER);

		scrollp[1] = new JScrollPane(table[1]);
		panel[4].add(scrollp[1], BorderLayout.CENTER);

		String[] name = { "p0", "p1", "p2", "p3", "p4" };
		for (int i = 0; i < 5; i++) {
			base.add(panel[i], name[i]);
		}
	}

	public void fetchGameList() {
		try {
			String names = conn.getGameName();
			String name = "";
			String json = "";
			String text = "";
			int num = 0;

			for (int i = 0; i < conn.readJsonToInt(names, 0); i++) {
				name = conn.readJsonToStr(names, num + 1);
				json = conn.request("getGameInfo", "true", "game_name", name);
				for (int j = 0; j < 4; j++) {
					text = conn.readJsonToStr(json, j);
					if (j == 2) {
						if (text.equals("f")) {
							table[0].setValueAt("public", num, j);
						} else {
							table[0].setValueAt("private", num, j);
						}
					} else if (j == 3) {
						table[0].setValueAt(text, num, j);

					} else {
						table[0].setValueAt(text, num, j);
					}
				}
				num++;
			}
			table[0].getColumn("join").setCellRenderer(new ButtonRenderer());
			table[0].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					table[0].removeMouseListener(this);
					int row = table[0].getSelectedRow();
					int column = table[0].getSelectedColumn();
					if (column == 3) {
						GAME = table[0].getValueAt(row, 0).toString();
						String ret = conn.joinGame(GAME, NAME);
						if (ret == null) {
							cardlayout.last(getContentPane());
							MultiThread mth2 = new MultiThread();
							mth2.start();
						}
					}
				}
			});
			table[0].repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fetchPlayers(String name) {
		String[] players = { "", "", "", "" };
		String json = conn.getPlayerName(name);
		for (int i = 0; i < 4; i++) {
			players[i] = conn.readJsonToStr(json, i);
			if (players[i] == "null") {
				players[i] = "";
			}
			table[1].setValueAt(players[i], i, 0);
			table[1].repaint();
		}
	}

	// Override:JButtonをレンダリングする,TableのvalueをString型でJButtonに表示
	class ButtonRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (!value.equals("")) {
				return new JButton("JOIN this game" + value.toString() + "/4");
			} else {
				return new JButton();
			}
		}
	}

	class MultiThread extends Thread {
		public void run() {
			while (started == false) {
				String[] players = { "", "", "", "" };
				String json = conn.getPlayerName(GAME);
				for (int i = 0; i < 4; i++) {
					players[i] = conn.readJsonToStr(json, i);
					if (players[i] == "null") {
						players[i] = "";
					}
					table[1].setValueAt(players[i], i, 0);
				}
				table[1].repaint();
				if (conn.readJsonToInt(json, 4) == 1) {
					// ゲストとして
					GameFrame gamefame = new GameFrame(GAME, NAME, false, false);
					gamefame.setVisible(true);
					started = true;
				}
			}
		}
	}
}