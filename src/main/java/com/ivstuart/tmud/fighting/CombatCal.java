/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 04-Oct-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.fighting;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Weapon;

import static com.ivstuart.tmud.constants.SkillNames.UNARMED_COMBAT;
import static com.ivstuart.tmud.constants.SpellNames.BLINDNESS;
import static com.ivstuart.tmud.constants.SpellNames.COMBAT_SENSE;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CombatCal {

	public static int getAttack(Mob mob) {
		int attack = getBaseAttack(mob);

		// Get skill level with currently wielded weapon

		Weapon weapon = mob.getEquipment().getWeapon();

		if (weapon != null) {

			int skill = mob.getLearned().getAbility(weapon.getSkill())
					.getSkill();

			attack += skill;
		} else {
			int skill = mob.getLearned().getAbility(UNARMED_COMBAT)
					.getSkill();

			attack += skill;
		}

		attack += mob.getEquipment().getHitRollBonus();


		return attack;
	}

	private static int getBaseAttack(Mob mob_) {
		int total = mob_.getOffensive();
		if (mob_.isPlayer()) {
			total += mob_.getPlayer().getAttributes().getSTR().getValue() * 2;
			total += mob_.getPlayer().getAttributes().getDEX().getValue();
			total += mob_.getPlayer().getAttributes().getINT().getValue();
		}

		if (mob_.getMobAffects().hasAffect(BLINDNESS)) {
			total /= 2;
		}

		if (mob_.getMobAffects().hasAffect(COMBAT_SENSE)) {
			total += 20;
		}


		return total;
	}

	private static int getBaseDefence(Mob mob_) {
		if (mob_ == null) {
			return 0;
		}
		int total = mob_.getDefence();
		if (mob_.isPlayer()) {
			total += mob_.getPlayer().getAttributes().getSTR().getValue();
			total += mob_.getPlayer().getAttributes().getDEX().getValue() * 2;
			total += mob_.getPlayer().getAttributes().getINT().getValue();
		}

		if (mob_.getMobAffects().hasAffect(BLINDNESS)) {
			total /= 2;
		}

		if (mob_.getMobAffects().hasAffect(COMBAT_SENSE)) {
			total += 20;
		}

		return total;
	}

	public static int getDefence(Mob mob) {
		int defence = getBaseDefence(mob);

		// Get skill level with currently wielded weapon
		Weapon weapon = mob.getEquipment().getWeapon();

		if (weapon != null) {

			int skill = mob.getLearned().getAbility(weapon.getSkill())
					.getSkill();

			defence += skill;
		} else {
			int skill = mob.getLearned().getAbility(UNARMED_COMBAT)
					.getSkill();

			defence += skill;
		}

		return defence;
	}

}
