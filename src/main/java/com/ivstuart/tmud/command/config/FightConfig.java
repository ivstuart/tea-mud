/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.config;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;


public class FightConfig extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {
		execute(mob.getPlayer(), input);
	}

	private void execute(Player mob, String input) {

		if (input.length() == 0) {
			mob.out(mob.getConfig().getFightData().look());
			return;
		}
		mob.out(mob.getConfig().getFightData().toggle(input));
	}

}