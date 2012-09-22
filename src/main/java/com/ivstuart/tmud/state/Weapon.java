package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.DiceRoll;

/**
 * @author Ivan Stuart
 * 
 */
public class Weapon extends Item {

	private static final long serialVersionUID = -7988147515765705604L;

	private String skill;
	private DiceRoll damage;

	public int damageRoll() {
		return damage.roll();
	}

	public String getSkill() {
		return skill;
	}

	public void setDamage(String damage_) {
		this.damage = new DiceRoll(damage_);
	}

	public void setSkill(String skill_) {
		skill = skill_.trim();
	}
}
