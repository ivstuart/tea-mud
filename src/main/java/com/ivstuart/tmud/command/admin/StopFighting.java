package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.WorldTime;

public class StopFighting extends AdminCommand {

	@Override
	public void execute(Mob mob, String input) {

		super.execute(mob,input);

		if(input.equalsIgnoreCase("world")) {
			for (Mob fighter : WorldTime.getFighting()) {
				fighter.getFight().stopFighting();
			}
			WorldTime.getFighting().clear(); // defense coding.
			mob.out("Admin stop fighting for the world");
			return;
		}
		
		mob.out("Admin stop fighting for mob target and mob");

		mob.getTargetFight().stopFighting();
		mob.getFight().stopFighting();

	}
}
