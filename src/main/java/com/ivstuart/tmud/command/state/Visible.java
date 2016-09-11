/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

/*
 * Created on 12-Nov-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.command.state;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.person.statistics.affects.BuffStatsAffect;
import com.ivstuart.tmud.state.Mob;

import static com.ivstuart.tmud.common.MobState.STAND;
import static com.ivstuart.tmud.constants.SpellNames.INVISIBILITY;

public class Visible extends BaseCommand {

	@Override
	public void execute(Mob mob_, String input_) {

		// Check current state
		if (mob_.getState().isSleeping()) {
			mob_.out("You are sleeping wake up to vis and invis!");
			return;
		}

        BuffStatsAffect invisAffect = (BuffStatsAffect) mob_.getMobAffects().getAffect(INVISIBILITY);

		if (invisAffect == null){
			mob_.out("That spell is not active!");
			return;
		}

		// Check allowed to change state
		if (mob_.isInvisible()) {
			mob_.out("You turn yourself visible");
			mob_.setInvisible(false);
			return;
		}

		// Change state and notify mob and room

		mob_.out("You turn yourself invisible");

		mob_.setState(STAND);
		mob_.setInvisible(true);

		// Note sleep to wake you will also stand
	}

}
