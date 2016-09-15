/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.move;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.state.Mob;

public class Down extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {
		CommandProvider.getCommand(Enter.class).execute(mob, "down");
	}
}