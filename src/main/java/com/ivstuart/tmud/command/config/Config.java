package com.ivstuart.tmud.command.config;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;

public class Config extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

        execute(mob.getPlayer(), input);

	}

	public void execute(Player mob, String input) {

		if (input.length() == 0) {
			mob.out(mob.getConfig().getConfigData().toString());
			return;
		}
		mob.out(mob.getConfig().getConfigData().toggle(input));
	}
}