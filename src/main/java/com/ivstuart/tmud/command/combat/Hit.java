package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.fighting.action.BasicAttack;
import com.ivstuart.tmud.state.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hit extends BaseCommand {

	private static final Logger LOGGER = LogManager.getLogger();

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