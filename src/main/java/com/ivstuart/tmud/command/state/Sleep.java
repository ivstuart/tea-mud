/*
 * Created on 12-Nov-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.state;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.common.MobState.SLEEP;

public class Sleep extends BaseCommand {

	@Override
	public void execute(Mob mob_, String input_) {

		if (mob_.getState() == SLEEP) {
			mob_.out("You are already sleeping!");
			return;
		}

		if (mob_.getFight().isEngaged()) {
			mob_.out("You can not sleep you are fighting");
			return;
		}

		// Check allowed to change state
		if (mob_.getRoom().isFlying()) {
			mob_.out("You can not sleep here you must continue to fly");
			return;
		}

		if (mob_.getRoom().isWater()) {
			mob_.out("You can not sleep here you must continue to swim");
			return;
		}
		// Change state and notify mob and room

		// mob_.out("You stop "+mob_.getState().getDesc()+" and stand.");

		mob_.setState(SLEEP);

		mob_.out("You sleep");
	}

}
