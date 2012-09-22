package com.ivstuart.tmud.command.ability;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.constants.AbilityConstants;
import com.ivstuart.tmud.state.Mob;

public class ShieldBlock implements Command {

	@Override
	public void execute(Mob mob, String input) {

		if (!mob.getLearned().hasLearned(AbilityConstants.SHIELD_BLOCK)) {
			mob.out("You have no knowledge of shield block");
			return;
		}

		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to shield block!");
			return;
		}

	}

}