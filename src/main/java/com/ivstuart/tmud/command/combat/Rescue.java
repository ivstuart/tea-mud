/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.command.combat;

import com.ivstuart.tmud.command.BaseCommand;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.state.Ability;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.world.WorldTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.ivstuart.tmud.constants.SkillNames.RESCUE;

/**
 *     rescue <mob>
 *     rescue <mob> [from <mob>]
 *     rescue <mob> [from all] rescue
 * <mob> [from 1.good]
 * 
 * @author Ivan Stuart
 * 
 */
public class Rescue extends BaseCommand {
	
	private static final Logger LOGGER = LogManager.getLogger();

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

        if (!ability.isNull() && ability.isSuccessful(mob)) {

            mob.getRoom().out(new Msg(mob, aggressor, ("<S-You/NAME> successfully rescue <T-you/NAME>.")));
            if (!mob.getFight().isFighting()) {
				mob.getFight().getMelee().setTarget(aggressor);
				WorldTime.addFighting(mob);
			}

			LOGGER.debug(mob.getName() +" rescues "+aggressor.getFight().getTarget().getName()+" from combat with you.");
			aggressor.getFight().changeTarget(mob);

		} else {
            mob.out(new Msg(mob, aggressor, ("<S-You/NAME> fail to rescue <T-you/NAME>.")));
        }

	}

}