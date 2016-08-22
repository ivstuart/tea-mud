/*
 * Created on 22-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Exit;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Hide extends BaseCommand {

	/*
	 * 2ndary action?
	 */
	@Override
	public void execute(Mob mob, String input) {

		// TODO can hide self
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

		if (mob.getMobStatus().isHidden()) {
			mob.out("You are already hidden.");
			return;
		}

		if (ability.isSuccessful()) {
			mob.out("<S-You/NAME> successfully hide.");
			mob.getMobStatus().setHidden(30);

			if (ability.isImproved()) {
				mob.out("[[[[ Your ability to " + ability.getId()
						+ " has improved ]]]]");
				ability.improve();
			}
		} else {
			mob.out("<S-You/NAME> failed to hide");
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

		Exit exit = mob.getRoom().getExit(input);

		if (exit != null) {
			// Can only hide none N,S,E,W,UP,DOWN exits
			if (!exit.isHidden() && exit.isSpecial()) {
				exit.setHidden(true);
				mob.getRoom().out(
						"<S-You/NAME> hide " + exit.getName()
								+ " to throw off pursuit!");
			}
		}

	}

}
