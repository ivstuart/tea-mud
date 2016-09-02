/*
 * Created on 12-Nov-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.state;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Prop;
import com.ivstuart.tmud.utils.StringUtil;

import static com.ivstuart.tmud.common.MobState.SLEEP;
import static com.ivstuart.tmud.common.MobState.SLEEP_ON;

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

		if (checkSleepOn(mob_, input_)) return;

		mob_.setState(SLEEP);

		mob_.out("You sleep");
	}

	private boolean checkSleepOn(Mob mob_, String input_) {
		String target = StringUtil.getLastWord(input_);

		if (target != null && target.length() > 0) {
			Prop prop = mob_.getRoom().getProps().get(target);

			if (prop == null) {
				mob_.out("There is no "+target+" to sleep on here.");
				return true;
			}

			if (!prop.isSleepable()) {
				mob_.out("You can not sleep on a "+target);
				return true;
			}

			mob_.out("You sleep on a "+prop.getBrief());

			mob_.setState(SLEEP_ON);
			return true;
		}
		return false;
	}
}
