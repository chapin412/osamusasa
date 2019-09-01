package application;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

public class GameFrame extends JFrame {
	private String GAME = null;
	private String NAME = null;
	private Dimension screen;
	private int NUM = -1;
	private int TURN = 0;
	private int winner = -1;
	private boolean gameend = false, myturn = false, enabledbtn = false, unablebtn = false, showturnlabel = false,
			diced = false, checkcardnum = false, useddev = false, isseven = false, sevenlockbtn = false,sevenlockbtn2 = false, droped = false, alldroped = false,
			movethief = false, thiefon = false, havenego = false, unlocked = false, end = false, fend = false;
	private JLayeredPane pane = new JLayeredPane();
	private SpringLayout splayout_base = new SpringLayout();
	private CardPanel cpanel = new CardPanel();
	private FieldPanel fpanel = new FieldPanel();
	private DicePanel dpanel = new DicePanel();
	private Player[] player = new Player[4];
	private PlayerPanel ppanel = new PlayerPanel(4);
	private ChatPanel chpanel = new ChatPanel();
	private Communication conn = new Communication();
	//t,b,w,s,i,3
	private boolean[] trade = {false,false,false,false,false,false};
	private FirstRun fr = new FirstRun();
	private Run run = new Run();
	private Draw draw = new Draw();

	GameFrame(String game, String playername, boolean host, boolean random) {
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		GAME = game;
		NAME = playername;
		// instance生成後setvisibleを実行すること
		this.setTitle("Catan");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, screen.width, screen.height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		// contentPaneの取得・設定
		Container base = this.getContentPane();
		base.setBackground(new Color(100, 200, 255));
		base.setLayout(splayout_base);
		base.add(pane);
		pane.setLayer(base, 0);

		// playerの生成
		String[] players = { "", "", "", "" };
		String json = conn.getPlayerName(game);
		for (int i = 0; i < 4; i++) {
			players[i] = conn.readJsonToStr(json, i);
			if (players[i] == "null") {
				players[i] = "NO PLAYER";
			}
		}
		for (int i = 0; i < 4; i++) {
			player[i] = new Player(players[i], i + 1);
		}
		// plyernum 自分の。
		for (int i = 0; i < 4; i++) {
			if (player[i].GetPlayerName().equals(NAME)) {
				NUM = player[i].getPlayerNum();
			}
		}

		// panelの生成
		cpanel.setCardPanel();
		// 街道ボタン
		cpanel.getFBtn(0).addActionListener(new ActionListener() {
			int res[] = { 1, 1, 0, 0, 0 };

			public void actionPerformed(ActionEvent e) {
				if (restrictResCard(res)) {
					try {
						String json = conn.request("getavroads", GAME, "num", String.valueOf(NUM));
						int num = conn.readJsonToInt(json, 0);
						for (int i = 1; i < num + 1; i++) {
							int index = conn.readJsonToInt(json, i);
							fpanel.getRoad(index).setStatus(5, fpanel.getRoadLocate(index), false);
							// 設置用のマウスリスナー
							int n = index;
							fpanel.getRoad(index).addMouseListener(new MouseAdapter() {
								public void mouseReleased(MouseEvent e) {
									// 他をemptyの状態に戻す
									for (int i = 1; i < num + 1; i++) {
										int index2 = conn.readJsonToInt(json, i);
										MouseListener[] listen = fpanel.getRoad(index2).getMouseListeners();
										fpanel.getRoad(index2).removeMouseListener(listen[0]);
										fpanel.getRoad(index2).setStatus(6, 0, false);
									}
									// 設置
									fpanel.getRoad(n).setStatus(NUM, fpanel.getRoadLocate(n), false);
									System.out.println(
											"インデックス:" + n + "画像の番号:" + fpanel.getRoadLocate(n) + "プレイヤー:" + NUM);
									conn.constructRoad(GAME, NAME, n, fpanel.getVector(n, 0), fpanel.getVector(n, 1),
											NUM);
									fpanel.getRoadPanel().repaint();
									// カード消費
									conn.useCard(GAME, NAME, res);
								}
							});
						}
						fpanel.getRoadPanel().repaint();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(getFrame(), "街道には<木:1,レンガ:1>が必要", "資源不足", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		// 開拓地ボタン
		cpanel.getFBtn(1).addActionListener(new ActionListener() {
			int[] res = { 1, 1, 1, 1, 0 };

			public void actionPerformed(ActionEvent e) {
				if (player[NUM - 1].getAmountOfStlms() <= 5) {
					if (restrictResCard(res)) {
						cpanel.unableBtns();
						ppanel.unableBtns();
						// 設置可能な開拓地を表示
						try {
							String json = conn.request("getavstlms", GAME, "num", String.valueOf(NUM));
							int num = conn.readJsonToInt(json, 0);
							for (int i = 1; i < num + 1; i++) {
								int index = conn.readJsonToInt(json, i);
								fpanel.getStlm(index).setStatus(NUM, 1, false);
								// 設置用のマウスリスナー
								int n = index;
								fpanel.getStlm(index).addMouseListener(new MouseAdapter() {
									public void mouseReleased(MouseEvent e) {
										// 他をemptyの状態に戻す
										for (int i = 1; i < num + 1; i++) {
											int index2 = conn.readJsonToInt(json, i);
											MouseListener[] listen = fpanel.getStlm(index2).getMouseListeners();
											fpanel.getStlm(index2).removeMouseListener(listen[0]);
											fpanel.getStlm(index2).setStatus(5, 0, false);
										}
										// 設置
										int x = fpanel.getXYZ(n, 0);
										int y = fpanel.getXYZ(n, 1);
										int z = fpanel.getXYZ(n, 2);
										int rest = (x + y + z) % 2;
										fpanel.getStlm(n).setStatus(NUM, 0, false);
										switch(n) {
										case 38:
										case 43:
											
										case 48:
										case 52:
											
										case 15:
										case 20:
											
										case 7:
										case 11:
											
										case 1:
										case 4:
											
										case 2:
										case 6:
										case 21:
										case 27:
										case 37:
										case 42:
										case 50:
										case 53:
										default:
										break;
										}
										conn.constructStml(GAME, NAME, n, 1, x, y, z, rest, NUM);
										fpanel.getStlmPanel().repaint();
										player[NUM - 1].constStlm();
										// カード消費
										int[] cards = { 1, 1, 1, 1, 0 };
										conn.useCard(GAME, NAME, cards);
										if (!useddev) {
											cpanel.enableDBtns();
										}
										cpanel.enableFBtns();
										ppanel.enableBtns();
									}
								});
							}
							fpanel.getStlmPanel().repaint();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(getFrame(), "開拓地には<木:1,レンガ:1,小麦:1,羊:1>が必要", "資源不足",
								JOptionPane.PLAIN_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(getFrame(), "建設できる開拓地は5つまで", "土地は有限資産", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		// 都市化ボタン
		cpanel.getFBtn(2).addActionListener(new ActionListener() {
			int[] res = { 0, 0, 2, 0, 3 };

			public void actionPerformed(ActionEvent e) {
				if (restrictResCard(res)) {
					cpanel.unableBtns();
					ppanel.unableBtns();
					// 都市化可能な開拓地を表示
					String json;
					try {
						json = conn.request("urbanize", GAME, "player", NAME);
						int num = conn.readJsonToInt(json, 0);
						for (int i = 1; i < num + 1; i++) {
							int index = conn.readJsonToInt(json, i);
							fpanel.getStlm(index).setStatus(NUM, 3, false);
							// 設置用のマウスリスナー
							int n = index;
							fpanel.getStlm(index).addMouseListener(new MouseAdapter() {
								public void mouseReleased(MouseEvent e) {
									for (int i = 1; i < num + 1; i++) {
										int index2 = conn.readJsonToInt(json, i);
										MouseListener[] listen = fpanel.getStlm(index2).getMouseListeners();
										fpanel.getStlm(index2).removeMouseListener(listen[0]);
										fpanel.getStlm(index2).setStatus(NUM, 0, false);
									}
									fpanel.getStlm(n).setStatus(NUM, 2, true);
									conn.constCity(GAME, n);
									conn.useCard(GAME, NAME, res);
									player[NUM-1].constCity();
									if (!useddev) {
										cpanel.enableDBtns();
									}
									cpanel.enableFBtns();
									ppanel.enableBtns();
								}
							});
						}
						fpanel.getStlmPanel().repaint();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(getFrame(), "都市化には<小麦:2,鉱石:3>が必要", "資源不足", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		// 発展ボタン
		cpanel.getFBtn(3).addActionListener(new ActionListener() {
			int[] res = { 0, 0, 1, 1, 1 };

			public void actionPerformed(ActionEvent e) {

				if (restrictResCard(res)) {
					String json = conn.acquiredev(GAME, NAME);
					if (json.equals("nocard")) {
						JOptionPane.showMessageDialog(getFrame(), "発展カードが残っていません", "empty", JOptionPane.PLAIN_MESSAGE);
					} else {
						conn.useCard(GAME, NAME, res);
						JOptionPane.showMessageDialog(getFrame(), "発展により " + json + "カード を手に入れました", "発展",
								JOptionPane.PLAIN_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(getFrame(), "発展には<小麦:1,羊:1,鉱石:1>が必要", "資源不足",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		// ターン終了ボタン
		cpanel.getFBtn(4).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] yn = { "YES", "NO" };
				int select = JOptionPane.showOptionDialog(getFrame(), "ターンを終了します", "確認", 0, JOptionPane.PLAIN_MESSAGE,
						null, yn, yn[1]);
				if (select == 0) {
					cpanel.unableBtns();
					ppanel.unableBtns();
					int point = player[NUM-1].getPoints();
					conn.sendPoint(GAME,NAME,point,NUM);
					end = true;
				}
			}
		});

		// 騎士カード
		cpanel.getDBtn(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (restrictDevCard(0)) {
					JOptionPane.showMessageDialog(getFrame(), "盗賊を移動させてください", "裏社会", JOptionPane.PLAIN_MESSAGE);
					thief(false);
				} else {
					JOptionPane.showMessageDialog(getFrame(), "騎士カードがありません", "武豊だけじゃただのおじさん",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		// 街道建設
		cpanel.getDBtn(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (restrictDevCard(1)) {
					try {
						cpanel.unableBtns();
						ppanel.unableBtns();
						String json1 = conn.request("getavroads", GAME, "num", String.valueOf(NUM));
						int num = conn.readJsonToInt(json1, 0);
						if (num != 0) {
							conn.useDev(GAME, NAME, "construct");
							for (int i = 1; i < num + 1; i++) {
								int index = conn.readJsonToInt(json1, i);
								fpanel.getRoad(index).setStatus(5, fpanel.getRoadLocate(index), false);
								// 設置用のマウスリスナー
								int n = index;
								fpanel.getRoad(index).addMouseListener(new MouseAdapter() {
									public void mouseReleased(MouseEvent e) {
										// 他をemptyの状態に戻す
										for (int i = 1; i < num + 1; i++) {
											int index2 = conn.readJsonToInt(json1, i);
											MouseListener[] listen = fpanel.getRoad(index2).getMouseListeners();
											fpanel.getRoad(index2).removeMouseListener(listen[0]);
											fpanel.getRoad(index2).setStatus(6, 0, false);
										}
										// 設置
										fpanel.getRoad(n).setStatus(NUM, fpanel.getRoadLocate(n), false);
										conn.constructRoad(GAME, NAME, n, fpanel.getVector(n, 0),
												fpanel.getVector(n, 1), NUM);
										fpanel.getRoadPanel().repaint();

										// 1more
										try {
											String json2 = conn.request("getavroads", GAME, "num", String.valueOf(NUM));
											int num = conn.readJsonToInt(json2, 0);
											if (num != 0) {
												for (int i = 1; i < num + 1; i++) {
													int index = conn.readJsonToInt(json2, i);
													fpanel.getRoad(index).setStatus(5, fpanel.getRoadLocate(index),
															false);
													// 設置用のマウスリスナー
													int n = index;
													fpanel.getRoad(index).addMouseListener(new MouseAdapter() {
														public void mouseReleased(MouseEvent e) {
															// 他をemptyの状態に戻す
															for (int i = 1; i < num + 1; i++) {
																int index2 = conn.readJsonToInt(json2, i);
																MouseListener[] listen = fpanel.getRoad(index2)
																		.getMouseListeners();
																fpanel.getRoad(index2).removeMouseListener(listen[0]);
																fpanel.getRoad(index2).setStatus(6, 0, false);
															}
															// 設置
															fpanel.getRoad(n).setStatus(NUM, fpanel.getRoadLocate(n),
																	false);
															conn.constructRoad(GAME, NAME, n, fpanel.getVector(n, 0),
																	fpanel.getVector(n, 1), NUM);
															fpanel.getRoadPanel().repaint();
														}
													});
												}
											} else {
												JOptionPane.showMessageDialog(getFrame(), "2本目の街道を立てられる場所がありませんでした",
														"無駄な投資", JOptionPane.PLAIN_MESSAGE);
												if (diced) {
													cpanel.enableFBtns();
													ppanel.enableBtns();
												}
											}
											useddev = true;
											if (diced) {
												cpanel.enableFBtns();
												ppanel.enableBtns();
											}
											fpanel.getRoadPanel().repaint();
										} catch (Exception e1) {
											e1.printStackTrace();
										}
									}
								});
							}
						} else {
							JOptionPane.showMessageDialog(getFrame(), "あなたが街道を立てられる場所がありません", "土地も資産",
									JOptionPane.PLAIN_MESSAGE);
							if (diced) {
								cpanel.enableFBtns();
								ppanel.enableBtns();
							}
							cpanel.enableDBtns();
						}
						fpanel.getRoadPanel().repaint();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(getFrame(), "街道建設カードがありません", "人生にレールなんてない",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		// 発見ボタン
		cpanel.getDBtn(2).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (restrictDevCard(2)) {
					cpanel.unableBtns();
					ppanel.unableBtns();
					DialogFrame df = new DialogFrame("dis", GAME, NAME);
					df.getAcceptBtn().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int[] res = { 0, 0, 0, 0, 0 };
							if (df.getSelectedDis() == 2) {
								for (int i = 0; i < 5; i++) {
									if (df.getRadioBtn(i).isSelected()) {
										res[i] = 1;
									}
								}
								useddev = true;
								conn.useDev(GAME, NAME, "discover");
								conn.discover(GAME, NAME, res);
								if (diced) {
									cpanel.enableFBtns();
									ppanel.enableBtns();
								}
								df.dispose();
							} else {
								df.getLetter2().setForeground(Color.RED);
								df.getPanel().repaint();
							}
						}
					});
					df.getCancelBtn().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							cpanel.enableDBtns();
							if (diced) {
								cpanel.enableFBtns();
								ppanel.enableBtns();
							}
							df.dispose();
						}
					});
					df.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(getFrame(), "街道建設カードがありません", "科研費をください...",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		// 独占ボタン
		cpanel.getDBtn(3).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (restrictDevCard(3)) {
					cpanel.unableBtns();
					ppanel.unableBtns();
					DialogFrame df = new DialogFrame("mono", GAME, NAME);
					df.getAcceptBtn().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (df.getSelectedMono().equals("error")) {
								df.getLetter1().setForeground(Color.RED);
								df.getPanel().repaint();
							} else {
								conn.monopoly(GAME, NAME, df.getSelectedMono());
								conn.useDev(GAME, NAME, "monopoly");
								if (diced) {
									cpanel.enableFBtns();
									ppanel.enableBtns();
								}
								useddev = true;
								df.dispose();
							}
						}
					});
					df.getCancelBtn().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							cpanel.enableDBtns();
							if (diced) {
								cpanel.enableFBtns();
								ppanel.enableBtns();
							}
							df.dispose();
						}
					});
					df.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(getFrame(), "街道建設カードがありません", "独占禁止法", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		if (host) {
			fpanel.arrangeTiles(random);
			this.sendTileInfo();
			conn.sendStlmInfo(GAME);
			conn.sendRoadInfo(GAME);
		} else {
			fpanel.fetchTiles(GAME);
		}
		fpanel.setTiles();
		fpanel.setRoads();
		fpanel.setSettlements();
		// addカードタブパネル
		splayout_base.putConstraint(SpringLayout.SOUTH, cpanel.getTabPane(), -1 * screen.height / 1080,
				SpringLayout.SOUTH, base);
		splayout_base.putConstraint(SpringLayout.EAST, cpanel.getTabPane(), 1 * screen.width / 1920, SpringLayout.EAST,
				base);
		base.add(cpanel.getTabPane());
		pane.setLayer(cpanel.getTabPane(), 100);

		// addフィールドパネル
		splayout_base.putConstraint(SpringLayout.NORTH, fpanel.getPane(), 0, SpringLayout.NORTH, base);
		splayout_base.putConstraint(SpringLayout.WEST, fpanel.getPane(), 530 * screen.width / 1920, SpringLayout.WEST,
				base);
		base.add(fpanel.getPane());
		// pane.setLayer(fpanel.getPanel(), 100);

		// addDicePanel
		splayout_base.putConstraint(SpringLayout.SOUTH, dpanel.getPanel(), -70, SpringLayout.SOUTH, base);
		splayout_base.putConstraint(SpringLayout.WEST, dpanel.getPanel(), 780 * screen.width / 1920, SpringLayout.WEST,
				base);
		base.add(dpanel.getPanel());
		dpanel.setDiceLabel(1, 6);
		dpanel.getBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diced = true;
				dpanel.getBtn().setEnabled(false);
				sendDice();
				
			}
		});
		// addプレイヤーパネル
		for (int i = 0; i < 4; i++) {
			if (i % 2 == 0) {
				splayout_base.putConstraint(SpringLayout.NORTH, ppanel.panel[i], (50 + i * 200) * screen.height / 1080,
						SpringLayout.NORTH, base);
				splayout_base.putConstraint(SpringLayout.WEST, ppanel.panel[i], (200 + i * 600) * screen.width / 1920,
						SpringLayout.WEST, base);
			} else {
				splayout_base.putConstraint(SpringLayout.NORTH, ppanel.panel[i],
						(50 + (i - 1) * 200) * screen.height / 1080, SpringLayout.NORTH, base);
				splayout_base.putConstraint(SpringLayout.WEST, ppanel.panel[i],
						(200 + Math.abs(i - 3) * 600) * screen.width / 1920, SpringLayout.WEST, base);
			}
			ppanel.setPlayerName(player);
			ppanel.setPlayerRes(player);
			base.add(ppanel.panel[i]);
			pane.setLayer(ppanel.panel[i], 100);
		}
		// addlistener to nego
		for (int i = 0; i < 4; i++) {
			int n = i;
			ppanel.getNBtn(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (player[n].getPlayerNum() == NUM) {
						// 貿易フレーム
					} else {
						// 取引フレーム
						DialogFrame df = new DialogFrame(player[n].getPlayerName(), NAME, player[n].getResCard(),
								player[NUM - 1].getResCard());
						df.getAcceptBtn().addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								cpanel.unableBtns();
								ppanel.unableBtns();
								int[] res1 = new int[5];
								int[] res2 = new int[5];
								for (int i = 0; i < 5; i++) {
									res1[i] = Integer.parseInt(df.getNegoSpin(1, i).getValue().toString());
									res2[i] = Integer.parseInt(df.getNegoSpin(0, i).getValue().toString());
								}
								conn.sendNegoRequest(GAME, NAME, player[n].getPlayerName(), res1, res2);
								df.dispose();
							}
						});
						df.getCancelBtn().addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if (!useddev) {
									cpanel.enableDBtns();
								}
								cpanel.enableFBtns();
								ppanel.enableBtns();
								df.dispose();
							}
						});
						df.setVisible(true);
					}
				}
			});
		}
		// add chatpanel
		splayout_base.putConstraint(SpringLayout.SOUTH, chpanel.getChatPanel(), -5 * screen.height / 1080,
				SpringLayout.SOUTH, base);
		splayout_base.putConstraint(SpringLayout.WEST, chpanel.getChatPanel(), 35 * screen.width / 1920,
				SpringLayout.WEST, base);
		base.add(chpanel.getChatPanel());
		pane.setLayer(chpanel.getChatPanel(), 100);
		if (host) {
			conn.broadcastStart(GAME);
		}
		draw.start();
		run.start();
		fr.start();
	}

	public void sendTileInfo() {
		for (int i = 0; i < 37; i++) {
			try {
				conn.request("tile", "true", "game_name", GAME, "index", String.valueOf(i), "kind",
						String.valueOf(fpanel.tile[i].getTileKind()), "num",
						String.valueOf(fpanel.tile[i].getTileNum()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void sendStlmInfo() {
		try {
			conn.request("stlm", "true", "game_name", GAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void thief(boolean dice) {
		try {
			for (int i = 0; i < 37; i++) {
				int n = i;
				fpanel.getTile(i).getNumLabel().addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {
						// 発展カードは1ターンに1度
						// 途中でやめられないように
						cpanel.unableBtns();
						ppanel.unableBtns();
						if(!dice) {
							conn.useDev(GAME, NAME, "knight");
						}
						// すべてのlistener消す
						for (int j = 1; j < 37; j++) {
							MouseListener[] listen = fpanel.getTile(j).getNumLabel().getMouseListeners();
							fpanel.getTile(j).getNumLabel().removeMouseListener(listen[0]);
						}
						// 設置
						fpanel.getTile(n).setTileNum(100);
						// 他人の開拓地のjson
						String json = conn.changeNum(GAME, NAME, n);
						if (conn.readJsonToInt(json, 0) != 0) {
							int count = conn.readJsonToInt(json, 0);
							// 盗む対象の選択
							for (int k = 0; k < count; k++) {
								int m = conn.readJsonToInt(json, k + 1);
								fpanel.getStlm(m).addMouseListener(new MouseAdapter() {
									public void mouseReleased(MouseEvent e) {
										for (int l = 0; l < count; l++) {
											MouseListener[] listen = fpanel.getStlm(conn.readJsonToInt(json, l + 1))
													.getMouseListeners();
											fpanel.getStlm(conn.readJsonToInt(json, l + 1)).removeMouseListener(listen[0]);
										}
										if (dice) {
											if (!useddev) {
												cpanel.enableDBtns();
											}
											movethief = true;
										} else {
											useddev = true;
										}
										String thief = conn.thief(GAME, NAME, fpanel.getStlm(m).getPossesion());
										if(thief.equals("nores")) {
											JOptionPane.showMessageDialog(getFrame(), "盗める資源がありませんでした","無駄足", JOptionPane.PLAIN_MESSAGE);
										}else {
											JOptionPane.showMessageDialog(getFrame(), thief + " を盗みました","トンズラするぜ", JOptionPane.PLAIN_MESSAGE);
										}
										cpanel.enableFBtns();
										ppanel.enableBtns();
									}
								});
							}
						} else {
							if (diced) {
								cpanel.enableFBtns();
								ppanel.enableBtns();
							}
							if (dice) {
								if (!useddev) {
									cpanel.enableDBtns();
								}
								movethief = true;
							} else {
								useddev = true;
							}
						}
						fpanel.getTilePanel().repaint();
					}
				});
			}
			fpanel.getTilePanel().repaint();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public boolean restrictResCard(int[] restrict) {
		boolean enable = true;
		int[] res = player[NUM - 1].getResCard();
		for (int i = 0; i < 5; i++) {
			if (res[i] < restrict[i]) {
				enable = false;
			}
		}
		return enable;
	}

	public boolean restrictDevCard(int index) {
		boolean enable = true;
		int[] num = player[NUM - 1].getDevCard();
		if (num[index] < 1) {
			enable = false;
		}
		return enable;
	}

	public void showTradeFrame(String target, String from, int[] restarget, int[] resfrom) {
		JFrame frame = new JFrame("取引");
		SpringLayout sp = new SpringLayout();
		JPanel panel = new JPanel(sp);
		JLabel[] namelabel = { new JLabel(target), new JLabel(from) };
		JLabel label = new JLabel();
		SpinnerNumberModel[] model = new SpinnerNumberModel[2];
		JSpinner[][] spin = new JSpinner[2][5];
		JButton acceptbtn = new JButton("決定");
		JButton cancelbtn = new JButton("取消");
		String json = conn.getTradeRate();
		ImageIcon icon = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(getClass().getResource("smallcards.png")));
		label.setIcon(resizeImage(icon));
		/*
		 * int[] rate = new int[5]; for (int i = 0; i < 5; i++) { rate[i] =
		 * conn.readJsonToInt(json, i); }
		 */
		frame.setTitle("取引");
		frame.setSize(new Dimension(450 * screen.width / 1920, 250 * screen.height / 1080));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		for (int i = 0; i < 2; i++) {
			sp.putConstraint(SpringLayout.NORTH, namelabel[i], 20 + i * 80, SpringLayout.NORTH, panel);
			sp.putConstraint(SpringLayout.WEST, namelabel[i], 360, SpringLayout.WEST, panel);
			panel.add(namelabel[i]);
			for (int j = 0; j < 5; j++) {
				if (i == 0) {
					model[i] = new SpinnerNumberModel(0, 0, restarget[j], 1);
				} else {
					model[i] = new SpinnerNumberModel(0, 0, resfrom[j], 1);
				}
				spin[i][j] = new JSpinner(model[i]);
				sp.putConstraint(SpringLayout.NORTH, spin[i][j], 15 + i * 80, SpringLayout.NORTH, panel);
				sp.putConstraint(SpringLayout.WEST, spin[i][j], 30 + j * 60, SpringLayout.WEST, panel);
				panel.add(spin[i][j]);
			}
		}
		sp.putConstraint(SpringLayout.NORTH, label, 40, SpringLayout.NORTH, panel);
		sp.putConstraint(SpringLayout.WEST, label, 20, SpringLayout.WEST, panel);
		panel.add(label);
		sp.putConstraint(SpringLayout.NORTH, acceptbtn, 140, SpringLayout.NORTH, panel);
		sp.putConstraint(SpringLayout.WEST, acceptbtn, 60, SpringLayout.WEST, panel);
		panel.add(acceptbtn);
		sp.putConstraint(SpringLayout.NORTH, cancelbtn, 140, SpringLayout.NORTH, panel);
		sp.putConstraint(SpringLayout.WEST, cancelbtn, 150, SpringLayout.WEST, panel);
		panel.add(cancelbtn);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

	public JFrame getFrame() {
		return this;
	}

	public void sendDice() {
		try {
			dpanel.rollDice();
			int d1 = dpanel.getDice(0), d2 = dpanel.getDice(1);
			if(d1+d2 == 7) {
				isseven = true;
			}else {
				isseven = false;
			}
			conn.request("game_name", GAME, "turn", String.valueOf(TURN), "dice1", String.valueOf(d1), "dice2",
					String.valueOf(d2));
		} catch (Exception e) {
			e.printStackTrace();
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

	class FirstRun extends Thread {
		boolean last = false;
		boolean first = false, second = false, drews1 = false, drews2 = false, drewr1 = false, drewr2 = false,
				stlm1 = false, stlm2 = false, road1 = false, road2 = false;
		int stlmindex1, stlmindex2, ini;
		int[][] number = { { 1, 2, 3, 4, 4, 3, 2, 1 }, { 2, 3, 4, 1, 1, 4, 3, 2 }, { 3, 4, 1, 2, 2, 1, 4, 3 },
				{ 4, 1, 2, 3, 3, 2, 1, 4 } };

		public void run() {
			ini = conn.readJsonToInt(conn.getChecker(GAME), 8);
			cpanel.unableBtns();
			ppanel.unableBtns();
			while (fend == false) {
				int turn = getFirst();
				switch (turn) {
				case 1:
					first = true;
					second = false;
					break;
				case 2:
					first = false;
					second = true;
					break;
				case 0:
					first = false;
					second = false;
					fend = true;
					break;
				case 3:
					first = false;
					second = false;
					break;
				}
				if (first == true) {
					if (stlm1 == false) {
						if (drews1 == false) {
							constStlm();// stlm1=true
							drews1 = true;
						} else if (drews1) {
							// 待ち
						}
					} else if (stlm1) {
						if (!road1) {
							if (!drewr1) {
								constRoad(stlmindex1);// ここでroad1=true
								drewr1 = true;
							} else if (drewr1) {
								// 待ち
							}
						} else if (road1) {
							// 終わったよ報告
							first = false;
						}
					}
				}
				if (second) {
					if (!stlm2) {
						if (!drews2) {
							drews2 = true;
							constStlm();// stlm2=true
						} else if (drews2) {
							// 待ち
						}
					} else if (stlm2) {
						if (!road2) {
							if (!drewr2) {
								drewr2 = true;
								constRoad(stlmindex2);// ここでroad1=true
							} else if (drewr2) {
								// 待ち
							}
						} else if (road2) {
							// 終わったよ報告
							second = false;
							fend = true;
						}
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (last) {
				conn.startTurn(GAME);
			}
		}

		public void constStlm() {
			// 表示更新停止
			// 設置可能な開拓地を表示
			try {
				String json = conn.request("getfreestlms", GAME, "num", String.valueOf(NUM));
				int num = conn.readJsonToInt(json, 0);
				for (int i = 1; i < num + 1; i++) {
					int index = conn.readJsonToInt(json, i);
					fpanel.getStlm(index).setStatus(NUM, 1, false);
					// 設置用のマウスリスナー
					int n = index;
					fpanel.getStlm(index).addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
							// 他をemptyの状態に戻す
							for (int i = 1; i < num + 1; i++) {
								int index2 = conn.readJsonToInt(json, i);
								MouseListener[] listen = fpanel.getStlm(index2).getMouseListeners();
								fpanel.getStlm(index2).removeMouseListener(listen[0]);
								fpanel.getStlm(index2).setStatus(5, 0, false);
							}
							// 設置
							int x = fpanel.getXYZ(n, 0);
							int y = fpanel.getXYZ(n, 1);
							int z = fpanel.getXYZ(n, 2);
							int rest = (x + y + z) % 2;
							fpanel.getStlm(n).setStatus(NUM, 0, false);
							conn.constructStml(GAME, NAME, n, 1, x, y, z, rest, NUM);
							player[NUM - 1].constStlm();
							// fpanel.getStlmPanel().repaint();
							if (first) {
								stlm1 = true;
								stlmindex1 = n;
							}
							if (second) {
								stlm2 = true;
								stlmindex2 = n;
							}
						}
					});
				}
				fpanel.getStlmPanel().repaint();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		public void constRoad(int stlmindex) {

			try {
				String json = conn.request("getavroadsfirst", GAME, "num", String.valueOf(NUM), "stlmindex",
						String.valueOf(stlmindex));
				int num = conn.readJsonToInt(json, 0);
				for (int i = 1; i < num + 1; i++) {
					int index = conn.readJsonToInt(json, i);
					fpanel.getRoad(index).setStatus(5, fpanel.getRoadLocate(index), false);
					// 設置用のマウスリスナー
					int n = index;
					fpanel.getRoad(index).addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
							// 他をemptyの状態に戻す
							for (int i = 1; i < num + 1; i++) {
								int index2 = conn.readJsonToInt(json, i);
								MouseListener[] listen = fpanel.getRoad(index2).getMouseListeners();
								fpanel.getRoad(index2).removeMouseListener(listen[0]);
								fpanel.getRoad(index2).setStatus(6, 0, false);
							}
							// 設置
							fpanel.getRoad(n).setStatus(NUM, fpanel.getRoadLocate(n), false);
							conn.constructRoad(GAME, NAME, n, fpanel.getVector(n, 0), fpanel.getVector(n, 1), NUM);
							fpanel.getRoadPanel().repaint();
							if (first) {
								road1 = true;
								first = false;
							} else if (second) {
								second = false;
								road2 = true;
								conn.updateCardFirst(GAME, stlmindex2, NAME);
							}
							conn.countFirst(GAME);
						}
					});
				}
				fpanel.getRoadPanel().repaint();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		public int getFirst() {
			String json = conn.getFirst(GAME, NUM);
			int ret = 0;
			int count = conn.readJsonToInt(json, 0);
			System.out.println(count);
			if (count >= 9) {
				ret = 0;
			} else if (number[ini - 1][count - 1] == NUM) {
				if (count < 5) {
					ret = 1;
				} else {
					if (count == 8) {
						ret = 2;
						last = true;
					}
					ret = 2;
				}
			} else {
				ret = 3;
			}
			return ret;
		}
	}

	class Run extends Thread {

		public void run() {
			// ゲームが終わるまで回す
			System.out.println("起動");
			while (gameend == false) {
				if (fend == false) {
					System.out.println("待機中");
				} else {
					if (end == false) {
						// 自分のターンの時
						if (myturn == true) {
							if (!showturnlabel) {
								showturnlabel = true;
								dpanel.getTurnLabel().setVisible(true);
								dpanel.getPanel().repaint();
							}
							System.out.println("あなたのターン" + NUM);
							if (!enabledbtn) {
								System.out.println("発展カード有効化");
								cpanel.enableDBtns();
								enabledbtn = true;
							}
							// サイコロを振ってカードを配布
							if (!diced) {
								dpanel.getBtn().setEnabled(true);
								dpanel.getPanel().repaint();
							} else {
								// サイコロが7だった
								if (isseven == true) {
									if(!sevenlockbtn) {
										sevenlockbtn = true;
										cpanel.unableBtns();
										ppanel.unableBtns();
									}
									// 所持カードの枚数確認
									int[] cards = new int[5];
									int sum = 0;
									if (!checkcardnum) {
										cards = player[NUM - 1].getResCard();
										for (int i = 0; i < 5; i++) {
											sum = sum + cards[i];
										}
										checkcardnum = true;
									}
									if (droped == false) {
										if (sum < 8) {
											// 処理スキップのため
											droped = true;
											int[] drop = { 0, 0, 0, 0, 0 };
											conn.drop(GAME, NAME, NUM, drop);
										} else {
											if(!sevenlockbtn2) {
												sevenlockbtn2 = true;
											cpanel.unableBtns();
											ppanel.unableBtns();
											}
											DialogFrame df = new DialogFrame(cards, GAME, NAME, NUM);
											df.getAcceptBtn().addActionListener(new ActionListener() {
												public void actionPerformed(ActionEvent e) {
													int[] cards = df.getDropped();
													if (cards[5] != df.getDropNum()) {
														df.getLetter3().setForeground(Color.RED);
														df.getPanel().repaint();
													} else {
														conn.drop(GAME, NAME, NUM, cards);
														droped = true;
														df.dispose();
													}
												}
											});
											df.setVisible(true);
										}
									}
									if (alldroped == false) {
										int b = conn.readJsonToInt(conn.getDrop(GAME), 4);
										if (b >= 4) {
											alldroped = true;
										}
									} else {
										// 盗賊を移動させたか
										if (movethief == false) {
											if (!thiefon) {
												JOptionPane.showMessageDialog(getFrame(), "盗賊を移動させてください", "強盗",
														JOptionPane.PLAIN_MESSAGE);
												thief(true);
												thiefon = true;
											}
										} else {
											// プレイヤーの操作を許可
											if (unlocked == false) {
												if (!useddev) {
													cpanel.enableDBtns();
												}
												cpanel.enableFBtns();
												ppanel.enableBtns();
												unlocked = true;
											}
										}
									}
								} else {
									if (!useddev) {
										cpanel.enableDBtns();
									}
									cpanel.enableFBtns();
									ppanel.enableBtns();
								}
							}
						}
						// not myturn
						else {
							if (!unablebtn) {
								cpanel.unableBtns();
								ppanel.unableBtns();
								unablebtn = true;
							}
							if (isseven) {
								int[] cards = new int[5];
								int sum = 0;
								if (!checkcardnum) {
									cards = player[NUM - 1].getResCard();
									for (int i = 0; i < 5; i++) {
										sum = sum + cards[i];
									}
									checkcardnum = true;
								}
								if (droped == false) {
									if (sum < 8) {
										// 処理スキップのため
										int[] drop = { 0, 0, 0, 0, 0 };
										conn.drop(GAME, NAME, NUM, drop);
										droped = true;
									} else {
										DialogFrame df = new DialogFrame(cards, GAME, NAME, NUM);
										df.getAcceptBtn().addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												int[] drops = df.getDropped();
												if (drops[5] != df.getDropNum()) {
													df.getLetter3().setForeground(Color.RED);
													df.getPanel().repaint();
												} else {
													droped = true;
													conn.drop(GAME, NAME, NUM, drops);
													df.dispose();
												}
											}
										});
										df.setVisible(true);
									}
								}
							}
						}
					}
					// ターン進行
					if (end == true) {
						proceedTurn();
					}
				}
				try {
					Thread.sleep(1000);
				} catch (

				InterruptedException e) {
					e.printStackTrace();
				}
			}
			String[] ok = { "OK" };
			int select = JOptionPane.showOptionDialog(getFrame(), "<"+player[winner].getPlayerName()+">の勝利", "ゲームセット", 0, JOptionPane.PLAIN_MESSAGE,
					null, ok, ok[0]);
			if (select == 0) {
				getFrame().dispose();
				
				System.out.println("終わりだよおおおん");
			}
			//全員押したらDELETE FROM game_list WHERE game_name = '%s';
		}

		public void unlockFBtns() {
			for (int i = 0; i < 5; i++) {
				cpanel.getFBtn(i).setEnabled(true);
			}
		}

		public void proceedTurn() {
			conn.proceedTurn(GAME);
			myturn = false;
			enabledbtn = false;
			unablebtn = false;
			showturnlabel = false;
			diced = false;
			checkcardnum = false;
			useddev = false;
			isseven = false;
			sevenlockbtn = false;
			sevenlockbtn2 = false;
			droped = false;
			alldroped = false;
			movethief = false;
			thiefon = false;
			havenego = false;
			unlocked = false;
			end = false;
			dpanel.getTurnLabel().setVisible(false);
			dpanel.getPanel().repaint();
		}
	}

	class Draw extends Thread {
		// {res,dev,stlm,road,num,nego,chat,dice,turn}
		int[] prevcheck = { 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0};
		int[] currentcheck = { 0, 0, 0, 0, 0, 0, 0, 0, 0,0 };

		public void run() {
			for (int i = 0; i < 9; i++) {
				prevcheck[i] = conn.readJsonToInt(conn.getChecker(GAME), i);
			}
			while (gameend == false) {
				requestAll();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void requestAll() {
			String checkjson = null;
			int num;// DBに前回のリクエストから更新が入ったか確認する (Vn != Vn+1)ならget&repaint checkjson =
			checkjson = conn.getChecker(GAME);
			/*
			 * if (conn.readJsonToInt(checkjson, 7) % 4 == NUM) { myturn = true; }
			 */
			for (int i = 0; i < 10; i++) {
				currentcheck[i] = conn.readJsonToInt(checkjson, i);
				if (prevcheck[i] != currentcheck[i]) {
					switch (i) {
					case 0:
						repaintRes();
						break;
					case 1:
						repaintDev();
						break;
					case 2:
						repaintStlm();
						break;
					case 3:
						repaintRoad();
						break;
					case 4:
						repaintNum();
					case 5:
						// repaintChat();
						// chpanel.getChatPanel().repaint();
						break;
					case 6:
						repaintNego();
						break;
					case 7:
						repaintDice();
						break;
					case 8:
						num = currentcheck[i] % 4;
						if (num == 0) {
							num = 4;
						}
						if (num == NUM) {
							myturn = true;
						} else {
							myturn = false;
						}
						break;
					case 9:
						winner = currentcheck[9] - 1;
						gameend = true;
						break;
					default:
						break;
					}
					prevcheck[i] = currentcheck[i];
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// ok
		private void repaintRes() {
			try {
				String json = conn.getAllRes(GAME);
				int[] res = { 0, 0, 0, 0, 0 };
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 5; j++) {
						res[j] = conn.readJsonToInt(json, i * 5 + j);
					}
					player[i].setResCardNum(res);
				}
				ppanel.setPlayerRes(player);
				int[] res1 = player[NUM - 1].getResCard();
				for (int i = 0; i < 5; i++) {
					cpanel.getResNum(i).setText("×" + res1[i]);
				}
				cpanel.getResPanel().repaint();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// ok
		private void repaintDev() {
			try {
				String json = conn.getAllDev(GAME);
				int[] res = { 0, 0, 0, 0, 0 };
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 5; j++) {
						res[j] = conn.readJsonToInt(json, i * 5 + j);
					}
					player[i].setDevCardNum(res);
					ppanel.setDevNumLabel(player[i]);
				}
				int[] res1 = player[NUM - 1].getDevCard();
				for (int i = 0; i < 5; i++) {
					cpanel.getDevNum(i).setText("×" + res1[i]);
				}
				cpanel.getDevPanel().repaint();
				//最大騎士力 交易路の確認
				String json1 = conn.getKnight(GAME);
				int[] knight = new  int[4];
				int[] road = new int[4];
				for(int i=0; i<4; i++) {
					knight[i] = conn.readJsonToInt(json1, 2*i);
					road[i] = conn.readJsonToInt(json1, 2*i +1);
				}
				int[] ary1 = getMaxValue(knight);
				int[] ary2 = getMaxValue(road);
				if(ary1[2] == 1) {
					if(ary1[1] >= 3) {
						for(int i=0;i<4;i++) {
							if(i == ary1[0]) {
							player[i].setMaxKnight(true);
							ppanel.setMaxKnightlabel(i,1);
						}else {
							player[i].setMaxKnight(false);
							ppanel.setMaxKnightlabel(i,0);
						}
						}
					}
				}
				if(ary2[2] == 1) {
					if(ary2[1] >= 5) {
						for(int i=0;i<4;i++) {
							if(i == ary2[0]) {
							player[i].setLongestRoad(true);
							ppanel.setLongestRoadLabel(i,1);
							
						}else {
							player[i].setLongestRoad(false);
							ppanel.setLongestRoadLabel(i,0);
						}
						}
					}
				}
				ppanel.setKnightNumLabel(knight);
				ppanel.setRoadNumLabel(road);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// ok
		private void repaintStlm() {
			String json = conn.getAllStlm(GAME);
			String poss = null;
			String[] name = { player[0].GetPlayerName(), player[1].GetPlayerName(), player[2].GetPlayerName(),
					player[3].GetPlayerName() };
			for (int i = 0; i < 54; i++) {
				poss = conn.readJsonToStr(json, i * 2);
				if (poss.equals("free") || poss.equals("invalid")) {
					fpanel.getStlm(i).setStatus(5, 0, false);
				} else if (poss.equals(name[0])) {
					if (conn.readJsonToInt(json, i * 2 + 1) == 1) {
						fpanel.getStlm(i).setStatus(1, 0, false);
					} else {
						fpanel.getStlm(i).setStatus(1, 2, false);
					}
				} else if (poss.equals(name[1])) {
					if (conn.readJsonToInt(json, i * 2 + 1) == 1) {
						fpanel.getStlm(i).setStatus(2, 0, false);
					} else {
						fpanel.getStlm(i).setStatus(2, 2, false);
					}
				} else if (poss.equals(name[2])) {
					if (conn.readJsonToInt(json, i * 2 + 1) == 1) {
						fpanel.getStlm(i).setStatus(3, 0, false);
					} else {
						fpanel.getStlm(i).setStatus(3, 2, false);
					}
				} else if (poss.equals(name[3])) {
					if (conn.readJsonToInt(json, i * 2 + 1) == 1) {
						fpanel.getStlm(i).setStatus(4, 0, false);
					} else {
						fpanel.getStlm(i).setStatus(4, 2, false);
					}
				}
			}
			fpanel.getStlmPanel().repaint();
		}

		// ok
		private void repaintRoad() {
			String json = conn.getAllRoad(GAME);
			String poss = null;
			String[] name = { player[0].GetPlayerName(), player[1].GetPlayerName(), player[2].GetPlayerName(),
					player[3].GetPlayerName() };
			for (int i = 0; i < 72; i++) {
				poss = conn.readJsonToStr(json, i);
				if (poss.equals("free")) {
					fpanel.getRoad(i).setStatus(6, 0, false);
				} else if (poss.equals(name[0])) {
					fpanel.getRoad(i).setStatus(1, fpanel.getRoadLocate(i), false);
				} else if (poss.equals(name[1])) {
					fpanel.getRoad(i).setStatus(2, fpanel.getRoadLocate(i), false);
				} else if (poss.equals(name[2])) {
					fpanel.getRoad(i).setStatus(3, fpanel.getRoadLocate(i), false);
				} else if (poss.equals(name[3])) {
					fpanel.getRoad(i).setStatus(4, fpanel.getRoadLocate(i), false);
				}
			}
			fpanel.getRoadPanel().repaint();
		}

		// ok
		private void repaintNum() {
			fpanel.fetchTiles(GAME);
			fpanel.getTilePanel().repaint();
		}

		private void repaintNego() {
			if (!myturn) {
				String json = conn.getNegoRequst(GAME, NAME);
				if (!json.equals("norequest")) {
					String from = conn.readJsonToStr(json, 0);
					int[][] res = { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } };
					for (int i = 0; i < 2; i++) {
						for (int j = 0; j < 5; j++) {
							res[i][j] = conn.readJsonToInt(json, 3 + 5 * i + j);
						}
					}

					DialogFrame df = new DialogFrame(from, res[1], res[0]);
					df.getAcceptBtn().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							conn.answerNego(GAME, from, NAME, res[0], res[1], "accepted");
							df.dispose();
						}
					});
					df.getCancelBtn().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							conn.answerNego(GAME, from, NAME, res[0], res[1], "denied");
							df.dispose();
						}
					});
					df.setVisible(true);
				}
			} else {
				String json = conn.getNegoAnswer(GAME, NAME);
				if (json.equals("noanswer")) {
					int num = conn.readJsonToInt(json, 0);
					for (int i = 0; i < num; i++) {
						String target = conn.readJsonToStr(json, 1 + 2 * i);
						String result = conn.readJsonToStr(json, 2 + i * 2);
						switch (result) {
						case "accepted":
							JOptionPane.showMessageDialog(getFrame(), target + "との取引は<承諾>されました", "取引結果",
									JOptionPane.PLAIN_MESSAGE);
							break;
						case "denied":
							JOptionPane.showMessageDialog(getFrame(), target + "との取引は<拒否>されました", "取引結果",
									JOptionPane.PLAIN_MESSAGE);
							break;
						}
					}
				}
			}
		}

		// ok??
		private void repaintDice() {
			String json = conn.getDice(GAME);
			if (conn.readJsonToInt(json, 0) != -1) {
				int[] dice = { conn.readJsonToInt(json, 1), conn.readJsonToInt(json, 2) };
				dpanel.setDiceLabel(dice[0], dice[1]);
				if (dice[0] + dice[1] == 7) {
					isseven = true;
				}
			}
		}
	
		//return index val count
		private int[] getMaxValue(int[] values) {
			int index = 0;
			int val = values[0];
			int count = 0;
			int[] ret = {0,0,0};
			for(int i=1;i<values.length;i++) {
				if(values[i]>val) {
					index=i;
					val=values[i];
				}
			}
			for(int i=0;i<values.length;i++) {
				if(values[i]==val) {
					count++;
				}
			}
			ret[0] = index;
			ret[1] = val;
			ret[2] = count;
			System.out.println(ret[0] +","+ret[1]+","+ret[2]);
			return ret;
		}
	}
}