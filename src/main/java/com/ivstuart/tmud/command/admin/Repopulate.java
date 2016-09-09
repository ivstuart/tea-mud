/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.WorldTime;

public class Repopulate extends AdminCommand {

	@Override
	public void execute(Mob mob, String input) {

		super.execute(mob,input);

		mob.out("Force repopulation of killed creatures.");

		WorldTime.getInstance().repopulateMobs(true);

	}
}
