package model.world;

import java.awt.Point;

public class Cover implements Damageable {
	private int currentHP , MaxHp;

	private Point location;

	public Cover(int x, int y) {
		this.currentHP = (int)(( Math.random() * 900) + 100);
		this.MaxHp = currentHP;
		location = new Point(x, y);
	}

	public int getMaxHP(){
		return MaxHp;
	}

	public int getCurrentHP() {
		return this.currentHP;
	}

	public void setCurrentHP(int newHp) {
		if (newHp < 0) {
			currentHP = 0;
		
		} else
			currentHP = newHp;
	}

	public Point getLocation() {
		return location;
	}

	public String toString() {
		return "Cover ";
	}
	

}
