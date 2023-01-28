package views;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class Bar extends JProgressBar{
	public Bar(int min , int max , int current , int x , int y , Color c) {
		Border border = BorderFactory.createLineBorder(Color.green);
		
		this.setMinimum(min);
		this.setMaximum(max);
		
		this.setValue(current);
		this.setBounds(x , y , 132 , 15);
		this.setStringPainted(true);
		this.setFont(new Font("",Font.BOLD,10));
		this.setForeground(c);
		this.setBackground(Color.black);
		this.setBorder(border);
		this.setString(current+"/"+max);
		
	}
}
