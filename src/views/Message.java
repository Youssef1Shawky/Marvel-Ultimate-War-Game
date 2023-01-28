package views;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Message extends JFrame implements ActionListener{
	
	JButton ok;
	JFrame game;
	
	public Message(String s , JFrame g) {
		
		game = g;
		game.setEnabled(false);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(565,255);
		this.setUndecorated(true);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setBackground(new Color(0, 0, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBounds(0 , 0 , 565	 , 255);
		panel.setOpaque(false);
		
		JPanel panel2 = new JPanel();
		panel2.setBounds(0 , 110 , 565, 500);
		panel2.setOpaque(false);
		panel2.setLayout(new FlowLayout());
		
		JLabel frame = new JLabel(new ImageIcon("Alert1.png"));
		panel.add(frame);
		
		JLabel text = new JLabel(s);
		text.setFont(new Font("Friz Quadrata TT" , Font.BOLD , 23));
		text.setForeground(new Color(0x999487));
		
		String[] a = s.split(" ");
		String tmp = "" , tmp2 = "";
		
		if(s.length()>50) {
			for(int i=0; i<a.length ;i++) {
				if(i < 9)
					tmp += a[i]+" ";
				else
					tmp2 += a[i]+" ";
			}
			text.setText(tmp);
			JLabel text2 = new JLabel(s);
			text2.setFont(new Font("Friz Quadrata TT" , Font.BOLD , 24));
			text2.setForeground(new Color(0x999487));
			text2.setText(tmp2);
			panel2.add(text);
			panel2.add(text2);
		}
		else
			panel2.add(text);

		

		this.ok = new JButton(); 
		ok.setBounds(244 , 211 , 78 ,40);
		ok.setIcon(new ImageIcon("ok.png"));
		ok.addActionListener(this);
		
		this.add(ok);
		this.add(panel2);
		this.add(panel);
		
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ok) {
			game.setEnabled(true);
			this.setVisible(false);
		}
	}
	
}
