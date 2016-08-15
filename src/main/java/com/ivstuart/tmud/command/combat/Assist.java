package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
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

		mob.out("<S-You/NAME> assist " + target.getName() + " by attacking "
				+ targetTarget.getName());

	}

}