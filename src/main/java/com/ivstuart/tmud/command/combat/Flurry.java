/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.fighting.action.AttackFlurry;
import com.ivstuart.tmud.state.Mob;

public class Flurry extends BaseCommand {

	@Override
	public void execute(Mob mob, String input) {

		if (mob.getRoom().isPeaceful()) {
			mob.out("You can not be aggressive in this room");
			return;
		}
		// 1st check already fighting

		if (mob.getFight().isEngaged() == false) {
			mob.out("You must be fighting someone to flurry!");
			return;
		}
		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to kill!");
			return;
		}

		if (mob.getFight().isGroundFighting()) {
			mob.out("You can not flurry you are ground fighting");
			return;
		}

		AttackFlurry punch = new AttackFlurry(mob, target);

		mob.getFight().add(punch);
	}

}