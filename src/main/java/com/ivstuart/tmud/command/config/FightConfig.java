package com.ivstuart.tmud.command.config;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.person.Player;
import com.ivstuart.tmud.state.Mob;

// TODO this is not finished- mobs should be able to configure themselves fighting wise.
// not just players.
public class FightConfig implements Command {

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