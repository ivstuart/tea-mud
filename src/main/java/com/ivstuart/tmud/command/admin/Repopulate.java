package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.WorldTime;

public class Repopulate extends AdminCommand {

	@Override
	public void execute(Mob mob, String input) {

		super.execute(mob,input);

		mob.out("Force repopulation of killed creatures.");

		WorldTime.getInstance().repopulateMobs(true);

	}
}
