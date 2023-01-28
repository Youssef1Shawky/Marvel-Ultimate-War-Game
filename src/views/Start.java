package views;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;

public class Start extends JLayeredPane{
	
	private JButton playButton;

	public Start() {
		
		JLabel backGround = new JLabel();
		backGround.setBounds(0 , 0 , 1280 , 720);
		backGround.setIcon(new ImageIcon("playWall.jpg"));
		
		playButton = new JButton("");
		playButton.setBounds(552 , 500 , 175 , 50);
		playButton.setIcon(new ImageIcon("play.png"));
		
		this.setBounds(0 , 0 , 1280 , 720);
		this.add(playButton);
		this.add(backGround);
	}
	
	public void setPlayButton(JButton playButton) {
		this.playButton = playButton;
	}

	public JButton getPlayButton() {
		return playButton;
	}
}
