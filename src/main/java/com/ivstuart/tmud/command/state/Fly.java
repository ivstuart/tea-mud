/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 12-Nov-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.state;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.common.MobState.FLYING;

public class Fly extends BaseCommand {

	@Override
	public void execute(Mob mob_, String input_) {

		// Check current state
		if (mob_.isFlying()) {
			mob_.out("You are already flying!");
			return;
		}

		if (mob_.getRoom().isTunnel()) {
			mob_.out("You can not fly here inside a tunnel");
			return;
		}

		if (!mob_.getRace().isFly()) {
			// Check allowed to change state
			if (!mob_.getMobAffects().hasAffect("levitate")) {
				mob_.out("You do not have that spell active");
				return;
			}
		}

		// Change state and notify mob and room
		mob_.getRoom().out(new Msg(mob_, "<S-NAME> start flying"));
		mob_.setState(FLYING);

	}

}
