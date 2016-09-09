/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.constants.ManaType;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

/**
 * A skill or spell can extend from this.
 * 
 * @author Ivan Stuart
 * 
 */
public class Ability implements Serializable {

	public static final int MAX_PRACTICE = 50;
	public static final Ability NULL_ABILITY = new Ability("NULL");
	private static final long serialVersionUID = -5422068126244012518L;
	private static final Logger LOGGER = LogManager.getLogger();
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
			LOGGER.error("Problem getting ability " + id, e);
		}

		return roll < difficulty;
	}

	public boolean isSkill() {
		return World.getSkills().containsKey(id);
	}

	public void setSkill(int skill_) {
		skill = skill_;
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

		LOGGER.debug("Rolling for success for " + this.getId() + " with skill = " + skill);

		return roll < skill;
	}

	public boolean practice(Player player_) {
		if (skill >= MAX_PRACTICE) {
			return false;
		}
		skill += player_.getAttributes().getINT().getValue();
		return true;
	}

	public boolean isNull() {
		return this == NULL_ABILITY;
	}

	public boolean isSuccessful(Mob mob) {
		if (this.isImproved()) {
			mob.out("[[[[ Your ability to " + this.getId() + " has improved ]]]]");
			this.improve();
		}
		return this.isSuccessful();

	}
}
