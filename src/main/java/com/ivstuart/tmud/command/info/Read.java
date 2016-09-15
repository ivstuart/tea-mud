/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.info;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.state.Mob;

public class Read extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		CommandProvider.getCommand(Look.class).execute(mob, input);
	}
}