/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.move;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.command.info.Look;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;

public class Enter extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		Room currentRoom = mob.getRoom();

		CommandProvider.getCommand(EnterNoLook.class).execute(mob, input);

		/* if moved */
		if (currentRoom != mob.getRoom()) {
			CommandProvider.getCommand(Look.class).execute(mob, "");
		}

	}

}