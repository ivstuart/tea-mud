package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.WorldTime;

public class ShowFighting implements Command {

	@Override
	public void execute(Mob mob, String input) {

		mob.out("showing who is fighting");

		for (Mob fighter : WorldTime.getFighting()) {
			mob.out(fighter.getName() + " is fighting");
		}

	}
}
