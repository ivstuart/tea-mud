package com.ivstuart.tmud.command.move;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.state.Mob;

public class East extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {
		CommandProvider.getCommand(Enter.class).execute(mob, "east");
	}
}