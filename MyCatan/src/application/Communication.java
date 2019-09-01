package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import javax.net.ssl.HttpsURLConnection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Communication {
	final String baseURL = ("https://mycatan.herokuapp.com/"); // heroku app url

	public String request(String... options) throws Exception {
		String ret = "";
		try {
			String urlOption = "?";
			for (int i = 0; i < options.length - 2; i += 2) {
				urlOption = urlOption + options[i] + "=" + options[i + 1] + "&";
			}
			urlOption = urlOption + options[options.length - 2] + "=" + options[options.length - 1];
			System.out.println(urlOption);
			URL url = new URL(baseURL + urlOption);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setConnectTimeout(100000);
			conn.setDoOutput(false);
			conn.setDoInput(true);
			ret = new BufferedReader(new InputStreamReader(conn.getInputStream())).readLine();
			System.out.println(ret);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public String readJsonToStr(String json, int index) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(json);
			String value = node.get(index).asText();
			return value;
		} catch (IOException e) {
			e.printStackTrace();
			return "Error";
		}
	}

	public int readJsonToInt(String json, int index) {
		ObjectMapper mapper = new ObjectMapper();
		int value = -1;
		try {
			JsonNode node = mapper.readTree(json);
			value = node.get(index).asInt();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public void registerUserName(String id, String name) {
		try {
			request("register", "true", "user_id", id, "user_name", name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String createGame(String name, String host, String mode, String pwd, String random) {
		String ret = "";
		try {
			ret = request("creategame", "true", "game_name", name, "host_name", host, "private", mode, "password", pwd,
					"map", random);
		} catch (Exception e) {
			System.exit(0);
		}
		return ret;
	}

	public String joinGame(String name1, String name2) {
		String ret = "";
		try {
			ret = request("joinGame", "true", "game_name", name1, "user_name", name2);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public String deleteGame(String name) {
		String ret = "";
		try {
			ret = request("deleteGame", "true", "game_name", name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	// init DB
	public String startGame(String game) {
		String ret = "";
		String turn = String.valueOf(new Random().nextInt(4) + 1);
		try {
			ret = request("init", "true", "game_name", game, "turn", turn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public String getPlayerName(String name) {
		String json = "";
		try {
			json = request("getPlayerName", "true", "game_name", name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public void sendStlmInfo(String game) {
		try {
			request("stlm", "true", "game_name", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendRoadInfo(String game) {
		try {
			request("road", "true", "game_name", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String fetchTiles(String game) {
		String json = "";
		try {
			json = request("fetchtiles", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public void drop(String game, String name, int num, int[] cards) {
		try {
			request("drop", game, "name", name, "num", String.valueOf(num), "tree", String.valueOf(cards[0]), "brick",
					String.valueOf(cards[1]), "wheat", String.valueOf(cards[2]), "sheep", String.valueOf(cards[3]),
					"iron", String.valueOf(cards[4]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDrop(String game) {
		String json = null;
		try {
			json = request("getdrop", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public String checkalldroped(String game, int turn) {
		String json = null;
		try {
			json = request("checkalldrop", game, "turn", String.valueOf(turn));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public String getGameName() {
		String json = "";
		try {
			json = request("getGameName", "true");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public void constructStml(String game, String name, int index, int type, int x, int y, int z, int rest, int num) {
		try {
			request("conststlm", game, "player", name, "index", String.valueOf(index), "type", String.valueOf(type),
					"x", String.valueOf(x), "y", String.valueOf(y), "z", String.valueOf(z), "rest",
					String.valueOf(rest), "num", String.valueOf(num));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void useCard(String game, String name, int[] cards) {
		try {
			request("usecard", game, "player", name, "tree", String.valueOf(cards[0]), "brick",
					String.valueOf(cards[1]), "wheat", String.valueOf(cards[2]), "sheep", String.valueOf(cards[3]),
					"iron", String.valueOf(cards[4]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void useDev(String game, String name, String kind) {
		try {
			request("usedev", game, "name", name, "kind", kind);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void constructRoad(String game, String name, int index, int stlm1, int stlm2, int num) {
		try {
			request("constroad", game, "player", name, "index", String.valueOf(index), "stlm1", String.valueOf(stlm1),
					"stlm2", String.valueOf(stlm2), "num", String.valueOf(num));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void constCity(String game, int n) {
		try {
			request("constcity", game, "index", String.valueOf(n));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void broadcastStart(String game) {
		try {
			request("broadcaststart", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFirst(String game, int num) {
		String json = null;
		try {
			json = request("getfirst", game, "player", "p" + String.valueOf(num));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public void countFirst(String game) {
		try {
			request("countfirst", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCardFirst(String game, int index, String name) {
		try {
			request("updatecardfirst", game, "index", String.valueOf(index), "name", name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void discover(String game, String name, int[] res) {
		try {
			request("discover", game, "name", name, "tree", String.valueOf(-res[0]), "brick", String.valueOf(-res[1]),
					"wheat", String.valueOf(-res[2]), "sheep", String.valueOf(-res[3]), "iron",
					String.valueOf(-res[4]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void monopoly(String game, String name, String res) {
		try {
			request("monopoly", game, "name", name, "res", res);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getChecker(String game) {
		String json = null;
		try {
			json = request("getchecker", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public String getAllRes(String game) {
		String json = null;
		try {
			json = request("getallres", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public String getAllDev(String game) {
		String json = null;
		try {
			json = request("getalldesv", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public String getAllStlm(String game) {
		String json = null;
		try {
			json = request("getallstlm", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public String getAllRoad(String game) {
		String json = null;
		try {
			json = request("getallroad", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public String getDice(String game) {
		String json = null;
		try {
			json = request("getdice", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public String changeNum(String game, String name, int index) {
		String json = null;
		try {
			json = request("changenum", game, "name", name, "index", String.valueOf(index));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public String thief(String game, String name, int possesion) {
		String res = null;
		try {
			String json = request("thief", game, "target", String.valueOf(possesion));
			if (readJsonToInt(json, 0) != 0) {
				int index = new Random().nextInt(readJsonToInt(json, 0)) + 1;
				res = readJsonToStr(json, index);
				request("steel", game, "name", name, "target", String.valueOf(possesion), "res", res);
			} else {
				res = "nores";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public String acquiredev(String game, String name) {
		String json = null;
		try {
			json = request("acquiredev", game, "name", name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public String getTradeRate() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void proceedTurn(String game) {
		try {
			request("proceed", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startTurn(String game) {
		try {
			request("startturn", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendNegoRequest(String game, String from, String target, int[] res1, int[] res2) {
		try {
			request("sendnegorequest", game, "from", from, "target", target, "t1", String.valueOf(res1[0]), "b1",
					String.valueOf(res1[1]), "w1", String.valueOf(res1[2]), "s1", String.valueOf(res1[3]), "i1",
					String.valueOf(res1[4]), "t2", String.valueOf(res2[0]), "b2", String.valueOf(res2[1]), "w2",
					String.valueOf(res2[2]), "s2", String.valueOf(res2[3]), "i2", String.valueOf(res2[4]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getNegoRequst(String game, String name) {
		String json = "norequest";
		try {
			json = request("getnegorequest", game, "name", name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public void answerNego(String game, String from, String target, int[] res1, int[] res2, String answer) {
		try {
			request("answernego", game, "from", from, "target", target, "t1", String.valueOf(res1[0]), "b1",
					String.valueOf(res1[1]), "w1", String.valueOf(res1[2]), "s1", String.valueOf(res1[3]), "i1",
					String.valueOf(res1[4]), "t2", String.valueOf(res2[0]), "b2", String.valueOf(res2[1]), "w2",
					String.valueOf(res2[2]), "s2", String.valueOf(res2[3]), "i2", String.valueOf(res2[4]), "answer",
					answer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getNegoAnswer(String game, String name) {
		String json = "noanswer";
		try {
			json = request("getnegoanswer", game, "name", name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public String getKnight(String game) {
		String json = null;
		try {
			json = request("getknight", game);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public void sendPoint(String game, String name, int point, int num) {
		try {
			request("sendpoint",game,"name",name,"point",String.valueOf(point),"winner",String.valueOf(num));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
