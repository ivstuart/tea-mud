package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.state.Mob;

public class Assist extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {
		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to assist!");
			return;
		}

		Mob targetTarget = target.getFight().getTarget();

		mob.getFight().changeTarget(targetTarget);

        mob.out(new Msg(mob, targetTarget, ("<S-You/NAME> assist " + target.getName() + " by attacking <T-NAME>")));

	}

}