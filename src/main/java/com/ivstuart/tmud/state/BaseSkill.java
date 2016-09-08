/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

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
	protected int level = 1;
	private DiceRoll damage;
	private int difficulty = 1;
	private String prereq;

	private String prof;

	private int speed = 10;

	private DiceRoll duration;

	public BaseSkill() {
	}

	public BaseSkill(String name) {
		setId(name);
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost_) {
		this.cost = cost_;
	}

	public DiceRoll getDamage() {
		if (damage == null) {
			return DiceRoll.ONE_D_SIX;
		}
		return damage;
	};

	public void setDamage(String damage) {
		this.damage = new DiceRoll(damage);
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int diff_) {
		this.difficulty = diff_;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level_) {
		this.level = level_;
	}

	public String getPrereq() {
		return prereq;
	}

	public void setPrereq(String prereq_) {
		this.prereq = prereq_;
	}

	public String getProf() {
		return prof;
	}

	public void setProf(String prof_) {
		this.prof = prof_;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isSkill() {
		return true;
	}

	@Override
	public String toString() {
		return "BaseSkill{" +
				"cost=" + cost +
				", damage=" + damage +
				", difficulty=" + difficulty +
				", level=" + level +
				", prereq='" + prereq + '\'' +
				", prof='" + prof + '\'' +
				", speed=" + speed +
				", duration=" + duration +
				'}';
	}

    public DiceRoll getDuration() {
    	return duration;
    }

	public void setDuration(String duration) {
		this.duration = new DiceRoll(duration);
	}
}
