/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 */
public class Sneak implements Command {

	/**
	 * Move without alerting others to your presence as you enter in the same
	 * room as them
	 */
	@Override
	public void execute(Mob mob, String input) {

		Ability sneak = mob.getLearned().getAbility("sneak");

		if (sneak == null) {
			mob.out("You have no knowledge of sneak");
			return;
		}

		if (mob.isSneaking()) {
			mob.out("You are already sneaking around");
		}

		if (sneak.isSuccessful()) {
			mob.out("<S-You/NAME> successfully start to sneak");

			mob.getMobStatus().setSneaking(60);// 1 minute of sneaking around.

			if (sneak.isImproved()) {
				mob.out("[[[[ Your ability to " + sneak.getId()
						+ " has improved ]]]]");
				sneak.improve();
			}
		}
	}

}
