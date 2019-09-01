package application;

public class Player {

	private String player_name;
	private int pnum;
	// [0]: Tree, [1]:Bricks, [2]:Wheat, [3]:Sheep, [4]:,Iron
	private int[] rescardnum = new int[5];
	// [0]: Night, [1]:Construct, [2]:Discover, [3]:Monopoly, [4]:Point
	private int[] devcardnum = new int[5];
	private int amountstlm = 0;//最大5個
	private int amountcity =0;
	private int knightnum = 0;
	private int roadnum = 0;
	private boolean maxknight = false;
	private boolean longestroad = false;
	// 勝利点
	private int victorypoint;

	Player(String name, int num) {
		player_name = name;
		pnum = num;
		for (int i = 0; i < 5; i++) {
			rescardnum[i] = 0;
			devcardnum[i] = 0;
		}
		knightnum = 0;
	}

	public void setResCardNum(int[] res) {
		for (int i = 0; i < 5; i++) {
			rescardnum[i] = res[i];
		}
	}
	
	public void setDevCardNum(int[] res) {
		for (int i = 0; i < 5; i++) {
			devcardnum[i] = res[i];
		}
	}

//get value methods
	String GetPlayerName() {
		return player_name;
	}

	int getPlayerNum() {
		return pnum;
	}

	int[] getResCard() {
		return rescardnum;
	}
	
	int[] getDevCard() {
		return devcardnum;
	}
	
	int getPoints() {
		int point =0;
		point = point + amountstlm + 2*amountcity + devcardnum[4];
		if(maxknight) {
			point = point + 2;
		}
		if(longestroad) {
			point = point + 2;
		}
		return point;
	}
	
	String getPlayerName() {
		return player_name;
	}

	public int getAmountOfStlms() {
		return amountstlm;
	}

	public void constStlm() {
		amountstlm++;
	}
	public void constCity() {
		amountstlm--;
		amountcity++;
	}

	public void setMaxKnight(boolean b) {
		maxknight = b;
	}

	public void setLongestRoad(boolean b) {
		longestroad = b;
	}

	public int getSumDev() {
		return devcardnum[0]+devcardnum[1]+devcardnum[2]+devcardnum[3]+devcardnum[4];
	}
}
