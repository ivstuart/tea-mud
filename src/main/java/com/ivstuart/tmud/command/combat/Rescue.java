package com.ivstuart.tmud.command.combat;

import java.util.List;

import com.ivstuart.tmud.command.BaseCommand;
import org.apache.log4j.Logger;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.WorldTime;

/**
 * TODO rescue <mob> rescue <mob> [from <mob>] rescue <mob> [from all] rescue
 * <mob> [from 1.good] TODO make into a fight action like bash
 * 
 * @author Ivan Stuart
 * 
 */
public class Rescue extends BaseCommand {

	private static final String RESCUE = "rescue";
	
	private static final Logger LOGGER = Logger.getLogger(Rescue.class);

	@Override
	public void execute(Mob mob, String input) {

		LOGGER.debug(mob.getName()+" executing rescue command");

		if (!mob.getLearned().hasLearned(RESCUE)) {
			mob.out("You have no knowledge of rescue");
			return;
		}

		Mob target = mob.getRoom().getMob(input);

		if (target == null) {
			mob.out(input + " is not here to rescue!");
			return;
		}

		/**
		 * if (!target.isPlayer()) { mob.out(input + " is not a player!");
		 * return; }
		 */

		// deduct 1 move point?

		// rescue from all attackers? or just one? random or first?

		List<Mob> aggressors = target.getFight().getTargettedBy();

		if (aggressors.isEmpty()) {
			mob.out(input + " does not need to be rescued");
			return;
		}

		if (!mob.getMv().deduct(10)) {
			mob.out("You do not have enough movement left to rescue");
			return;
		}

		Mob aggressor = aggressors.get(0);

		Ability ability = mob.getLearned().getAbility(RESCUE);
		if (ability.isSuccessful()) {
			// mob.out("<S-You/NAME> successfully rescue <T-you/NAME>.");
			mob.getRoom().out("<S-You/NAME> successfully rescue <T-you/NAME>.");
			if (!mob.getFight().isFighting()) {
				mob.getFight().getMelee().setTarget(aggressor);
				WorldTime.addFighting(mob);
			}

			LOGGER.debug(mob.getName() +" rescues "+aggressor.getFight().getTarget().getName()+" from combat with you.");
			aggressor.getFight().changeTarget(mob);

			if (ability.isImproved()) {
				mob.out("[[[[ Your ability to " + ability.getId()
						+ " has improved ]]]]");
				ability.improve();
			}
		} else {
			mob.out("<S-You/NAME> fail to rescue <T-you/NAME>.");
		}

	}

}