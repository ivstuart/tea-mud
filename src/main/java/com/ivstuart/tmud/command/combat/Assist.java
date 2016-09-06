/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.state.Mob;

public class Assist extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		Mob target = null;

		if (input.length() == 0) {
			for (Mob aMob : mob.getPlayer().getGroup()) {
				if (!aMob.getFight().isFighting()) {
					continue;
				}
				if (aMob == mob) {
					continue;
				}
				target = aMob;
				break;
			}
		} else {
			target = mob.getRoom().getMob(input);
		}

		if (target == null) {
			mob.out("No one here to assist!");
			return;
		}

		Mob targetTarget = target.getFight().getTarget();

		if (targetTarget == null) {
			mob.out("Your colleague is not fighting anyone");
			return;
		}

		mob.getFight().changeTarget(targetTarget);

		Fight.startCombat(mob, targetTarget);

        mob.out(new Msg(mob, targetTarget, ("<S-You/NAME> assist " + target.getName() + " by attacking <T-NAME>")));

	}

}