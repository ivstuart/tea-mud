package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.fighting.action.AttackFlurry;
import com.ivstuart.tmud.state.Mob;

public class Flurry extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {
		// 1st check already fighting

		if (mob.getFight().isEngaged() == false) {
			mob.out("You must be figthing someone to flurry!");
			return;
		}
		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to kill!");
			return;
		}

		AttackFlurry punch = new AttackFlurry(mob, target);

		mob.getFight().add(punch);
	}

}