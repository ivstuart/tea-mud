/*
 * Created on 12-Nov-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.state;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.common.MobState.*;

public class Fly implements Command {

	@Override
	public void execute(Mob mob_, String input_) {

		// Check current state
		if (mob_.isFlying()) {
			mob_.out("You are already flying!");
			return;
		}

		// Check allowed to change state
		if(!mob_.getMobAffects().hasAffect("levitate")) {
			mob_.out("You do not have that spell active");
			return;
		}
		// Change state and notify mob and room
		mob_.getRoom().out("You start flying"); // TODO Msg text
		mob_.out("You fly");

		mob_.setState(FLYING);

	}

}
