/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Hide extends BaseCommand {

	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * @param mob
	 * @param input calling code ensures that null is never passed in
	 */
	@Override
	public void execute(Mob mob, String input) {


		if (mob.getFight().isEngaged() || mob.getFight().isFighting()) {
			mob.out("You can not hide while engaged in combat");
			return;
		}

		// hide objects
		// hide exits or props only if they where hidden in the first place and
		// made visible.

		// Success or fail
		Ability ability = mob.getLearned().getAbility("hide");

		if (ability == null) {
			mob.out("You have no knowledge of how to hide!");
			return;
		}

		if (!mob.getMv().deduct(10)) {
			mob.out("You do not have enough movement to hide.");
			return;
		}

		if (input.length() > 0) {
			executeHideObject(mob, input);
			return;
		}

		// TODO hide a mob

		if (mob.getMobStatus().isHidden()) {
			mob.out("You are already hidden.");
			return;
		}

		if (ability.isSuccessful()) {
			mob.out(new Msg(mob, "<S-You/NAME> successfully hide."));
			mob.getMobStatus().setHidden(30);
			mob.setHidden(true);

			if (ability.isImproved()) {
				mob.out("[[[[ Your ability to " + ability.getId()
						+ " has improved ]]]]");
				ability.improve();
			}
		} else {
			mob.out(new Msg(mob, "<S-You/NAME> failed to hide."));
		}

		// Do "search" in collaboration with this command.

	}

	private void executeHideObject(Mob mob, String input) {
		// Try object on ground first
		// then exit

		Item item = mob.getRoom().getInventory().get(input);

		if (item != null) {
			item.setHidden(true);
			mob.getRoom().out(
					"<S-You/NAME> hide " + item.getName() + " in this room");
			return;
		}

		LOGGER.debug("Item was null hence trying to hide a special exit instead");

		Exit exit = mob.getRoom().getExit(input);

		if (exit != null) {
			// Can only hide none N,S,E,W,UP,DOWN exits
			if (!exit.isHidden() && exit.isSpecial()) {
				exit.setHidden(true);
				mob.getRoom().out(
						"<S-You/NAME> hide " + exit.getName()
								+ " to throw off pursuit!");
				return;
			}
		}

		mob.out("You failing to find any " + input + " to hide");
	}

}
