/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.state.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hit extends BaseCommand {

	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void execute(Mob mob, String input) {

        CommandProvider.getCommand(Kill.class).execute(mob, input);

	}

}