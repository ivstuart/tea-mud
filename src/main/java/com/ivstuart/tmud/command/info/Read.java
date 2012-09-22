package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.state.Mob;

public class Read implements Command {

	@Override
	public void execute(Mob mob, String input) {

		CommandProvider.getCommand(Look.class).execute(mob, input);
	}
}