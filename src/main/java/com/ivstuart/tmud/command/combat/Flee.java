package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;

public class Flee extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		if (mob.getFight().isEngaged() == false) {
			mob.out("Someone much be attacking you for you to flee!");
			return;
		}

		// If you where attacking yourself then fleeing will success

		// If more than one person is attacking you would it be harder to flee?

		// Should skills or dex affect a players ability to flee?

		// You will stop attacking anyone
		// mob.getFight().stopFighting();

		mob.getFight().add(new com.ivstuart.tmud.fighting.action.Flee(mob));

	}

}