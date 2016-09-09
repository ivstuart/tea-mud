/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 22-Sep-2003
 *
 */
package com.ivstuart.tmud.command.communication;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.World;

/**
 * @author stuarti
 * 
 */
public class Reply extends BaseCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see command.Command#execute(java.lang.String)
	 */
	@Override
	public void execute(Mob mob, String input) {

		String name = mob.getLastToldBy().getName();

		if (name == null) {
			mob.out("No one has told you anything yet!");
			return;
		}

		Mob playerMob = World.getMob(name.toLowerCase());

		if (playerMob == null) {
			mob.out("You can not reply to " + name + " they have left the mud world");
			return;
		}

		if (!playerMob.isPlayer()) {
			mob.out("Talking with someone who is not a player!");
		}

		String message = "$D" + mob.getId() + " tells you -->" + input + "$J";
		playerMob.out(message);
	}

}
