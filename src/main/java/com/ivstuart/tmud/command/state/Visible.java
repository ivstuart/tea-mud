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

public class Visible implements Command {

	@Override
	public void execute(Mob mob_, String input_) {

		if (input_.length() > 0) {
			Mob mobToWake = mob_.getRoom().getMob(input_);

			if (mobToWake == null) {
				mob_.out("Can not see " + input_ + " to wake");
				return;
			}

			if (mobToWake.getState() != SLEEP) {
				mob_.out(mob_.getName() + " is already awake!");
				return;
			}

			mobToWake.setState(STAND);
			mobToWake.out("You are woken by " + mob_.getName());
			mob_.out("You wake " + mobToWake.getName());
			return;

		}

		// Check current state
		if (mob_.getState() != SLEEP) {
			mob_.out("You are already awake!");
			return;
		}

		// Check allowed to change state

		// Change state and notify mob and room

		mob_.out("You wake");

		mob_.setState(STAND);

		// Note sleep to wake you will also stand
	}

}
