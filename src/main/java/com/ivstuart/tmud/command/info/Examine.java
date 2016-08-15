package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.state.Mob;

public class Examine extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		CommandProvider.getCommand(Look.class).execute(mob, input);
	}
}