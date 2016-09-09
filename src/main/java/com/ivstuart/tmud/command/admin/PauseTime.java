/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.WorldTime;

public class PauseTime extends AdminCommand {

	@Override
	public void execute(Mob mob, String input) {

		// Used to debug when ticks are annoying and are getting in the way
		mob.out("You set the world pause time to "+WorldTime.togglePauseTime());
	}
}
