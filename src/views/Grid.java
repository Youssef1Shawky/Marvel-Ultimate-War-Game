package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

import engine.Game;
import engine.Player;
import model.abilities.Ability;
import model.effects.Effect;
import model.world.*;

public class Grid extends JPanel {

	JLabel[][] cells = new JLabel[5][5];
	JProgressBar[][] bars;
	Object[][] board;
	int x, y;
	Player p1 , p2;

	public Grid(Object[][] board) {
		this.setLayout(new GridLayout(5, 5, 10, 10));
		this.setBounds(610, 190, 700, 700);

		//this.setBackground(Color.white);
		this.setOpaque(false);
		this.board = board;
	}
	
	public String championInfo(Champion c) {
		String s = "<html>";
		 String type = c.getClass()+"";
		 s += c.getName() + "<br>" + type.substring(18 , type.length()) + "<br> CurrentHp: "+c.getCurrentHP() 
		 				  + "<br> Mana: "
				 		  + c.getMana() + "<br> Max Actions: "+c.getMaxActionPointsPerTurn()
				 		  + "<br> Speed: "+c.getSpeed() + "<br> Attack Range: " + c.getAttackRange()
				 		  + "<br> Attack Damage: " + c.getAttackDamage()+"<br>";
		 if(p1.getLeader().equals(c))
			 s += "Leader <br> Applied Effects: <br>";
		 else if(p2.getLeader().equals(c))
			 s+= "Leader <br> Applied Effects: <br>";
		 else
			 s += "Not Leader <br> Applied Effets: <br>";
		for(Effect e : c.getAppliedEffects()) {
			s += "-"+e.getName()+"<br>"+"	    Duration: "+e.getDuration()+"<br>";
		}
		return s;
	}


	public void change() {
		this.removeAll();
		this.repaint();
		this.revalidate();
		for (int i = 4; i >=0 ; i--) {
			for (int j = 0; j < 5; j++) {
				Border border = BorderFactory.createLineBorder(new Color(0xc8a96c));
				cells[i][j].removeAll();
				cells[i][j].setLayout(null);
				if (board[i][j] == null) {
					cells[i][j].setForeground(null);
					cells[i][j].setText("Empty Cell");
					cells[i][j].setIcon(new ImageIcon("emptyCell.png"));
					
				} else {
					Damageable d = (Damageable) board[i][j];
					
					Bar bar = new Bar(0, d.getMaxHP(), d.getCurrentHP() , 0 , 117 , Color.red);
					
					if (board[i][j] instanceof Champion) {
						Champion c = (Champion)d;
						cells[i][j].setIcon(new ImageIcon(((Champion) board[i][j]).getName()+"2.png"));	
						cells[i][j].setToolTipText(championInfo(c));
						if(p1.getTeam().contains(c)) {
							cells[i][j].setForeground(Color.blue);
						}
						else {
							bar.setForeground(Color.BLUE);
							cells[i][j].setForeground(Color.RED);
						}
					} else {
						bar.setForeground(Color.DARK_GRAY);
						cells[i][j].setIcon(new ImageIcon("cover1.png"));	
						//cells[i][j].setText("Cover");
					}
					cells[i][j].add(bar);
				}
				cells[i][j].setHorizontalAlignment(JLabel.CENTER);
				//cells[i][j].setBorder(border);
				this.add(cells[i][j]);

			}
		}
	}
}
