package views;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import engine.*;
import model.abilities.Ability;
import model.world.*;

public class SelectChampions extends JFrame implements ActionListener, MouseListener {

	ArrayList<JLabel> championsButtons, leader1, leader2;
	ArrayList<Champion> availableChampions;
	JPanel championsPanel, leaderPanel1, leaderPanel2 , title;
	JLabel background, info, champType, next, play , playerName , text;
	Game game;
	Player p1, p2;
	boolean f, f2;

	public SelectChampions(Game game) {
		this.setIconImage(new ImageIcon("img.png").getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setLayout(null);
		this.getContentPane().setBackground(Color.white);

		background = new JLabel();
		background.setBounds(0, 0, 1920, 1080);
		background.setIcon(new ImageIcon("background.png"));

		this.game = game;
		p1 = game.getFirstPlayer();
		p2 = game.getSecondPlayer();

		title = new JPanel();
		title.setBounds(150, 0, 650, 200);
		title.setOpaque(false);
		title.setLayout(new FlowLayout());
	

		
		text = new JLabel("Choose your Champions");
		text.setFont(new Font("Friz Quadrata TT", Font.BOLD, 50));
		
		
		playerName = new JLabel(p1.getName());
		playerName.setFont(new Font("Friz Quadrata TT", Font.BOLD, 50));
		
		title.add(text);
		title.add(playerName);
		
		info = new JLabel();
		champType = new JLabel();

		next = new JLabel();
		next.setBounds(795, 1000, 330, 50);
		next.setIcon(new ImageIcon("next0.png"));
		next.addMouseListener(this);
		next.setVisible(false);

		play = new JLabel();
		play.setBounds(795, 1000, 330, 50);
		play.setIcon(new ImageIcon("play0.png"));
		play.addMouseListener(this);
		play.setVisible(false);

		championsPanel = new JPanel();
		championsPanel.setBounds(75, 150, 870, 810);
		championsPanel.setOpaque(false);
		championsPanel.setLayout(new GridLayout(3, 5, 30, 30));

		leaderPanel1 = new JPanel();
		leaderPanel1.setBounds(200, 200, 650, 250);
		leaderPanel1.setOpaque(false);
		leaderPanel1.setLayout(new GridLayout(1, 3, 100, 30));

		leaderPanel2 = new JPanel();
		leaderPanel2.setBounds(200, 600, 650, 250);
		leaderPanel2.setOpaque(false);
		leaderPanel2.setLayout(new GridLayout(1, 3, 100, 30));

		availableChampions = game.getAvailableChampions();

		this.add(play);
		this.add(info);
		this.add(next);
		this.add(title);

		this.add(leaderPanel1);
		this.add(leaderPanel2);

		this.add(championsPanel);
		this.add(background);
		addChampions();

		this.setVisible(true);
	}

	public void addChampions() {
		championsButtons = new ArrayList<>();
		leader1 = new ArrayList<>();
		leader2 = new ArrayList<>();
		Border border = BorderFactory.createLineBorder(Color.WHITE);
		for (Champion c : availableChampions) {
			if (p1.getTeam().contains(c) && !f2)
				continue;
			JLabel championButton = new JLabel(c.getName());
			championButton.setIcon(new ImageIcon(c.getName() + "0.png"));
			championButton.setSize(156, 240);
			championButton.addMouseListener(this);
			championButton.setForeground(Color.white);
			championButton.setOpaque(false);
			championsButtons.add(championButton);
			if (p1.getTeam().contains(c))
				leader1.add(championButton);
			if (p2.getTeam().contains(c))
				leader2.add(championButton);
		}
		for (JLabel label : championsButtons) {
			championsPanel.add(label);
		}
		if (championsButtons.size() == 12)
			championsPanel.add(new JLabel());
		this.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public String championInfo(String x) {
		String s = "<html>";
		Champion c = null;
		for (Champion champ : availableChampions) {
			if (champ.getName().equals(x)) {
				c = champ;
				break;
			}
		}
		String type = c.getClass() + "";
		champType.setText(type.substring(18, type.length()));
		champType.setFont(new Font("Friz Quadrata TT", Font.BOLD, 40));
		if (c instanceof Hero)
			champType.setBounds(110, 60, 200, 50);
		else if (c instanceof AntiHero)
			champType.setBounds(65, 60, 200, 50);
		else
			champType.setBounds(100, 60, 200, 50);
		s += "MaxHp: " + c.getMaxHP() + "<br> Mana: " + c.getMana() + "<br> Actions: " + c.getMaxActionPointsPerTurn()
				+ "<br> Speed: " + c.getSpeed() + "<br> Attack Range: " + c.getAttackRange() + "<br> Attack Damage: "
				+ c.getAttackDamage() + "<br> Abilities: <br>";
		for (Ability a : c.getAbilities()) {
			s += "-" + a.getName() + "<br>";
		}
		return s;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == play) {	
			game.placeChampions();
			new GameView(game);
			this.setVisible(false);
		}
		else if (e.getSource() == next && !f2) {
			championsPanel.removeAll();
			championsPanel.repaint();
			championsPanel.revalidate();
			playerName.setText(p2.getName());
			for (JLabel label : championsButtons) {
				label.setIcon(new ImageIcon(label.getText() + "0.png"));
				label.setEnabled(true);
				label.addMouseListener(this);
			}
			addChampions();
			next.setVisible(false);
			this.revalidate();
			this.repaint();
		} else if (e.getSource() == next) {
			playerName.setVisible(false);
			text.setText("Choose your Leader");
			text.setFont(new Font("Friz Quadrata TT", Font.BOLD, 70));
			title.setBounds(120 , 50 , 800 , 200);
			addChampions();
			for (JLabel label : championsButtons) {
				label.setIcon(new ImageIcon(label.getText() + "0.png"));
				label.setEnabled(true);
				label.addMouseListener(this);
			}
			championsPanel.setVisible(false);
			next.setVisible(false);
			for (JLabel label : leader1) {
				leaderPanel1.add(label);
			}
			for (JLabel label : leader2) {
				leaderPanel2.add(label);
			}
			this.repaint();
			this.revalidate();
		} else if (p1.getTeam().size() < 3) {
			for (int i = 0; i < championsButtons.size(); i++) {
				if (e.getSource() == championsButtons.get(i)) {
					for (Champion c : availableChampions) {
						if (c.getName().equals(championsButtons.get(i).getText())) {
							p1.getTeam().add(c);
							break;
						}
					}
					championsButtons.get(i).setEnabled(false);
					championsButtons.get(i).removeMouseListener(this);
					info.setVisible(false);
				}
			}
			if (p1.getTeam().size() == 3) {
				for (JLabel label : championsButtons) {
					label.setIcon(new ImageIcon(label.getText() + "01.png"));
					label.setEnabled(false);
					label.removeMouseListener(this);
				}
				f = true;
				next.setVisible(true);
			}
		} else if (p2.getTeam().size() < 3) {
			for (int i = 0; i < championsButtons.size(); i++) {
				if (e.getSource() == championsButtons.get(i)) {
					for (Champion c : availableChampions) {
						if (c.getName().equals(championsButtons.get(i).getText())) {
							p2.getTeam().add(c);
							break;
						}
					}
					championsButtons.get(i).setEnabled(false);
					championsButtons.get(i).removeMouseListener(this);
					info.setVisible(false);
				}
			}
			if (p2.getTeam().size() == 3) {
				for (JLabel label : championsButtons) {
					label.setIcon(new ImageIcon(label.getText() + "01.png"));
					label.setEnabled(false);
					label.removeMouseListener(this);
				}
				f2 = true;
				next.setVisible(true);
			}
		} else {
			here: for (JLabel label : leader1) {
				if (e.getSource() == label) {
					for (Champion c : p1.getTeam()) {
						if (c.getName().equals(label.getText())) {
							p1.setLeader(c);
							for (JLabel label2 : leader1) {
								label2.setIcon(new ImageIcon(label2.getText() + "01.png"));
								label2.setEnabled(false);
								label2.removeMouseListener(this);
							}
							break here;
						}
					}
				}
			}
			here: for (JLabel label : leader2) {
				for (Champion c : p2.getTeam()) {
					if (e.getSource() == label) {
						if (c.getName().equals(label.getText())) {
							p2.setLeader(c);
							for (JLabel label2 : leader2) {
								label2.setIcon(new ImageIcon(label2.getText() + "01.png"));
								label2.setEnabled(false);
								label2.removeMouseListener(this);
							}
							break here;
						}
					}
				}
			}
			if(p1.getLeader() != null && p2.getLeader() != null)
				play.setVisible(true);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		for (JLabel label : championsButtons) {
			if (e.getSource() == label) {
				label.setIcon(new ImageIcon(label.getText() + "01.png"));
				if (championsPanel.isVisible()) {
					Rectangle r = label.getBounds();
					info.setVisible(true);
					info.removeAll();
					info.setIcon(new ImageIcon("window3.png"));
					info.setBounds(230 + r.x, 10 + r.y, 500, 500);
					JLabel infor = new JLabel(championInfo(label.getText()));
					infor.setBounds(28, 70, 300, 400);
					infor.setForeground(new Color(0x86692d));
					infor.setFont(new Font("Friz Quadrata TT", Font.BOLD, 24));
					info.add(champType);
					info.add(infor);
				}
			}
		}
		if (e.getSource() == next) {
			next.setIcon(new ImageIcon("next01.png"));
		}
		if (e.getSource() == play) {
			play.setIcon(new ImageIcon("play01.png"));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		for (JLabel label : championsButtons) {
			if (e.getSource() == label) {
				label.setIcon(new ImageIcon(label.getText() + "0.png"));
				info.setVisible(false);
				info.removeAll();
			}
		}
		if (e.getSource() == next) {
			next.setIcon(new ImageIcon("next0.png"));
		}
		if (e.getSource() == play) {
			play.setIcon(new ImageIcon("play0.png"));
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
}
