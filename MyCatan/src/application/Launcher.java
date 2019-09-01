package application;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Launcher extends JFrame {
	static public void main(String[] args) {
		String game = "test";
		Communication conn = new Communication();
		conn.createGame(game,"osamusasa", "false", null, "true");
		conn.joinGame(game, "nkepin");
		conn.joinGame(game, "tuefri");
		conn.joinGame(game, "snowtantan");
		conn.startGame(game);
		GameFrame gamef1 = new GameFrame(game,"osamusasa",true,true);
		gamef1.setVisible(true);
		GameFrame gamef2 = new GameFrame(game,"nkepin",false,true);
		gamef2.setVisible(true);
		GameFrame gamef3 = new GameFrame(game,"tuefri",false,true);
		gamef3.setVisible(true);
		GameFrame gamef4 = new GameFrame(game,"snowtantan",false,true);
		gamef4.setVisible(true);
		//LobbyFrame lobbyf = new LobbyFrame();
		//lobbyf.setVisible(true);
	}
}