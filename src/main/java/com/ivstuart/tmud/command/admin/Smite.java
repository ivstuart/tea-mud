/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Smite implements Command {

	/**
	 * Instantely kill any mob
	 */
	@Override
	public void execute(Mob mob, String input) {

		if (!mob.isAdmin()) {
			mob.out("Admin only");
			// return;
		}

		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to smite!");
			return;
		}

		// Set mob hp to 0 and deal damage to them
		target.getHp().setValue(-1);
		DamageManager.checkForDefenderDeath(mob, target);

		// TODO use Msg and room output
		mob.out("You smite " + target.getName() + " to a pile of ash!");
		target.out("You are hit with a bolt of pure light lightning and crumble to ash!");
	}

}
