package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.fighting.Fight;
import com.ivstuart.tmud.state.Mob;

public class Kill extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		if (mob.getRoom().isPeaceful()) {
			mob.out("You can not be aggressive in this room");
			return;
		}

		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to kill!");
			return;
		}

		Fight.startCombat(mob,target);

	}

}