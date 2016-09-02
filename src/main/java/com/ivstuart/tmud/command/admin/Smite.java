/*
 * Created on 28-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Smite extends AdminCommand {

	/**
	 * Instantely kill any mob
	 */
	@Override
	public void execute(Mob mob, String input) {

		super.execute(mob,input);

		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to smite!");
			return;
		}

		// Set mob hp to 0 and deal damage to them
		target.getHp().setValue(-1);
		DamageManager.checkForDefenderDeath(mob, target);

        mob.getRoom().out(new Msg(mob, target, "<S-You/NAME> smites <T-you/NAME> to a pile of ash, with a pure bolt of lightning!"));

	}

}
