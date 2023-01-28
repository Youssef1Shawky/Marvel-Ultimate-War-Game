package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.TextUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.View;
import javax.swing.text.Position.Bias;

import engine.Game;
import engine.Player;
import model.world.Champion;


public class GameFrame extends JFrame implements ActionListener , MouseListener {
	
	private Player firstPlayer , secondPlayer;
	private Game game;
	
	private JTextField firstPlayerName , secondPlayerName;
	
	private JButton next;
	
	JLabel secondText , firstText;
	
	private Clip clip;

	public GameFrame() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		ImageIcon img = new ImageIcon("img.png");
		
		this.setTitle("Marvel Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setIconImage(img.getImage());
		this.getContentPane().setBackground(Color.white);
		this.setLayout(null);
		
		JLabel logo = new JLabel();
		logo.setBounds(120 , 100 , 250 , 150);
		logo.setIcon(new ImageIcon("logo1.png"));
		
		firstPlayerName = new JTextField(20);
		firstPlayerName.setBounds(80, 360, 340, 30);
		firstPlayerName.setFont(new Font("" , Font.BOLD , 15));
		firstPlayerName.setOpaque(false);
		firstPlayerName.setBorder(null);
		firstPlayerName.addMouseListener(this);
			
		firstText = new JLabel(new ImageIcon("firstText1.png"));
		firstText.setBounds(70 , 340 , 360 , 60);
		
		secondPlayerName = new JTextField(20);
		secondPlayerName.setBounds(80 , 472 , 340 , 30);
		secondPlayerName.setFont(new Font("" , Font.BOLD , 15));
		secondPlayerName.setOpaque(false);
		secondPlayerName.setBorder(null);
		secondPlayerName.addMouseListener(this);
		
		
		secondText = new JLabel(new ImageIcon("secondText.png"));
		secondText.setBounds(70 , 455 , 360 , 60);
		
		
		next = new JButton();
		next.setBounds(210 , 650 , 80 , 80);
		next.setIcon(new ImageIcon("next1.png"));
		next.addMouseListener(this);
		next.addActionListener(this);
		next.setOpaque(false);
		next.setContentAreaFilled(false);
		next.setBorderPainted(false);
		
		
		JLabel label1 = new JLabel();
		label1.setBounds(500 , 0 , 1920 , 1080);
		label1.setIcon(new ImageIcon("wall41.png"));
		
		this.addMouseListener(this);

		this.add(firstPlayerName);
		
		this.add(secondPlayerName);
		this.add(firstText);
		this.add(secondText);
		this.add(logo);
		this.add(next);
		this.add(label1);
		
		this.setVisible(true);
		
		File file = new File("start.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		clip = AudioSystem.getClip();
		clip.open(audioStream);
		clip.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == next ) {
			if(!firstPlayerName.getText().isEmpty() && !secondPlayerName.getText().isEmpty()) {
				
				firstPlayer = new Player(firstPlayerName.getText());
				secondPlayer = new Player(secondPlayerName.getText());
				game = new Game(firstPlayer, secondPlayer);
	
				this.getContentPane().removeAll();
                this.revalidate();
                this.repaint();
                
                new SelectChampions(game);
                this.setVisible(false);
			}
		}
		this.revalidate();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getComponent() == secondPlayerName) {
			secondText.setIcon(new ImageIcon("secondText1.png"));
		}
		else {
			if(secondPlayerName.getText().isEmpty())
				secondText.setIcon(new ImageIcon("secondText.png"));
			else
				secondText.setIcon(new ImageIcon("secondText2.png"));
		}
		if(e.getComponent() == firstPlayerName) {
			firstText.setIcon(new ImageIcon("firstText1.png"));
		}
		else {
			if(firstPlayerName.getText().isEmpty())
				firstText.setIcon(new ImageIcon("firstText.png"));
			else
				firstText.setIcon(new ImageIcon("firstText2.png"));
		}
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getComponent() == next)
			next.setIcon(new ImageIcon("next.png"));
	}



	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getComponent() == next)
			next.setIcon(new ImageIcon("next1.png"));
		
	}
	
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		GameFrame gameFrame = new GameFrame();
	}
}
