package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.DiceRoll;

public class BaseSkill extends BasicThing {

	public static final int MAX_PRACTICE = 50;

	public static final BaseSkill NULL = new BaseSkill();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int cost = 10; // Could be mana or moves or something else.

	private DiceRoll damage;

	private int difficulty = 1;

	protected int level = 1;

	private String prereq;

	private String prof;

	private int speed = 10;

	public BaseSkill() {
	}

	public BaseSkill(String name) {
		setId(name);
	}

	public int getCost() {
		return cost;
	}

	public DiceRoll getDamage() {
		if (damage == null) {
			return DiceRoll.ONE_D_SIX;
		}
		return damage;
	}

	public int getDifficulty() {
		return difficulty;
	};

	public int getLevel() {
		return level;
	}

	public String getPrereq() {
		return prereq;
	}

	public String getProf() {
		return prof;
	}

	public int getSpeed() {
		return speed;
	}

	public boolean isSkill() {
		return true;
	}

	public void setCost(int cost_) {
		this.cost = cost_;
	}

	public void setDamage(String damage) {
		this.damage = new DiceRoll(damage);
	}

	public void setDifficulty(int diff_) {
		this.difficulty = diff_;
	}

	public void setLevel(int level_) {
		this.level = level_;
	}

	public void setPrereq(String prereq_) {
		this.prereq = prereq_;
	}

	public void setProf(String prof_) {
		this.prof = prof_;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
