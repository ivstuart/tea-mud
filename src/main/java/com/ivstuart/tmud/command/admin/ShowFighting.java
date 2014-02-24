package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.WorldTime;

public class ShowFighting implements Command {

	@Override
	public void execute(Mob mob, String input) {

		if(!mob.isAdmin()) {
			mob.out("Admin only");
			// return;
		}
		
		mob.out("Showing who is fighting:\n");

		for (Mob fighter : WorldTime.getFighting()) {
			mob.out(fighter.getName() + " is fighting in room "+fighter.getRoom().getId());
		}

	}
}
