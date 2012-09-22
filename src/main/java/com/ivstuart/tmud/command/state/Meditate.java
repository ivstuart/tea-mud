/*
 * Created on 12-Nov-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.state;

import static com.ivstuart.tmud.common.MobState.MEDITATE;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

public class Meditate implements Command {

	@Override
	public void execute(Mob mob_, String input_) {

		if (mob_.getState() == MEDITATE) {
			mob_.out("You are already meditating!");
			return;
		}

		if (mob_.getFight().isEngaged()) {
			mob_.out("You can not meditate you are fighting");
			return;
		}

		// Check allowed to change state

		// Change state and notify mob and room

		mob_.out("You stop " + mob_.getState().getDesc() + " and meditate.");

		mob_.setState(MEDITATE);
	}

}
