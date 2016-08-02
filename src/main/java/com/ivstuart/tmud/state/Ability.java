package com.ivstuart.tmud.state;

import java.io.Serializable;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.constants.ManaType;
import com.ivstuart.tmud.person.Player;

/**
 * A skill or spell can extend from this.
 * 
 * @author Ivan Stuart
 * 
 */
public class Ability implements Serializable {

	private static final long serialVersionUID = -5422068126244012518L;

	public static final int MAX_PRACTICE = 50;

	public static final Ability NULL_ABILITY = new Ability("NULL");

	private String id;

	private int skill = 0;

	public Ability() {
	}

	public Ability(String name_) {
		id = name_;
	}
	
	public Ability(String name,int startSkillLevel) {
		id = name;
		skill = startSkillLevel;
	}

	public String getId() {
		return id;
	}

	public ManaType getManaType() {
		return World.getSpell(id).getManaType();
	}

	public int getSkill() {
		return skill;
	}

	public void improve() {
		skill++;
	}

	public boolean isImproved() {
		int roll = DiceRoll.ONE_D100.roll();
		int difficulty = 5;

		try {
			difficulty = World.getAbility(id).getDifficulty();
		} catch (NullPointerException e) {
			// TODO log warning message diff
		}

		return roll < difficulty;
	}

	public boolean isSkill() {
		return World.getSkills().containsKey(id);
	}

	public boolean isSpell() {
		return World.getSpells().containsKey(id);
	}

	public boolean isSuccessful() {
		int roll = DiceRoll.ONE_D100.roll();

		if (roll < 5) {
			return true;
		} else if (roll > 95) {
			return false;
		}
		return roll < skill;
	}

	public boolean practice(Player player_) {
		if (skill >= MAX_PRACTICE) {
			return false;
		}
		skill += player_.getAttributes().getINT().getValue();
		return true;
	}

	public void setSkill(int skill_) {
		skill = skill_;
	}

	public boolean isNull() {
		return this == NULL_ABILITY;
	}
}
