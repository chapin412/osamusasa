package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ChatPanel {
	
	private JScrollPane scroll = new JScrollPane();
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel();
	private JButton btn = new JButton("送信");
	private JTextField txtfield = new JTextField();
	
	ChatPanel() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		panel.setPreferredSize(new Dimension(400*screen.width/1920,360*screen.height/1080));
		panel.setBackground(new Color(150,200,245));
		panel.setBorder(new LineBorder(Color.BLUE));
		label.setText("");
		label.setBackground(new Color(255,255,255));
		scroll.setViewportView(label);
		scroll.setPreferredSize(new Dimension(380*screen.width/1920,260*screen.height/1080));
		scroll.setOpaque(true);
		panel.add(scroll);
		txtfield.setPreferredSize(new Dimension(350*screen.width/1920,20*screen.height/1080));
		panel.add(txtfield);
		panel.add(btn);	
	}
	public JPanel getChatPanel() {
		return panel;
	}

}
