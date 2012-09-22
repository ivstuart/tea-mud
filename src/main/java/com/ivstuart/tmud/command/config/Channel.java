package com.ivstuart.tmud.command.config;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;

public class Channel implements Command {

	@Override
	public void execute(Mob mob, String input) {
		// TODO Auto-generated method stub
		execute(mob.getPlayer(), input);

	}

	public void execute(Player mob, String input) {

		if (input.length() == 0) {
			mob.out(mob.getConfig().getChannelData().toString());
			return;
		}
		mob.out(mob.getConfig().getChannelData().toggle(input));
	}
}