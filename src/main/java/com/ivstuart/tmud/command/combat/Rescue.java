package com.ivstuart.tmud.command.combat;

import java.util.List;

import com.ivstuart.tmud.command.Command;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;

/**
 * TODO rescue <mob> rescue <mob> [from <mob>] rescue <mob> [from all] rescue
 * <mob> [from 1.good] TODO make into a fight action like bash
 * 
 * @author Ivan Stuart
 * 
 */
public class Rescue implements Command {

	private static final String RESCUE = "rescue";

	@Override
	public void execute(Mob mob, String input) {

		mob.out("todo rescue");

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
			mob.out("<S-You/NAME> successfully rescue <T-you/NAME>.");

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