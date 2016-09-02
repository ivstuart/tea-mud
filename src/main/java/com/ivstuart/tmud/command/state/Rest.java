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

import static com.ivstuart.tmud.common.MobState.REST;
import static com.ivstuart.tmud.common.MobState.REST_ON;

public class Rest extends BaseCommand {

	@Override
	public void execute(Mob mob_, String input_) {

		if (mob_.getState() == REST) {
			mob_.out("You are already resting!");
			return;
		}

		if (mob_.getFight().isEngaged()) {
			mob_.out("You can not rest you are fighting");
			return;
		}

		// Check allowed to change state
		if (mob_.getRoom().isFlying()) {
			mob_.out("You can not rest here you must continue to fly");
			return;
		}

		if (mob_.getRoom().isWater()) {
			mob_.out("You can not rest here you must continue to swim");
			return;
		}
		// Change state and notify mob and room

		if (checkRestOn(mob_, input_)) return;

		mob_.out("You stop " + mob_.getState().getDesc() + " and rest.");

		mob_.setState(REST);

	}

	private boolean checkRestOn(Mob mob_, String input_) {
		String target = StringUtil.getLastWord(input_);

		if (target != null && target.length() > 0) {
			Prop prop = mob_.getRoom().getProps().get(target);

			if (prop == null) {
				mob_.out("There is no "+target+" to rest on here.");
				return true;
			}

			if (!prop.isSittable()) {
				mob_.out("You can not rest on a "+target);
				return true;
			}

			mob_.out("You rest on a "+prop.getBrief());

			mob_.setState(REST_ON);
			return true;
		}
		return false;
	}
}
