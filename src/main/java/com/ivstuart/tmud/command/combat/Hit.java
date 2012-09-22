package com.ivstuart.tmud.command.combat;

import org.apache.log4j.Logger;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.fighting.action.BasicAttack;
import com.ivstuart.tmud.state.Mob;

public class Hit implements Command {

	private static final Logger LOGGER = Logger.getLogger(Hit.class);

	@Override
	public void execute(Mob mob, String input) {
		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to kill!");
			return;
		}

		BasicAttack basicAttack = new BasicAttack(mob, target);

		mob.getFight().add(basicAttack);

		// Add aggro to mob and add it to fighting.

		if (target.getFight().getTarget() == null) {
			LOGGER.info("Mob has no target at the moment!");

			BasicAttack bite = new BasicAttack(target, mob);

			target.getFight().add(bite);
		}

	}

}