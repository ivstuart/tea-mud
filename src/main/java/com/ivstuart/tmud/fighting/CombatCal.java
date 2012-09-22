/*
 * Created on 04-Oct-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.fighting;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Weapon;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CombatCal {

	public static int getAttack(Mob ent) {
		int attack = getBaseAttack(ent);

		// Get skill level with currently wielded weapon

		Weapon weapon = ent.getEquipment().getWeapon();

		if (weapon != null) {

			int skill = ent.getLearned().getAbility(weapon.getSkill())
					.getSkill();

			attack += skill;
		}
		return attack;
	}

	private static int getBaseAttack(Mob mob_) {
		int total = mob_.getOffensive();
		if (mob_.isPlayer()) {
			total += mob_.getPlayer().getAttributes().getSTR().getValue() * 2;
			total += mob_.getPlayer().getAttributes().getDEX().getValue();
			total += mob_.getPlayer().getAttributes().getINT().getValue();
		}
		return total;
	}

	private static int getBaseDefence(Mob mob_) {
		int total = mob_.getDefence();
		if (mob_.isPlayer()) {
			total += mob_.getPlayer().getAttributes().getSTR().getValue();
			total += mob_.getPlayer().getAttributes().getDEX().getValue() * 2;
			total += mob_.getPlayer().getAttributes().getINT().getValue();
		}
		return total;
	}

	public static int getDefence(Mob ent) {
		int defence = getBaseDefence(ent);

		// Get skill level with currently wielded weapon
		Weapon weapon = ent.getEquipment().getWeapon();

		if (weapon != null) {

			int skill = ent.getLearned().getAbility(weapon.getSkill())
					.getSkill();

			defence += skill;
		}

		return defence;
	}

}
