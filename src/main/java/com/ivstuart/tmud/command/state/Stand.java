/*
 * Created on 12-Nov-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.state;

import static com.ivstuart.tmud.common.MobState.STAND;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

public class Stand implements Command {

	@Override
	public void execute(Mob mob, String input) {

		if (mob.getFight().isGroundFighting()) {
			// chance to scramble up ?
			// effect if winning or losing gf if fails ?
			// use movement points

			mob.out("You try to scramble up to avoid fighting on the ground");

			mob.getFight().setMeleeToBasicAttack();
			mob.getTargetFight().setMeleeToBasicAttack();
		}

		if (mob.getState() == STAND) {
			mob.out("You are already standing!");
			return;
		}

		// Check allowed to change state

		// Change state and notify mob and room

		mob.out("You stop " + mob.getState().getDesc() + " and stand.");

		mob.setState(STAND);

	}

}
