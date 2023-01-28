package views;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.management.ImmutableDescriptor;
import javax.swing.*;
import javax.swing.border.Border;

import engine.Game;
import engine.Player;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.Effect;
import model.world.Champion;
import model.world.Cover;
import model.world.Damageable;
import model.world.Direction;

public class GameView extends JFrame implements ActionListener, MouseListener {
	Game game;
	Grid grid;

	int z;
	JLabel up, down, left, right, attack;
	JLabel firstAbility, secondAbility, thirdAbility ,  endTurn, useLeader;

	Ability a1, a2, a3;
	JLabel[][] cells = new JLabel[5][5];
	JLabel currentChampion , currentChampionInfo , firstPlayer , secondPlayer , abilityInfo , currentChampionName;
	JLabel ability1 = new JLabel(), ability2 = new JLabel(), ability3 = new JLabel();
	JLabel turn , effects;
	
	JPanel appliedEffects , turnOrder;
	
	boolean f, f2, f3;

	public GameView(Game game) {
		this.setIconImage(new ImageIcon("img.png").getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setLayout(null);
		
		this.game = game;
		Object[][] board = game.getBoard();
		
		this.grid = new Grid(game.getBoard());
		for(int i=0; i<5; i++) {
			for(int j=0; j<5 ;j++) {
				cells[i][j]= new JLabel(); 
				cells[i][j].addMouseListener(this);
			}
		}
		grid.cells = this.cells;
		grid.p1 = game.getFirstPlayer();
		grid.p2 = game.getSecondPlayer();
		grid.addMouseListener(this);
		grid.change();

		attack = new JLabel("Attack");
		attack.setBounds(1450, 920, 100, 100);
		attack.setIcon(new ImageIcon("attack1.png"));
		attack.addMouseListener(this);
		this.add(attack);
		
		up = new JLabel("up");
		up.setBounds(1475, 880, 70, 50);
		up.setFocusable(false);
		up.setIcon(new ImageIcon("up.png"));
		up.addMouseListener(this);
		this.add(up);

		left = new JLabel("left");
		left.setBounds(1400, 945, 70, 50);
		left.setFocusable(false);
		left.setIcon(new ImageIcon("left.png"));
		left.addMouseListener(this);
		this.add(left);

		right = new JLabel("right");
		right.setBounds(1550, 945, 70, 50);
		right.setIcon(new ImageIcon("right.png"));;
		right.addMouseListener(this);
		this.add(right);

		down = new JLabel("down");
		down.setBounds(1475, 1010, 70, 50);
		down.setIcon(new ImageIcon("down.png"));
		down.addMouseListener(this);
		this.add(down);

		firstAbility = new JLabel();
		firstAbility.setHorizontalTextPosition(JLabel.CENTER);
		firstAbility.setIcon(new ImageIcon("label3.png"));
		firstAbility.setForeground(Color.white);
		firstAbility.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 25));
		firstAbility.setBounds(1340, 240, 330, 50);
		firstAbility.addMouseListener(this);

		secondAbility = new JLabel();
		secondAbility.setHorizontalTextPosition(JLabel.CENTER);
		secondAbility.setIcon(new ImageIcon("label3.png"));
		secondAbility.setForeground(Color.white);
		secondAbility.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 25));
		secondAbility.setBounds(1340, 340, 330, 50);
		secondAbility.addMouseListener(this);

		thirdAbility = new JLabel();
		thirdAbility.setHorizontalTextPosition(JLabel.CENTER);
		thirdAbility.setIcon(new ImageIcon("label3.png"));
		thirdAbility.setForeground(Color.white);
		thirdAbility.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 25));
		thirdAbility.setBounds(1340, 440, 330, 50);
		thirdAbility.addMouseListener(this);
		
		ability1 = new JLabel();
		ability1.setIcon(new ImageIcon("label0.png"));
		ability1.setHorizontalTextPosition(JLabel.CENTER);
		ability1.setForeground(Color.white);
		ability1.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 25));
		ability1.setBounds(1340, 240, 330, 50);
		
		ability2 = new JLabel();
		ability2.setIcon(new ImageIcon("label0.png"));
		ability2.setHorizontalTextPosition(JLabel.CENTER);
		ability2.setForeground(Color.white);
		ability2.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 25));
		ability2.setBounds(1340, 340, 330, 50);
		
		ability3 = new JLabel();
		ability3.setIcon(new ImageIcon("label0.png"));
		ability3.setHorizontalTextPosition(JLabel.CENTER);
		ability3.setForeground(Color.white);
		ability3.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 25));
		
		
		
		ability3.setBounds(1340, 440, 330, 50);

		endTurn = new JLabel();
		endTurn.setText("End Turn");
		endTurn.setHorizontalTextPosition(JLabel.CENTER);
		endTurn.setIcon(new ImageIcon("label1.png"));
		endTurn.setForeground(Color.white);
		endTurn.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 25));
		endTurn.setBounds(980, 950, 330, 50);
		endTurn.addMouseListener(this);

		currentChampion = new JLabel();
		currentChampion.setBounds(130, 340, 330, 400);
		currentChampion.setForeground(Color.white);
		currentChampion.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 25));
		currentChampion.setIcon(new ImageIcon("ability1.png"));
		

		currentChampionInfo = new JLabel();
		currentChampionInfo.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 32));
		currentChampionInfo.setBounds(135 , 385 , 350 , 350);
		currentChampionInfo.setForeground(Color.white);
		
		currentChampionName = new JLabel();
		currentChampionName.setIcon(new ImageIcon("label0.png"));
		currentChampionName.setBounds(130, 340, 330, 50);
		currentChampionName.setHorizontalTextPosition(JLabel.CENTER);
		currentChampionName.setForeground(Color.white);
		currentChampionName.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 32));
		
		abilityInfo = new JLabel();
		abilityInfo.setForeground(Color.white);
		abilityInfo.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 25));
		
		useLeader = new JLabel();
		useLeader.setText("use Leader Ability");
		useLeader.setHorizontalTextPosition(JLabel.CENTER);
		useLeader.setIcon(new ImageIcon("label1.png"));
		useLeader.setForeground(Color.white);
		useLeader.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 25));
		useLeader.setBounds(610, 950, 330, 50);
		useLeader.addMouseListener(this);
		
		
		JLabel order = new JLabel();
		order.setBounds(710 , 20 , 500 , 170);
		order.setIcon(new ImageIcon("turnOrder1.png"));
		
		turn = new JLabel(new ImageIcon("turnOrder01.png"));
		turn.setBounds(710 , 45 , 175 , 50);
		turn.addMouseListener(this);
		
		effects = new JLabel(new ImageIcon("effects.png"));
		effects.setBounds(885 , 45 , 175 , 50);
		effects.addMouseListener(this);
		
		turnOrder = new JPanel();
		turnOrder.setOpaque(false);
		turnOrder.setBounds(710 , 95 , 500 , 70);
		turnOrder.setLayout(new GridLayout(1 , 6));
		
		appliedEffects = new JPanel();
		appliedEffects.setBackground(Color.white);
		appliedEffects.setBounds(710 , 95 , 500 , 70);
		appliedEffects.setLayout(new GridLayout(1 , 6));
		appliedEffects.setVisible(false);
		appliedEffects.setOpaque(f);
		
		JLabel firstBorder = new JLabel(new ImageIcon("firstPlayer.png"));
		firstBorder.setBounds(130 ,10 , 330 , 150);
		
		JLabel firstPlayerName = new JLabel(new ImageIcon("label0.png"));
		firstPlayerName.setText(game.getFirstPlayer().getName());
		firstPlayerName.setHorizontalTextPosition(JLabel.CENTER);
		firstPlayerName.setBounds(130 ,10 , 330 , 50);
		firstPlayerName.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 25));
		firstPlayerName.setForeground(Color.white);
		
		firstPlayer = new JLabel();
		firstPlayer.setBounds(140 , 55 , 600 , 100);
		firstPlayer.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 25));
		firstPlayer.setForeground(Color.white);
		
		secondPlayer = new JLabel();
		secondPlayer.setBounds(1350 , 55 , 600 , 100);
		secondPlayer.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 25));
		secondPlayer.setForeground(Color.white);
		
		JLabel secondBorder = new JLabel(new ImageIcon("secondPlayer.png"));
		secondBorder.setBounds(1340 ,10 , 330 , 150);
		
		JLabel secondPlayerName = new JLabel(new ImageIcon("label0.png"));
		secondPlayerName.setText(game.getSecondPlayer().getName());
		secondPlayerName.setHorizontalTextPosition(JLabel.CENTER);
		secondPlayerName.setBounds(1340 ,10 , 330 , 50);
		secondPlayerName.setFont(new Font("BeaufortW01-HeavyItalic" , Font.BOLD , 25));
		secondPlayerName.setForeground(Color.white);
		
		JLabel background = new JLabel(new ImageIcon("back5.png"));
		background.setBounds(0 , 0 , 1920 , 1080);
		
		update();
		this.add(secondPlayerName);
		this.add(secondBorder);
		this.add(firstPlayerName);
		this.add(firstBorder);
		this.add(appliedEffects);
		this.add(effects);
		this.add(turn);
		this.add(turnOrder);
		this.add(order);
		this.add(currentChampionName);
		this.add(abilityInfo);
		this.add(firstPlayer);
		this.add(secondPlayer);
		
		
		this.add(useLeader);

		this.add(ability1);
		this.add(ability2);
		this.add(ability3);
		
		this.add(currentChampion);
		this.add(firstAbility);
		this.add(secondAbility);
		this.add(thirdAbility);
		this.add(endTurn);
		this.add(currentChampionInfo);
		
		this.add(grid);
		this.add(background);
		this.setLocationRelativeTo(null);

		
		this.setVisible(true);
	}
	
	public String AbilityInfo(Ability a) {
		String s = "";
		int x = 0;
		Effect e = null;
		s += "<br> Cast Area: "+ a.getCastArea() + "<br> Cast Range: " + a.getCastRange() 
				   + "<br> <br> Mana Cost: " + a.getManaCost() +"<br> Required Action Points: "
				   + a.getRequiredActionPoints() + "<br><br> Current Cooldown: " + a.getCurrentCooldown() 
				   + "<br> Base Cooldown: " + a.getBaseCooldown()+"<br>";
		if(a instanceof HealingAbility) {
			s = "<html> Healing Ability <br>" + s;
			x = ((HealingAbility) a).getHealAmount();
			s += "<br>Healing Amount: "+ x +"<html>";
		}
		else if(a instanceof DamagingAbility) {
			s = "<html> Damaging Ability<br>" + s;
			x = ((DamagingAbility) a).getDamageAmount();
			s += "<br>Damaging Amount: "+ x +"<html>";
		}
		else{
			s = "<html> CrowdControl Ability <br>" + s;
			e = ((CrowdControlAbility) a).getEffect();
			s += "<br>Effect: " +e.getName()+ "<html>";
		}
		return s;
	}
	
	public String championInfo(Champion c) {
		String s = "<html>";
		 String type = c.getClass()+"";
		 s += type.substring(18 , type.length()) + "<br><br> CurrentHp: "+c.getCurrentHP() 
		 				  + "<br> Mana: "
				 		  + c.getMana() + "<br><br> Actions: "+c.getCurrentActionPoints()
				 		  + "<br> Speed: "+c.getSpeed() + "<br><br> Attack Range: " + c.getAttackRange()
				 		  + "<br> Attack Damage: " + c.getAttackDamage() + "<html>";
		return s;
	}
	
	public ArrayList<Champion> getTurnOrder(){
		ArrayList<Champion> arr = new ArrayList<>() , tmp = new ArrayList<>();
		
		while(!game.getTurnOrder().isEmpty()) {
			Champion c = (Champion) game.getTurnOrder().remove();
			arr.add(c);
			tmp.add(c);
		}
		while(!tmp.isEmpty()) {
			game.getTurnOrder().insert(tmp.remove(0));
		}
		return arr;
	}

	public void update() {
		
		Champion c = game.getCurrentChampion();

		currentChampionName.setText(c.getName());

		ArrayList<Ability> arr = c.getAbilities();
		ArrayList<Effect> arr2 = c.getAppliedEffects();
		ArrayList<Champion> arr3 = getTurnOrder();

		appliedEffects.removeAll();
		appliedEffects.repaint();
		for(int i=0; i<6 ;i++) {
			Border border = BorderFactory.createLineBorder(new Color(0x0bc6e3));
			JLabel label = new JLabel();
			label.setBorder(border);
			if(i < arr2.size()) {
				label.setIcon(new ImageIcon(arr2.get(i).getName()+".png"));
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setToolTipText("<html>"+arr2.get(i).getName()+"<br>"+" Duration: "+arr2.get(i).getDuration());
			}
			appliedEffects.add(label);
		}
		
		turnOrder.removeAll();
		turnOrder.repaint();
		for(int i=0; i<6 ;i++) {
			Border border = BorderFactory.createLineBorder(new Color(0x0bc6e3));
			JLabel label = new JLabel();
			label.setBorder(border);
			if(i < arr3.size()) {
				label.setIcon(new ImageIcon(arr3.get(i).getName()+"3.png"));
				label.setHorizontalAlignment(JLabel.CENTER);
			}
			turnOrder.add(label);
		}
		
		a1 = arr.get(0);
		a2 = arr.get(1);
		a3 = arr.get(2);

		ability1.setText(a1.getName());
		ability2.setText(a2.getName());
		ability3.setText(a3.getName());
		
//		ability1.setToolTipText(AbilityInfo(a1));
//		ability2.setToolTipText(AbilityInfo(a2));
//		ability3.setToolTipText(AbilityInfo(a3));
		
		String s , s1;
		if(game.isFirstLeaderAbilityUsed())
			s = "Used";
		else
			s = "Not Used";
		if(game.isSecondLeaderAbilityUsed())
			s1 = "Used";
		else
			s1 = "Not Used"; 
		
		firstPlayer.setText("<html> Remaining Champions: "
							+game.getFirstPlayer().getTeam().size()+"<br><br> Leader Ability "+s);
		
		secondPlayer.setText("<html>Remaining Champions: "
				+game.getSecondPlayer().getTeam().size()+"<br><br> Leader Ability "+s1);
		
		currentChampionInfo.setText(championInfo(c));
		grid.change();
		if(game.checkGameOver() != null) {
			new Message(game.checkGameOver().getName() +" Win!!!" , this);
			//JOptionPane.showMessageDialog(null, game.checkGameOver().getName() +" Win!!!");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
		this.revalidate();
	}

	public void castAbility(Direction d, int x, int y) {
		Champion c = game.getCurrentChampion();
		try {
			if(z == 1) {
				switch (a1.getCastArea()) {
				case DIRECTIONAL : game.castAbility(a1, d);break;
				case SINGLETARGET : game.castAbility(a1, x, y);
				}
			}
			else if(z == 2) {
				switch (a2.getCastArea()) {
				case DIRECTIONAL : game.castAbility(a2, d);break;
				case SINGLETARGET : game.castAbility(a2, x, y);
				}
			}
			else {
				switch (a3.getCastArea()) {
				case DIRECTIONAL : game.castAbility(a3, d);break;
				case SINGLETARGET : game.castAbility(a3, x, y);
				}
			}
		}
		catch (NotEnoughResourcesException e) {
			new Message(e.getMessage() , this);
			//JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (AbilityUseException e) {
			new Message(e.getMessage() , this);
			//JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (InvalidTargetException e) {
			new Message(e.getMessage() , this);
		//	JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (CloneNotSupportedException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		update();
		this.revalidate();
		this.repaint();
	}

	public void useAbility() {
		try {
			if(z == 1) {
				switch (a1.getCastArea()) {
				case DIRECTIONAL : f2 = true ;
				new Message("Chose your dirction" ,this);
				//JOptionPane.showMessageDialog(null, "Chose your dirction");
				break;
				case SINGLETARGET : f3 = true;
				new Message("Click on the target" ,this );
				//JOptionPane.showMessageDialog(null, "Click on the target");
				break;
				default : game.castAbility(a1); break;
				}
			}
			if(z == 2) {
				switch (a2.getCastArea()) {
				case DIRECTIONAL : f2 = true ;
				new Message("Chose your dirction" , this);
				//JOptionPane.showMessageDialog(null, "Chose your dirction");
				break;
				case SINGLETARGET : f3 = true;
				new Message("Click on the target" , this);
				//JOptionPane.showMessageDialog(null, "Click on the target");
				break;
				default : game.castAbility(a2); break;
				}
			}
			if(z == 3) {
				switch (a3.getCastArea()) {
				case DIRECTIONAL : f2 = true ;
				new Message("Chose your dirction" ,this);
				//JOptionPane.showMessageDialog(null, "Chose your dirction");
				break;
				case SINGLETARGET : f3 = true;
				new Message("Click on the target" , this);
				//JOptionPane.showMessageDialog(null, "Click on the target");
				break;
				default : game.castAbility(a3); break;
				}
			}
		}
		catch (NotEnoughResourcesException e) {
			new Message(e.getMessage() , this);
			//JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (AbilityUseException e) {
			new Message(e.getMessage() , this);
			//JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (CloneNotSupportedException e) {
		}
		update();
		this.revalidate();
		this.repaint();
	}

	public void move(String direction) {
		try {
			switch (direction) {
			case "up":
				game.move(Direction.UP);
				break;
			case "down":
				game.move(Direction.DOWN);
				break;
			case "left":
				game.move(Direction.LEFT);
				break;
			case "right":
				game.move(Direction.RIGHT);
				break;
			}
			update();
		} catch (NotEnoughResourcesException e) {
			new Message(e.getMessage() , this);
			//JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (UnallowedMovementException e) {
			new Message(e.getMessage() , this);
			//JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public void attack(String direction) {
		try {
			switch (direction) {
			case "up": 
				game.attack(Direction.UP);break;
			case "down": 
				game.attack(Direction.DOWN);break;
			case "left": 
				game.attack(Direction.LEFT);break;
			case "right":
				game.attack(Direction.RIGHT);break;
			}
			update();
			grid.change();	
		} catch (NotEnoughResourcesException e) {
			new Message(e.getMessage() ,this);
			//JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (ChampionDisarmedException e) {
			new Message(e.getMessage() ,this);
			//JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
	}
	public void actionPerformed(ActionEvent e) {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == turn) {
			turnOrder.setVisible(true);
			effects.setIcon(new ImageIcon("effects.png"));
			turn.setIcon(new ImageIcon("turnOrder01.png"));
			appliedEffects.setVisible(false);
			
		}
		else if(e.getSource() == effects) {
			effects.setIcon(new ImageIcon("effects1.png"));
			turn.setIcon(new ImageIcon("turnOrder0.png"));
			turnOrder.setVisible(false);
			appliedEffects.setVisible(true);
		}
		else if (f3) {
			here: for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (e.getComponent() == cells[i][j]) {
						castAbility(null, i, j);
						break here;
					}
				}
			}
			f3 = false;
		}
		else if (e.getSource() == useLeader) {
			try {
				game.useLeaderAbility();
				update();
			} catch (LeaderNotCurrentException e1) {
				new Message(e1.getMessage() , this);
				//JOptionPane.showMessageDialog(null, e1.getMessage());
			} catch (LeaderAbilityAlreadyUsedException e1) {
				new Message(e1.getMessage() , this);
				//JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		} else if (e.getSource() == endTurn) {
			game.endTurn();
			update();
		} else if (e.getSource() == firstAbility) {
			z = 1;
			useAbility();
		} else if (e.getSource() == secondAbility) {
			z = 2;
			useAbility();
		} else if (e.getSource() == thirdAbility) {
			z = 3;
			useAbility();
		} else if (e.getSource() == attack) {
			new Message("Chose your direction" , this);
			//JOptionPane.showMessageDialog(null, "Chose your direction");
			f = true;
		} else if (f) {
			attack(((JLabel) (e.getSource())).getText());
			f = false;
			f2 = false;
			f3 = false;
		} else if (f2) {
			switch (((JLabel) e.getSource()).getText()) {
			case "up" :castAbility(Direction.UP, 0, 0);break;
			case "down" :castAbility(Direction.DOWN, 0, 0);break;
			case "left" :castAbility(Direction.LEFT, 0, 0);break;
			case "right" :castAbility(Direction.RIGHT, 0, 0);break;
			}
			f = false;
			f2 = false;
			f3 = false;
		} else {
			move(((JLabel) e.getSource()).getText());
		}
		this.revalidate();
		this.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(f3) {
			for(int i=0; i<5; i++) {
				for(int j=0; j<5 ;j++) {
					if(e.getComponent() == cells[i][j]) {
//						Border border = BorderFactory.createLineBorder(new Color(0xc8a96c));
						Border border = BorderFactory.createLineBorder(Color.green);
						cells[i][j].setBorder(border);
					}
				}
			}
		}

		if(e.getComponent() == attack) {
			attack.setIcon(new ImageIcon("attack.png"));
		}
		if(e.getComponent() == up) {
			up.setIcon(new ImageIcon("up1.png"));
		}
		if(e.getComponent() == right) {
			right.setIcon(new ImageIcon("right1.png"));
		}
		if(e.getComponent() == left) {
			left.setIcon(new ImageIcon("left1.png"));
		}
		if(e.getComponent() == down) {
			down.setIcon(new ImageIcon("down1.png"));
		}
		if(e.getSource() == useLeader) {
			useLeader.setIcon(new ImageIcon("label.png"));
		}
		if(e.getSource() == endTurn) {
			endTurn.setIcon(new ImageIcon("label.png"));
		}
		if(e.getSource() == firstAbility) {
			abilityInfo.setVisible(true);
			abilityInfo.setText(AbilityInfo(a1));
			abilityInfo.setBounds(1340 , 280 , 500 , 350);
			secondAbility.setBounds(1340, 650, 330, 50);
			ability2.setBounds(1340, 650, 330, 50);
			thirdAbility.setBounds(1340, 750, 330, 50);
			ability3.setBounds(1340, 750, 330, 50);
			firstAbility.setBounds(1340, 240, 330, 400);
			firstAbility.setIcon(new ImageIcon("Ability.png"));
		}
		if(e.getSource() == secondAbility) {
			abilityInfo.setVisible(true);
			abilityInfo.setText(AbilityInfo(a2));
			abilityInfo.setBounds(1340 , 380 , 500 , 350);
			thirdAbility.setBounds(1340, 750, 330, 50);
			ability3.setBounds(1340, 750, 330, 50);
			secondAbility.setBounds(1340, 340, 330, 400);
			secondAbility.setIcon(new ImageIcon("Ability.png"));
		}
		if(e.getSource() == thirdAbility) {
			abilityInfo.setVisible(true);
			abilityInfo.setText(AbilityInfo(a3));
			abilityInfo.setBounds(1340 , 480 , 500 , 350);
			thirdAbility.setBounds(1340, 440, 330, 400);
			thirdAbility.setIcon(new ImageIcon("Ability.png"));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		for(int i=0; i<5; i++) {
			for(int j=0; j<5 ;j++) {
				if(e.getComponent() == cells[i][j]) {
					Border border = BorderFactory.createLineBorder(new Color(0xc8a96c));
					cells[i][j].setBorder(null);
				}
			}
		}
		if(e.getComponent() == attack) {
			attack.setIcon(new ImageIcon("attack1.png"));
		}
		if(e.getComponent() == up) {
			up.setIcon(new ImageIcon("up.png"));
		}
		if(e.getComponent() == right) {
			right.setIcon(new ImageIcon("right.png"));
		}
		if(e.getComponent() == left) {
			left.setIcon(new ImageIcon("left.png"));
		}
		if(e.getComponent() == down) {
			down.setIcon(new ImageIcon("down.png"));
		}
		if(e.getSource() == useLeader) {
			useLeader.setIcon(new ImageIcon("label1.png"));
		}
		if(e.getSource() == endTurn) {
			endTurn.setIcon(new ImageIcon("label1.png"));
		}
		if(e.getSource() == firstAbility) {
			abilityInfo.setVisible(false);
			firstAbility.setBounds(1340, 240, 330, 50);
			secondAbility.setBounds(1340, 340, 330, 50);
			ability2.setBounds(1340, 340, 330, 50);
			thirdAbility.setBounds(1340, 440, 330, 50);
			ability3.setBounds(1340, 440, 330, 50);
			firstAbility.setIcon(new ImageIcon("label3.png"));
		}
		if(e.getSource() == secondAbility) {
			abilityInfo.setVisible(false);
			thirdAbility.setBounds(1340, 440, 330, 50);
			ability3.setBounds(1340, 440, 330, 50);
			secondAbility.setBounds(1340, 340, 330, 50);
			secondAbility.setIcon(new ImageIcon("label3.png"));
		}
		if(e.getSource() == thirdAbility) {
			abilityInfo.setVisible(false);
			thirdAbility.setBounds(1340, 440, 330, 50);
			thirdAbility.setIcon(new ImageIcon("label3.png"));
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
