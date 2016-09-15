/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.state.Mob;

public class Disengage extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		if (!mob.getFight().isFighting()) {
			mob.out("Someone much be attacked by you in order to disengage");
			return;
		}

		Mob target = mob.getFight().getTarget();

		if(target != null) {
			mob.out("You disengage from fighting a "+target.getName());
		}

		mob.getFight().stopFighting();

	}

}