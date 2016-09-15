/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Mob;

public class Level extends AdminCommand {

	@Override
	public void execute(Mob mob_, String input_) {

		super.execute(mob_,input_);
		
		int numberOfLevels = 1;

		if (input_.length() > 0) {
			numberOfLevels = Integer.parseInt(input_);
		}

		while (numberOfLevels-- > 0) {

			long xp = mob_.getPlayer().getData().getToLevelXp();

			mob_.getPlayer().getData().addXp(xp);

			mob_.getPlayer().checkIfLeveled();
		}
	}
}
