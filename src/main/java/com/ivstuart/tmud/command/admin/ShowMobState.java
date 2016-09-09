/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.admin;

import com.ivstuart.tmud.state.Mob;

public class ShowMobState extends AdminCommand {

	@Override
	public void execute(Mob mob, String input) {


		super.execute(mob,input);

		mob.out("Showing mob state:\n");

		if (input.length() > 0) {
			Mob target = mob.getRoom().getMob(input);

			if (target == null) {
				mob.out(input + " is not here to get fighting stats on!");
				return;
			}

			mob.out("MobState = " + target.getMobStatus());
		}
		else {
			mob.out("MobState = " + mob.getMobStatus());
		}
	}
}
