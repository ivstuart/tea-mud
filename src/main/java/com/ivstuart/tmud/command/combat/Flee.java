/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;

public class Flee extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		if (mob.getFight().isEngaged() == false) {
			mob.out("Someone much be attacking you for you to flee!");
			return;
		}

		if (mob.getMobStatus().isOffBalance()) {
			mob.out("You are too off balance to be able to flee right now!");
			return;
		}

		if (mob.getMobStatus().isBashed()) {
			mob.out("You have been bash you need to get up in order to flee!");
			return;
		}

		if (mob.getMobStatus().isImmobile()) {
			mob.out("You are immobile at the moment so you can not flee");
			return;
		}

		// If you where attacking yourself then fleeing will success

		// If more than one person is attacking you would it be harder to flee?

		// Should skills or dex affect a players ability to flee?

		mob.getFight().add(new com.ivstuart.tmud.fighting.action.Flee(mob));

	}

}