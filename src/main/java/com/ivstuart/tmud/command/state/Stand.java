/*
 * Created on 12-Nov-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.state;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.common.MobState.STAND;

public class Stand extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		if (mob.getMobStatus().isGroundFighting()) {
			mob.out("You are locked into ground fighting for the moment");
		} else if (mob.getFight().isGroundFighting()) {
			// chance to scramble up ?
			// effect if winning or losing gf if fails ?
			// use movement points
			if (!mob.getMv().deduct(5)) {
				mob.out("You dont have enough movement available to stand");
				return;
			}

			if (DiceRoll.ONE_D100.rollLessThan(50)) {
				mob.out("You try to scramble up to avoid fighting on the ground, but fail to get up");
				return;
			}

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
