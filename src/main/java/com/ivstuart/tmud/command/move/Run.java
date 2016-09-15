/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.move;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.CommandProvider;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;

public class Run extends BaseCommand {

	// Run w w w n n

	@Override
	public void execute(Mob mob, String input) {

		mob.setRunning(true);

		for (String direction : input.split(" ")) {

			Room previousRoom = mob.getRoom();
			Room currentRoom = null;

			if (mob.getMv().deduct(10)) {
				CommandProvider.getCommand(EnterNoLook.class).execute(mob,
						direction);

				currentRoom = mob.getRoom();
			} else {
				mob.out("You have run out of puff to run!");
				break;
			}

			if (previousRoom == currentRoom) {
				break;
			}
		}

		mob.setRunning(false);

	}

}